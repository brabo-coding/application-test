#!/usr/bin/env bash

if [ `docker ps | grep -c localstask-dynamodb` != 0 ]; then
  docker ps | grep localstask-dynamodb | awk '{print $1}' | xargs docker rm -f
fi