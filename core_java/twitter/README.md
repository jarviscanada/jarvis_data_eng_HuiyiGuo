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

## Spring


# Test


# Deployment
The application was deployed as a Docker image on DockerHub to facilitate distribution. The image can be pulled using the docker command `docker pull` on the CLI.

# Improvements
* Allowing the user to also search for Tweets by keywords and then display it.
* Adding a scheduling tool and schedule Twitter posts in advance.
* Automatically ownloading tweets for further analysis.
