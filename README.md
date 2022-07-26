# Github Rest Api
Simple REST API that returns user and repositories data.

## Table of Contents
* [General info](#general-info)
* [Authorization](#authorization)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [First Steps](#first-steps)


## General Info
I created this project to learn how to build Rest API with spring boot.
Overall it is proxy API which shows on one endpoint data which original GitHub API shows on couple endpoints.
Also, it makes a recap of user languages and languages per repository.


## Technologies Used
* Spring
* Java 17
* Mockito
* JUnit

## Authorization
GitHub limits connections to API for one user to 60 per hour. It can be
increased by using personal token, so in my project you can use it. [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) 
is instruction how to generate token. After generating just add header `Auth` with the token.

## Setup
*This app requires Java 17*
1. **Open project**
\
Clone repository, paste personal token to resources/application.properties and run project.
2. **Package**
\
[Download](https://github.com/bladeours/allegro-summer-experience-2022/releases) 
package with jar and config file then run it from command line
\
`java -jar restApi.jar`
\
\
Now you can simply send request from web browser or any other tool.
## First Steps
There is two endpoints to get data, one for user and one for repositories info.


### User 
GET: `localhost:8080/users/{username}`\
Header: `Auth: personalToken`\
Return:
* Bio
* Login
* Name
* Number of bytes per language

Here is sample output:
\
`curl -H "Auth: personalToken" localhost:8080/users/bladeours`
```
{
  "bio": "IT student and DevOps engineer.",
  "login": "bladeours",
  "name": "Rafal Wisniewski",
  "languages": {
    "Java": 88967,
    "CSS": 14750
  }
}
```

### Repositories
GET: `localhost:8080/user-repositories/{username}`\
Header: `Auth: personalToken`\
Return:
* list of repositories
* languages per repository

Here is sample output:\
`curl -H "Auth: personalToken" localhost:8080/user-repositories/bladeours`
```
[
  {
    "name": "homeBudget",
    "languages": {
      "Java": 67906,
      "CSS": 14750
    }
  },
  {
    "name": "Test",
    "languages": {
      "Java": 22912
    }
  }
]
```

