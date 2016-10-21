# Project01

Welcome to the main repository for the first project of the AMT course at HEIG-VD.

This project is a simple Java EE application that manage user accounts.

## Deploy the Java EE application

After cloning this repository, you can deploy the application by just entering the following commands in a console:

```
cd scripts
``` 
```
sh launch.sh
```

The first command moves you from the main repository of the project to the scripts repository. The second command launches the deployment of the application by executing a shell script. This script contains the following commands:

```
mvn clean install -f ../pom.xml
``` 
``` 
cp ../target/Project01.war ../docker/images/wildfly/data/
```
```
docker-compose -f ../topology-project01/docker-compose.yml down
docker-compose -f ../topology-project01/docker-compose.yml up --build
```

The first command builds the project with Apache Maven and produces a ".war" file in a target directory. The second command copies this ".war" file in the right directory (data directory). This ".war" file will be copied in the path "/opt/jboss/wildfly/standalone/deployments/" when the Docker container that contains the WildFly application server starts (auto-deploy purpose). The last two commands launch all the needed Docker containers: a container for the MySQl database, a container for phpMyAdmin and a container for the WildFly application server.

If an error occured, you can enter these commands by hand in the script directory or directly in the right directories without the -f argument for the first, third and fourth commands and by adapating the second command.

## Access the Java EE application

