#!/usr/bin/env bash

echo "Adding plugins and Jenkins configuration..."

source "$snake_defaults"
rm -rf "$JENKINS_DATA_DIR"
tar zxvf docker-services/jenkins/jenkins.tgz -C "$docker_base_path/"
chown -R 1000:1000 "$JENKINS_DATA_DIR"
chmod 777 "$JENKINS_DATA_DIR"
