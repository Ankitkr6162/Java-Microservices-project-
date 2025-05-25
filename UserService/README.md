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


*** Fault Tolerance ***

Fault Tolerance in microservices refers to the ability of a system to continue functioning correctly even when one or more of its components fail.
In the context of Spring Boot microservices, 
fault tolerance ensures that failures in one service don’t cascade and bring down the entire application.
Fault tolerance is implemented with the help of library called as Resilience4j.
It can be implemented with the help of different  strategies like circuit breaker, Rate Limiter or Retry .

*** Circuit Breaker ***

Prevents a service from repeatedly calling a failing service. 
If failures exceed a threshold, the circuit opens and blocks calls for some time.


The Circuit Breaker pattern in microservices is a fault tolerance mechanism that prevents
the system from repeatedly trying to execute an operation that's likely to fail—such as calling a downed service.
It works like an electrical circuit breaker and operates in three states:

1. Closed State
The circuit is closed, meaning everything is normal.
All requests are allowed to pass through to the downstream service.
The circuit breaker monitors the success/failure of these requests.
If the failure rate exceeds a configured threshold, the circuit trips and transitions to the Open state.

Example scenario:
100 requests sent
60 fail (threshold is 50%)
Circuit breaker moves to Open



2. Open State
The circuit is open, meaning calls to the service are not allowed.
Any calls made during this time are automatically failed (fallback is used instead).
The system waits for a "cool-off" period (e.g., 30 seconds), during which no attempt is made to call the service.
This gives the downstream service time to recover.

Example:
External service is down.
Circuit breaker avoids overwhelming it with more requests.
Returns fallback response immediately.



3. Half-Open State
After the cool-off period, the circuit transitions to half-open.
A limited number of test requests are allowed through.
Based on the result:
If the test requests succeed, the circuit closes again (back to normal).
If the test requests fail, the circuit opens again.

Example:
5 test requests allowed
4 succeed → back to Closed
3 fail → back to Open


*** Retry ***

Retry is a fault tolerance strategy where a failed operation is automatically retried a
specified number of times before giving up.
This is helpful in handling temporary failures like:
Network timeouts
Slow downstream services
Temporary unavailability


Why use Retry ?
Without retry:
One-time network glitch → request fails

With retry:
Retry a few times before failing
Prevents cascading failures due to minor/transient issues


*** Rate  Limiter ***

A Rate Limiter is a fault-tolerance mechanism used to control the number of requests a client (or user) can make to a service within a given time frame.
It helps prevent:
Abuse or overuse of APIs
Server overload
DDoS attacks
Performance degradation



Why Use Rate Limiting?

Without a rate limiter:
One misbehaving client can flood your service and make it unresponsive to others.

With a rate limiter:
Requests are throttled and excess requests are rejected (or delayed).
Ensures fair usage, stability, and availability of your services.


*** What is DDoS attack ***
DDoS (Distributed Denial of Service) is a malicious cyberattack where multiple systems flood a targeted server, 
service, or network with an overwhelming amount of traffic,
causing it to slow down, crash, or become unavailable to legitimate users.


Breakdown of the Term
Distributed: The attack is launched from many sources (often thousands of compromised computers called a botnet).
Denial of Service: The aim is to deny access to the legitimate users by overwhelming the server or network.




