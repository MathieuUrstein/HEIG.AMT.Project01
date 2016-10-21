# Project01

Welcome to the main repository for the first project of the AMT course at HEIG-VD.

This project is a simple Java EE application that manage user accounts. All the information about these accounts is stored in a MySQL database.

## Deploy the Java EE application

### Requirements

To deploy this application you will need the following components:

* Apache Maven
* Shell sh
* Docker

### Procedure

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

The first command builds the project with Apache Maven and produces a .war file in a target directory. The second command copies this .war file in the right directory (data directory). This .war file will be copied in the path **/opt/jboss/wildfly/standalone/deployments/** when the Docker container that contains the WildFly application server starts (auto-deploy purpose). The last two commands launch all the needed Docker containers: a container for the MySQL database, a container for phpMyAdmin and a container for the WildFly application server.

If an error occured, you can enter these four commands by hand in the scripts directory or directly in the right directories without the -f argument for the first, third and fourth commands and by adapating the second command.

## Access the Java EE application

When you have executed the shell script, the application can be accessed by entering the following URL in a web browser: **http://localhost:9090/Project01**.

## Access the MySQL database of the Java EE application

If you need to access to the database of the application, you can use the web application phpMyAdmin by entering the following URL in a web browser: **http://localhost:6060**. You can use the administrator account **root** with the password **adminpw** to connect yourself.

You will see that data is already present in the database used by the project (named project01). This data is added when we build the MySQL Docker image (a .sql file to build the database and an other to add data). It is for test purpose.

## Features of the Java EE application

When you access the application with the browser, you will see the welcome page. From there you can access the login page. Now you can login if you have already an account or go to the register page to create a new account. When your are connected, you are redirected to the protected page. You can also go back to the welcome page and go to the administration page with the new link. In this page you can see all the user accounts in the database (id, last name, first name, user name, but not password for security reasons). At the end you can logout with the specific link in a page and your session will be ended and you will be redirected to the welcome page.

## REST API

You can find a description of the REST API of this Java EE application and a Postman script in the corresponding [Wiki page](https://github.com/MathieuUrstein/HEIG.AMT.Project01/wiki/REST-API).

## JMeter script

In the scripts folder you will find a test.jmx file. You can open this file in JMeter, launch the corresponding tests (all in the same time) and see the results (normally all the tests must be in green). These tests launch several HTTP requests (GET, POST, PUT and DELETE) to the REST API. It is for testing the concurrency of the application.


Mathieu Urstein and Sébastien Boson
