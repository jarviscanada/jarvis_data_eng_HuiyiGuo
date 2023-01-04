# Introduction
The Twitter CRD application provides a method for a CLI to CRD (Create, Read, and Delete) tweets through Twitter's REST APIs. Maven and Springboot were used to organize this Java-based application. Unit and integration testing were done using JUnit and Mockito. The application was then deployed using Docker.

# Quick Start
In order to running this application through Docker, the environment variables `consumerKey`, `consumerSecret`, `accessToken` and `tokenSecret` from the Twitter Developer account is needed. 

After the environment variables has been set, `post`, `show` and `delete` a tweet actions can be performed using

`docker run --rm -e consumerKey -e consumerSecret -e accessToken -e tokenSecret <username>/twitter show|post|delete [options]`

# Design
## UML diagram
![UML](assets/asset.png)

## Models
The Tweet model for this project has been created to accurately represent the Tweet JSON object that Twitter's REST APIs would deliver. The Tweet model and its dependencies are depicted in the diagram below.
![UML](assets/asset2.JPG)

The app's major implementation components: the main component, the controller, the service component, and the DAO component.

### main component
The arguments entered by the user are passed to the controller layer using this file. In order to establish a connection with the Twitter API, the environment variables (Twitter keys and tokens) are managed here and given to the HttpHelper. The Application uses spring boot to manage dependencies and injects instances across the various classes. The hierarchy of dependency is of `TwitterController` -> `TwitterService` -> `TwitterDao` -> `TwitterHelper`.

### controller
The controller component manages the user's argument given to the service layer by the application/main layer. Results are then returned to the application/main layer. The service component would need this for further checks.

### service
The service component is responsible for the business logic and ensures that the user arguments are valid. For instance, it checks if Tweet ID is in the correct format when reading/deleting and the text does not exceed the character limit (140 characters). After that, it will make the appropriate DAO instance call and return the results to the upper layer.

### DAO
The TwitterDao involves calling the twitter rest API v1.1 and mapping the response to the models/dto defined in the application where we use Jackson to map objects from HttpResponse to Data models. The TwitterHelper is used to return the HttpResponse required for TwitterDao object.

## Spring


# Test
For each main component in the application, unit testing and integration testing was written to sufficiently test their functionality and behaviour.

The workflow of the test class was controlled by JUnit. Assertion was used to compare the actual result with the predicted result for each @Test case/method.

For unit testing, Mockito was used to test each class separately. This was accomplished by mimicking the class dependencies so that, when called, they would produce the desired outcome.


# Deployment
The application was deployed as a Docker image on DockerHub to facilitate distribution. The image can be pulled using the docker command `docker pull` on the CLI.

# Improvements
* Allowing the user to also search for Tweets by keywords and then display it.
* Adding a scheduling tool and schedule Twitter posts in advance.
* Automatically ownloading tweets for further analysis.
