#!/bin/bash

###
# Post install
###

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

echo "Configuring Message of the Day..."
rm /etc/update-motd.d/10-help-text
cat << EOF > /etc/update-motd.d/10-banner-text
#!/bin/sh
printf "\n"
printf "    ___                  _  \n"
printf "   / __\   _  __ _ _ __ (_) \n"
printf "  / / | | | |/ _\` | '_ \| | \n"
printf " / /__| |_| | (_| | | | | | \n"
printf " \____/\__, |\__, |_| |_|_| \n"
printf "       |___/ |___/          \n"
printf "  __             _          \n"
printf " / _\_ __   __ _| | _____   \n"
printf " \ \| '_ \ / _\` | |/ / _ \  \n"
printf " _\ \ | | | (_| |   <  __/  \n"
printf " \__/_| |_|\__,_|_|\_\___|  \n"
printf "\n"

EOF
chmod +x /etc/update-motd.d/10-banner-text

cat << EOF > /etc/update-motd.d/30-docker-status
#!/bin/sh
/usr/bin/docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Size}}"
EOF
chmod +x /etc/update-motd.d/30-docker-status

cat << EOF > /etc/update-motd.d/40-system-stats
#!/bin/sh
echo
date
echo
uptime
echo
df -h
echo
EOF
chmod +x /etc/update-motd.d/40-system-stats

echo "Fixing system locale"
base/fix-locale.sh

echo "Configuring ntp-pool..."
cat << EOF > /etc/ntp.conf
# /etc/ntp.conf, configuration for ntpd; see ntp.conf(5) for help

driftfile /var/lib/ntp/ntp.drift


# Enable this if you want statistics to be logged.
#statsdir /var/log/ntpstats/

statistics loopstats peerstats clockstats
filegen loopstats file loopstats type day enable
filegen peerstats file peerstats type day enable
filegen clockstats file clockstats type day enable

# Specify one or more NTP servers.

# Use servers from the NTP Pool Project. Approved by Ubuntu Technical Board
# on 2011-02-08 (LP: #104525). See http://www.pool.ntp.org/join.html for
# more information.
#server 0.ubuntu.pool.ntp.org
#server 1.ubuntu.pool.ntp.org
#server 2.ubuntu.pool.ntp.org
#server 3.ubuntu.pool.ntp.org

server 0.se.pool.ntp.org
server 1.se.pool.ntp.org
server 2.se.pool.ntp.org
server 3.se.pool.ntp.org

# Use Ubuntu's ntp server as a fallback.
server ntp.ubuntu.com

# Access control configuration; see /usr/share/doc/ntp-doc/html/accopt.html for
# details.  The web page <http://support.ntp.org/bin/view/Support/AccessRestrictions>
# might also be helpful.
#
# Note that "restrict" applies to both servers and clients, so a configuration
# that might be intended to block requests from certain clients could also end
# up blocking replies from your own upstream servers.

# By default, exchange time with everybody, but don't allow configuration.
restrict -4 default kod notrap nomodify nopeer noquery
restrict -6 default kod notrap nomodify nopeer noquery

# Local users may interrogate the ntp server more closely.
restrict 127.0.0.1
restrict ::1

# Clients from this (example!) subnet have unlimited access, but only if
# cryptographically authenticated.
#restrict 192.168.123.0 mask 255.255.255.0 notrust


# If you want to provide time to your local subnet, change the next line.
# (Again, the address is an example only.)
#broadcast 192.168.123.255

# If you want to listen to time broadcasts on your local subnet, de-comment the
# next lines.  Please do this only if you trust everybody on the network!
#disable auth
#broadcastclient
EOF

echo "Hardening SSH"
if ( grep 'UseDNS' /etc/ssh/sshd_config ); then
    echo "UseDNS already defined!"
else
    echo "Removing DNS check on ssh login"
    echo "UseDNS no" | tee -a /etc/ssh/sshd_config
fi

if ( grep 'PermitRootLogin no' /etc/ssh/sshd_config ); then
    echo "PermitRootLogin already set to no"
else
    mv /etc/ssh/sshd_config /etc/ssh/sshd_config.org > /dev/null
    cat /etc/ssh/sshd_config.org | sed 's/PermitRootLogin .*/PermitRootLogin no/' > /etc/ssh/sshd_config
fi

#echo "Enabling two factor login"
#if ( grep 'pam_google_authenticator.so' /etc/pam.d/sshd ); then
#    echo "pam_google_authenticator.so already defined!"
#else
#    echo "Require pam_google_authenticator.so"
#    echo "auth required pam_google_authenticator.so" | tee -a /etc/pam.d/sshd
#fi
#
#if ( grep 'ChallengeResponseAuthentication yes' /etc/ssh/sshd_config ); then
#    echo "ChallengeResponseAuthentication already set to yes"
#else
#    mv /etc/ssh/sshd_config /etc/ssh/sshd_config.org > /dev/null
#    cat /etc/ssh/sshd_config.org | sed 's/ChallengeResponseAuthentication .*/ChallengeResponseAuthentication yes/' > /etc/ssh/sshd_config
#fi
systemctl restart ssh

