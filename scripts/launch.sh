#!/bin/bash

# error case
set -e

echo step 1 : maven command to build the .war
echo ' '
mvn clean install -f ../pom.xml
echo ' '

echo step 2 : copy the .war in the right directory
echo ' '
cp ../target/Project01.war ../docker/images/wildfly/data/
echo ' '

echo setp 3 : docker-compose commands to launch the containers
echo ' '
docker-compose -f ../topology-project01/docker-compose.yml down
docker-compose -f ../topology-project01/docker-compose.yml up --build