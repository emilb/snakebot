#!/bin/bash -eu

###
# setup
###

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

source config.sh

echo "Base install..."
base/base-install.sh

echo "Generating passwords..."
./generate-passwords.sh

source passwords.sh

echo "Post install..."
base/post-install.sh

echo "Scripts install..."
base/scripts-install.sh

echo "Installing Docker..."
docker-services/docker.sh

echo "Adding users..."
base/users-install.sh

echo "Pulling docker containers..."
docker-services/pull-services.sh

echo "Adding systemd for docker containers..."
docker-services/add-systemd-startup.sh

echo "Generating SSL keys..."
#docker-services/generate-ssl-keys.sh

echo ""
echo "All is done, rember to keep generated passwords safe!"
cat ~/passwords.sh | sed s/'export '//g

#
# Problems with apt-get update then run:
#
# apt-get clean
# rm -rf /var/cache/apt/*
# rm -rf /var/lib/apt/lists/*
# apt-get update