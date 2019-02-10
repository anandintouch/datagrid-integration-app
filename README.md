# Spring-JDG: Spring App and Datagrid integration example
# What is it?
Spring-jdg is an example that shows how to use Infinispan or JBoss Data Grid together with Spring.
In order to simplify the Spring bootstrapping this project uses Spring Boot. Infinispan/JBoss Data Grid is configured to use only default values.
# System requirements
All you need to build this project is Java 7.0 (Java SDK 1.7) or better, Maven 3.0 or better.
# Configure Maven
If your current setting.xml file doesn't have redhat profile/repository configured then this project might not build.If you have not yet done so, you could Configure Maven before testing this sample datagrid-integration-app .
Copy the settings.xml file from the root of the project directory to your Maven install directory.
Note: If you do not wish to configure the Maven settings, you must pass the configuration setting on every Maven command as follows: -s PROJECT_HOME/settings.xml

# Build the App (datagrid-integration-app)
* Clone this repo and go to the project folder  "~/datagrid-integration-app"
* Type this command to build the app:

        mvn clean install -DskipTests=true
# Deploy to Openshift Platform
I'm using sample yml template to build and deploy the app in the openshift project.
**Assumption**: DataGrid server/pod is already provisioned. Follow [Datagrid Deployment](https://access.redhat.com/documentation/en-us/red_hat_jboss_data_grid/7.2/html-single/data_grid_for_openshift/index) page to provision datagrid in your openshift project if it's not available. 
* Make sure you have project created in openshift,if not then create using below command after login to openshift.
    ```sh
    $ oc new-project <projectname>
    ```

* Being in your project namespace, first create the configmap for the application from the path "~/datagrid-integration-app/openshift"
    ```sh
    $ oc create -f config.yml
    ```

* Apply the build/deploy template .
  Here "sample-jdg-app" is the name of the app,it could be any name and same should be used in the next step. 
    ```sh
    $ oc process -f my-build-deploy-no-ssl-template.yml -o yaml APPLICATION_NAME=sample-jdg-app | oc apply -f -
    ```

* Start build/deploy of the application from the path "~/datagrid-integration-app"
    ```sh
    $ oc start-build sample-jdg-app --from-file=target/springbootjdgapp.jar
    ```

* Done !! Application should be up and running in your project.

# Access the application
The application will be running at the following URLs:
<http://sample-jdg-app-anand-datagrid-demo.<ocp domain name>/clients>

**Note:** Here "anand-datagrid-demo" is the name of the project/namespace in the Openshift and "sample-jdg-app" is the name you provide for your app.
http://<app name>-<project name>.<ocp domain name>/clients
# Endpoints to test the DataGrid read/write operations
You could test below Endpoint/APIs in the postman tool.
1. GET call to read/write one record to datagrid
    ```sh
    http://sample-jdg-app-<projectname>.<ocp domain name>/clients?caching=true
    ```

2. PUT call to write and update data where "id" is the key in the URI.
    ```sh
    http://sample-jdg-app-<projectname>.<ocp domain name>/clients?id=2&caching=true
    ```

3. GET call to Evict entire records from the datagrid server
   ```sh
   http://sample-jdg-app-<projectname>.<ocp domain name>/clients/evictAll
   ```
# Notes/Info: 
* To view number of entries written to DataGrid, go to "Open Java Console" in datagrid pod and check respective cache. Example cache configured in this project/app is "session_cache"
* This app is configured to connect to datagrid service by name/host is "datagrid-service-app"(name of the datagrid server) using Hotrod protocol and the port is 11333.
* In this sample app,hostname,cache name is hardcoded .Incase you wish to modify hostname then update "DomainConfig.java" class . To modify cache name ,then update "CachedClientGetter.java" and "ClientCache.java" files.
Rough(not fully auto configured) build/deploy yml template and config map for this project is used as an openshift prerequsites 

