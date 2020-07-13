#!/usr/bin/env bash

devops/scripts/clean.sh

docker run --name 'localstask-dynamodb' -d -p '4567-4599:4567-4599' --env SERVICES=dynamodb  localstack/localstack
