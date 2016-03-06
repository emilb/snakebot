#!/bin/bash -eu

###
# systemd startup config for docker containers
###

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

guid=`getent group fileshare | cut -d: -f3`
uid=`id -u admin`

cat << EOF > $snake_defaults
# snake system defaults
JENKINS_DATA_DIR="$docker_base_path/jenkins"
SONAR_DATA_DIR="$docker_base_path/sonarqube/data"
SONAR_EXTENSION_DIR="$docker_base_path/sonarqube/extensions"
REGISTRY_DATA_DIR="$docker_base_path/registry"
POSTGRES_DATA_DIR="$docker_base_path/postgres"
ARCHIVA_DATA_DIR="$docker_base_path/archiva"

DOMAIN="$domain"
INTERNAL_DOMAIN="$internal_domain"
SUBDOMAINS="$subdomains"
DOCKER_IP="$docker_ip"
EOF

source "$snake_defaults"

# Create datadirs
mkdir -p "$JENKINS_DATA_DIR"
chmod 777 "$JENKINS_DATA_DIR"

mkdir -p "$SONAR_DATA_DIR"
chmod 777 "$SONAR_DATA_DIR"

mkdir -p "$SONAR_EXTENSION_DIR"
chmod 777 "$SONAR_EXTENSION_DIR"

mkdir -p "$REGISTRY_DATA_DIR"
chmod 777 "$REGISTRY_DATA_DIR"

#log_config="--log-driver=gelf --log-opt gelf-address=udp://localhost:12201"
log_config=""

#**** skydns-docker.service ****
cat << EOF > /etc/systemd/system/skydns-docker.service
[Unit]
Description=SkyDNS container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill skydns
ExecStartPre=-/usr/bin/docker rm skydns

ExecStart=/usr/bin/docker run \
	$log_config \
	-p $docker_ip:53:53/udp \
	-p $docker_ip:8080:8080 \
	--name skydns \
	crosbymichael/skydns \
	    -nameserver 8.8.8.8:53,8.8.4.4:53 \
	    -domain $domain

ExecStop=/usr/bin/docker stop skydns

[Install]
WantedBy=multi-user.target
EOF

# **** skydock-docker.service ****
cat << EOF > /etc/systemd/system/skydock-docker.service
[Unit]
Description=SkyDock container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill skydock
ExecStartPre=-/usr/bin/docker rm skydock

ExecStart=/usr/bin/docker run \
	$log_config \
	-v /var/run/docker.sock:/docker.sock \
	--link="skydns" \
	--name skydock \
	crosbymichael/skydock -ttl 30 -environment docker -s /docker.sock -domain $domain -name skydns

ExecStop=/usr/bin/docker stop skydock

[Install]
WantedBy=multi-user.target
EOF

#**** nginx-proxy-docker.service ****
cat << EOF > /etc/systemd/system/nginx-proxy-docker.service
[Unit]
Description=nginx proxy container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill nginx-proxy
ExecStartPre=-/usr/bin/docker rm nginx-proxy
ExecStartPre=-/bin/sleep 10

ExecStart=/usr/bin/docker run \
	$log_config \
	-p 80:80 \
	-v /var/run/docker.sock:/tmp/docker.sock:ro \
	--name nginx-proxy \
	jwilder/nginx-proxy

ExecStop=/usr/bin/docker stop nginx-proxy

[Install]
WantedBy=multi-user.target
EOF

#**** jenkins-docker.service ****
cat << EOF > /etc/systemd/system/jenkins-docker.service
[Unit]
Description=jenkins container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill jenkins
ExecStartPre=-/usr/bin/docker rm jenkins
ExecStartPre=-/bin/sleep 30

ExecStart=/usr/bin/docker run \
	$log_config \
	-e VIRTUAL_PORT=8080 \
	-e VIRTUAL_HOST=jenkins.$domain,jenkins.$internal_domain \
	-v \${JENKINS_DATA_DIR}:/var/jenkins_home \
	--name jenkins \
	jenkins

ExecStop=/usr/bin/docker stop jenkins

[Install]
WantedBy=multi-user.target
EOF

#**** sonarqube-docker.service ****
cat << EOF > /etc/systemd/system/sonarqube-docker.service
[Unit]
Description=sonarqube container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill sonarqube
ExecStartPre=-/usr/bin/docker rm sonarqube
ExecStartPre=-/bin/sleep 45

ExecStart=/usr/bin/docker run \
	$log_config \
	-e VIRTUAL_PORT=9000 \
	-e VIRTUAL_HOST=sonarqube.$domain,sonarqube.$internal_domain \
	-e SONARQUBE_JDBC_USERNAME=sonar \
    -e SONARQUBE_JDBC_PASSWORD=sonar \
    -e SONARQUBE_JDBC_URL=jdbc:postgresql://postgres.docker.snake.cygni.se:5432/sonar \
	-v \${SONAR_DATA_DIR}:/opt/sonarqube/data \
	-v \${SONAR_EXTENSION_DIR}:/opt/sonarqube/data \
	--name sonarqube \
	sonarqube

ExecStop=/usr/bin/docker stop sonarqube

[Install]
WantedBy=multi-user.target
EOF

#**** postgres-docker.service ****
cat << EOF > /etc/systemd/system/postgres-docker.service
[Unit]
Description=postgres container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill postgres
ExecStartPre=-/usr/bin/docker rm postgres
ExecStartPre=-/bin/sleep 30

ExecStart=/usr/bin/docker run \
	$log_config \
	-e POSTGRES_USER=sonar \
    -e POSTGRES_PASSWORD=sonar \
	-v \${POSTGRES_DATA_DIR}:/var/lib/postgresql/data \
	--name postgres \
	postgres

ExecStop=/usr/bin/docker stop postgres

[Install]
WantedBy=multi-user.target
EOF

#**** archiva-docker.service ****
cat << EOF > /etc/systemd/system/archiva-docker.service
[Unit]
Description=archiva container
Requires=docker.service
After=docker.service

[Service]
Restart=always
EnvironmentFile=$snake_defaults

ExecStartPre=-/usr/bin/docker kill archiva
ExecStartPre=-/usr/bin/docker rm archiva
ExecStartPre=-/bin/sleep 30

ExecStart=/usr/bin/docker run \
	$log_config \
	-e VIRTUAL_PORT=8080 \
	-e VIRTUAL_HOST=archiva.$domain,archiva.$internal_domain \
	-v \${ARCHIVA_DATA_DIR}:/var/archiva \
	--name archiva \
	ninjaben/archiva-docker

ExecStop=/usr/bin/docker stop archiva

[Install]
WantedBy=multi-user.target
EOF

systemctl enable skydns-docker.service
systemctl enable skydock-docker.service
systemctl enable nginx-proxy-docker.service
systemctl enable jenkins-docker.service
systemctl enable sonarqube-docker.service
systemctl enable postgres-docker.service
systemctl enable archiva-docker.service

systemctl daemon-reload

find /etc/systemd/system -iname "*.service" -exec chmod +x {} \;
find /etc/systemd/system -iname "*.timer" -exec chmod +x {} \;
