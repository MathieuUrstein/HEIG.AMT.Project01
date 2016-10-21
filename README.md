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

The first command builds the project with Apache Maven and produces a ".war" file in a target directory. The second command copies this ".war" file in the right directory (data directory). This ".war" file will be copied in the path "**/opt/jboss/wildfly/standalone/deployments/**" when the Docker container that contains the WildFly application server starts (auto-deploy purpose). The last two commands launch all the needed Docker containers: a container for the MySQL database, a container for phpMyAdmin and a container for the WildFly application server.

If an error occured, you can enter these four commands by hand in the script directory or directly in the right directories without the -f argument for the first, third and fourth commands and by adapating the second command.

## Access the Java EE application

When you have executed the shell script, the application can be accessed by entering the following URL in a web browser: "http://localhost:9090/Project01".

## Access the MySQL database of the Java EE application

If you need to access to the database of the application, you can use the web application phpMyAdmin by entering the following URL in a web browser : "http://localhost:6060". You can use the administrator account : "root/adminpw".
