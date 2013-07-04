cdi-owb-jpa2-example
=====================

Example application showing the integration between CDI Java Specification (using Apache Open Web Beans provider) with JPA2 ORM (eclipselink). It`s an application with only one entity class, and a Junit test class, receiving the objects via dependency injection with CDI annotations.

You can use it to study the integration, and to get an example of how it`s done, to create a more complex system.

To create the project,

just run the command below:

mvn eclipse:eclipse

It will create the eclipse project.

Then you can run the unit tests from Eclipse Junit framework.

To check that it`s working, just run mvn clean install, it will download the libs, compile the code, run the tests and build the app jar file.

