#!/bin/bash -eu

###
# Locale fix
###

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

defaultlang="en_US"
defaultEncoding="UTF-8"

cat << EOF > /etc/default/locale
LANG="$defaultlang"
LANGUAGE="$defaultlang.$defaultEncoding"
LC_ALL="$defaultlang.$defaultEncoding"
EOF

locale-gen $defaultlang
dpkg-reconfigure locales

echo "Log out and in to reload locales"
