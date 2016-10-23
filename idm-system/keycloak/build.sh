#!/bin/bash

docker build -t tdlabs/keycloak .

docker images -f "dangling=true" -q | xargs --no-run-if-empty docker rmi -f