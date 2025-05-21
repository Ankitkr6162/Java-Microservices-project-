*** Before Microservices ***

* Monolithic architecture : multiple components are combined in single large app.
* Single code base.
* Deployed in single bundle.
* Change in one service then whole app is deployed.
* Development problem : every developer has to communicate with each other.
* Problem in scaling.
* Cumbersome over time.

*** After Microservices ***

* In Microservices large apps are divided into small parts.
* Every service has a different code base with different tech-stack.
* But the disadvantage is Handling microservices is a complex task.



*** Service Registry ***

A service registry is a central directory that keeps track of available services,
their locations, and associated metadata in a distributed system, particularly within
microservices architectures. It tracks all the information of services which will be independent of
IPs while calling the services we can just use the name of the service .

*** API Gateway ***

Think of it like a front door to your microservices system.
Instead of having clients talk to each microservice directly, they interact with the API Gateway. The Gateway then:
Routes the request to the correct service.
It makes our application independent of IPs and ports we can directly call any service and the client 
does not know from which service the response is coming from. 

       +-------------+
       |  Client App |
       +------+------+
              |
              v
     +--------+---------+
     |    API Gateway    |  <-- Spring Cloud Gateway
     +--------+---------+
              |
+----------+-----------+-------------+
|                      |             |
v                      v             v
User Service      Rating Service   Hotel Service


*** Config Server ***

A Config Server in the context of Spring Boot microservices is a centralized configuration management service. 
It allows you to manage all your microservices' configuration files
(e.g., application.yml, application.properties) from a central location, typically stored in a Git repository.

When you have many microservices, each one usually has its own set of configuration values (database URL, ports, API keys, etc.). Managing these separately can be difficult.
A Spring Cloud Config Server solves this by:
Storing configuration in one place (usually Git, but can also be file system, SVN, or Vault).
Serving configuration values to all microservices via HTTP.
Supporting profiles (e.g., dev, test, prod) and dynamic refresh of configs.

            Git Repo (config files)
                   |
                   v
         +--------------------+
         |  Spring Config     |
         |      Server        |
         +--------------------+
                   |
                   v
+-------------------+   +-------------------+
| Microservice A    |   | Microservice B    |
| (Config Client)   |   | (Config Client)   |
+-------------------+   +-------------------+

