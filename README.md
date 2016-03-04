# payzen-soapwebservice-java-example
This is an example of a web application that performs Payzen payments using Web Services and Java.

It uses the [Java Payzen SDK](https://github.com/profesorfalken/PayzenWebServicesSDK "Payzen Web Services SDK") which facilitates the use of Payzen Web Services v5 interface:

[https://www.en.payzen.eu/wp-content/uploads/integration/Technical_Implementation_Guide_Webservice_V5_PayZen_v1.5.pdf](https://www.en.payzen.eu/wp-content/uploads/integration/Technical_Implementation_Guide_Webservice_V5_PayZen_v1.5.pdf)

# How to launch this example

The easiest way to launch this example is usen Maven: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

Once Maven is installed, you can clone this repository using Git or just download the project as a Zip.

**Configuration**

The first thing to do in the project is to set the configuration for the site. 

The configuration file is called *payzen-config.properties* and it is placed in */src/main/resources* directory. You will have to set the following parameters:

    shopId=[shop identifier]
    shopKey=[shop private key]
    mode=[mode TEST or PRODUCTION]
    endpointHost=[the name of the host. Ex: secure.payzen.eu]


**Generation and installation**

Then, we have to execute this command from the root path of the project:

    mvn install

This will create a /target directory which includes a .war file.

We only have to take the .war file and place it into the deployment folder of out favourite application server/servlet container (Tomcat, Jetty, Jboss, WebSphere...).

The application will be accesible from a context root with the name of the .war file. For example, if the war file was renamed to ExamplePz.war, we will find the application in the url http://[serverhost]/ExamplePz/

![](http://www.cippu.org/pic/zacJsuQOQhmZtbg4)

# Note about Java version (must read if you use Java < 8)

This example uses Java 8 and lambda expressions, but if you use an older version of java, it can be easily adapted.

To do that, you only have to change the callback style and use anonymous classes instead of lamda expressions.

For example, instead of this:

    (result) -> {
    	ResponseUtils.processResponse(request, response, result);
    }

You can just use this:

    new ResponseHandler() {
	    @Override
    	public void handle(ServiceResult result) throws Exception {
	    	ResponseUtils.processResponse(request, response, result);
        }
    }

For more examples and infomation, please check the page of SDK project: 

https://github.com/profesorfalken/PayzenWebServicesSDK
