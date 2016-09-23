# Hair Salon

#### _An application to manage clients for a hair salon, 9/16/2016_

#### By _**Timothy Herold**_

## Description

_This application is designed to ask the user a series of questions. Once the user specifies the size and scope of their event, the application will calculate that event's total cost. The total cost is determined by an arbitrary formula._

## Setup/Installation Requirements

_Download Java and build source code from [GitHub](https://github.com/therold/hair-salon-java)._
* _$ git clone https://github.com/therold/hair-salon-java.git_
* _$ cd event-planner_
* _$ gradle run_
* _Then open [http://localhost:4567](http://localhost:4567) in your preferred web browser._

## User Stories
* _As a salon employee, I need to be able to see a list of all our stylists._
* _As an employee, I need to be able to select a stylist, see their details, and see a list of all clients that belong to that stylist._
* _As an employee, I need to add new stylists to our system when they are hired._
* _As an employee, I need to be able to add new clients to a specific stylist._
* _As an employee, I need to be able to update a stylist's details._
* _As an employee, I need to be able to update a client's details._
* _As an employee, I need to be able to delete a stylist if they're no longer employed here._
* _As an employee, I need to be able to delete a client if they no longer visit our salon._

## Specification
* The program should be able to store and retrieve the names of each stylist.
  * **Example Input**: Bob
  * **Example Output**: Bob
* The program should be able to store and retrieve the names of each client.
  * **Example Input**: Tom
  * **Example Output**: Tom
* The program should be able to associate a client with a single stylist.
  * **Example Input**: Tom stylist is Bob
  * **Example Output**: Tom stylist is Bob
* The program should be able save individual stylists to a database.
  * **Example Input**: Save Bob
  * **Example Output**: [None]
* The program should be able to save individual clients to a database.
  * **Example Input**: Save Tom
  * **Example Output**: [None]
* The program should be able delete individual stylists from the database.
  * **Example Input**: Delete Bob
  * **Example Output**: [None]
* The program should be able to delete individual clients from the database.
  * **Example Input**: Delete Tom
  * **Example Output**: [None]
* The program should be able update the details of an individual stylists in the database.
  * **Example Input**: Update Bob
  * **Example Output**: [None]
* The program should be able to update the details of an individual clients in the database.
  * **Example Input**: Update Tom
  * **Example Output**: [None]


## Technologies Used

* _Java_
* _JUnit_
* _Gradle_
* _Spark_
* _Velocity_
* _PostgreSQL_

### License

*GPL*

Copyright (c) 2016 **_Timothy Herold_**
