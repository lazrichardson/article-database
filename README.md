# Article Database  

Luther Richardson  
04-22-2021

## Design pattern and its Use Case Scenario Description. 

### Provide description of the application use case scenario 

My application is a REST API. It takes local XML files downloaded from Pubmed and parses them using
the SAX parser. It takes the article title and dates from those files and loads them into a local
in-memory database, as well as a Lucene database. Finally, it provides an endpoint for searching for
those articles utilizing Spring Boot. The endpoint supports searching by a combination of keyword
and start to end dates.

### Patterns Utilized

#### API Gateway / Microservices

This pattern determines the overall structure of the application. My application provides the client
with an endpoint to call on an aggregation of microservices from a single location, which is through
the HTML query. The user doesn't have to call on each microservice on its own, even though many are
used to return the data, in this case the search results, to the client. This allows the
implementation of the microservices to change without the client needing to know.

#### Pipeline

I used this pattern to provide the behaviour for the XML parsing process in my application. The
Pipeline allows for the processing of data in stages, with each step feeding directly to the next.
This pattern is ideal for data processing because it greatly increases the modularity of the
process, allowing for enhanced troubleshooting by enabling enhanced readability of complex
operations. This pattern also supports the use of the Single Responsibility Principle (SRP)

### Additional Patterns Utilized

### Strategy

The strategy pattern is used in this application to encapsulate different searching behaviours. Each
database provides the context for the search strategy.

### Facade

The database classes act as a facade for additional search complexity where search items and indexes
are passed to the search methods, which also utilize the Strategy pattern.

### Main design concepts 

* What are the design goals in your project?
  * Make the project more extensible 
  * Improve readability of the data load + parsing process
  * Make it easier to implement different searching algorithms
  * Loose coupling between databases and parsing



