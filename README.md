# Simple Flight Tracking Application

This is a simple Java EE 7 web-application for demonstration purposes.  

The application contains only one page where one can
* add, delete, and edit flights;
* list existing flights in a paginating table;
* filter the list by narrowing to a time period of departures.

There are some automatics demonstrating different aspects of the technology:
* At first start, the system creates some dummy airports, aircrafts and flights automatically.
* If just schedule a flight, it will depart and land automatically on time.
* Views (flight tables) are refreshed automatically when a flight created, updated or deleted.

The project is about 80% ready.

## Getting Started

### Environment

IDE: 		Red Hat JBoss Developer Studio 11.3.0.GA
Project: 	Gradle driven
Language:   Java 8 or higher
Container:  WildFly 12 with Java EE 8
Web:		PrimeFaces 6.2
Testing:    JUnit4 and Arquillian

### Installing

In Eclipse, use the project git import feature.

### Preparing the runtime environment

* Websockets require JSF 2.3, so in Widfly12 environment standalone-ee8.xml is proposed.
* Datasource needs to be created according to the reference in persistence.xml (PostgreSQL preferred).

## Running the tests

Test can be executed as Gradle tasks (build time) and also with the JUnit Eclipse tools. 

## TODOs

* Complete test coverage
* Clean Code 
* DRY
* etc.


## License

This project is licensed under the MIT License.
