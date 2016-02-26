#!/usr/bin/env bash

echo "Adding plugins and Jenkins configuration..."

source "$snake_defaults"
rm -rf "$docker_base_path/jenkins"
mv jenkins.tgz "$docker_base_path/"
tar zxvf "$docker_base_path/jenkins.tgz"
chown -R 1000:1000 "$docker_base_path/jenkins"
rm -rf "$docker_base_path/jenkins.tgz"
