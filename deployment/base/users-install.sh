#!/bin/bash

###
# Create users and group memberships
###

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# Create common group
groupadd $common_group

# Create users
tokens=($users)
for user in "${tokens[@]}"
do
	pwd=`printenv $user`
	echo "$user has password: $pwd"
	sudo adduser $user --gecos "$user,,," --disabled-password
	echo "Setting password $pwd for user: $user"
	echo $user:$pwd | chpasswd

	# Add admin users to correct groups
	echo "Adding $user to sudo"
	usermod -aG sudo $user
	echo "Adding $user to docker"
	usermod -aG docker $user
	echo "Adding $user to $common_group"
	usermod -aG $common_group $user
done

# Reset root password
pwd=`printenv root_user`
echo "Setting root password to: $pwd"
echo root:$pwd | chpasswd

