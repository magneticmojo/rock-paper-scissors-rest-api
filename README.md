## Rock Paper Scissors API
The Rock Paper Scissors API is a RESTful API that provides a platform to play the 
classic game of Rock, Paper, Scissors (https://en.wikipedia.org/wiki/Rock_paper_scissors).
It is developed in Java 17 using Spring Boot 3.1.1 and Apache Maven 3.9.1 and designed to be a starting point 
for a larger project accessible for other developers to continue working on, adding functionality, endpoints or other games. 

#### The project is separated in two main packages:
* api - The API module. It contains the RESTful API for the Rock-Paper-Scissors game.

* services - The services package. It is intended to be extendable with more services in the future.
  * rockpaperscissors - The game package. It contains the service and other components of for the Rock Paper Scissors game.

### Pre-requisites
To run this project, you need to have:

* Java 17 or higher installed (https://www.java.com/download/ie_manual.jsp)
* Apache Maven 3.9.1 or higher installed (https://maven.apache.org/download.cgi)

## Building the Project
Clone the repository:

https://github.com/MagneticMojo/RockPaperScissorsRESTAPI.git

Build the project with Maven:
```
mvn clean install
```

## Running the Application
### Running with Maven: 
You can start the application directly from Maven:

```
mvn spring-boot:run
```

### Running as a Standalone JAR
You can package the application as a standalone JAR:
```
mvn clean package
```
The JAR file will be located in the target directory. You can run the JAR file with the following command from the project root directory:
```
java -jar target/RockPaperScissorsRESTAPI-0.0.1-SNAPSHOT.jar
```
### Compatibility and Testing
This project has been developed and tested on a MacBook Pro (14-inch, 2021) equipped with an Apple M1 Pro chip and 16GB of memory. 
The operating system used is macOS 13.3.1. While the project should work fine on other systems that meet the prerequisites (Java 17 and Apache Maven), 
it's worth noting that the testing so far has been performed on the aforementioned system. 
As such, if you're using a different setup, your mileage may vary. Contributions to test and improve compatibility across different platforms are welcome.


## API Usage
The API provides the following endpoints:

### /api/games 
Create a new game (POST request). 
### /api/games/{id} 
Get the state of a game (GET request).
### /api/games/{id}/join
Join a game (PATCH request).
### /api/games/{id}/move
Make a move (PATCH request).

## Example usage of the API:
### Create a new game
Endpoint: /api/games

The endpoint is used by the first player to create a new game. The request body should contain the name of the player creating the game.

Request body:
```
{
    "name": "Player One"
}
```

Response (Successful: Http status 201 CREATED):
```
{
    "message": "Rock-Papper-Scissors game created",
    "playerOne": "Player One",
    "gameId": "d42da742-9f7e-41ab-86d9-e2712473d623"
}
```
### Get the state of the game
Endpoint: 
/api/games/{id}

The endpoint is used by both players to check the current state of the game. 
Substitute {id} with the gameId from the response from the create game request. The request body should be empty.   
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623
```
Request body (empty):
```
```
Response (Successful: Http status 200 OK): 
```
{
    "gameState": "Player one created and joined game",
    "playerOne": "Player One"
}
```
### Join a game (second player)
Endpoint: /api/games/{id}/join

The endpoint is used by the second player to join the game. The request body should contain the name of the player joining the game.
Substitute {id} with the gameId from the response from the create game request.
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623/join
```
Request body: 
```
{
    "name": "Player Two"
}
```
Response (Successful: Http status 200 OK):
```
{
    "message": "Player two joined",
    "playerTwo": "Player Two"
}
```
### Check the current game state
Endpoint:
/api/games/{id}

The endpoint may be used intermittently by both players to check the current state of the game. 
At this occurrence the client making the request will see the confirmation that the second player has joined the game. 
Substitute {id} with the gameId from the response from the create game request. The request body should be empty.
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623
```
Request body (empty):
```
```
Response (Successful: Http status 200 OK):
```
{
    "gameState": "Player two joined",
    "playerOne": "Player One",
    "playerTwo": "Player Two"
}
```
### Make a move
Endpoint: 
/api/games/{id}/move

The endpoint is used by both players to make a move. sets the data to be sent in the request body. 
The JSON data in the request body represents a player named "Player One" making the move "ROCK". The move needs to be written in capital letters. 
Substitute {id} with the gameId from the response from the create game request. The request body should be empty.
The player making the move, whether it makes the first or last move, only sees the move made by itself in the response. 
A subsequent request to "api/games/{id}", for checking the current game state, will only show that the first move has been made, not the value of the move.
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623/move
```
Request body: 
```
{
    "player": {
        "name": "Player One"
    },
    "move": "ROCK"
}
```
Response (Successful: Http status 200 OK):
```
{
    "message": "First move made",
    "player": "Player One",
    "move": "ROCK"
}
```

### Make a move (second player)
Endpoint: /api/games/{id}/move

Substitute {id} with the gameId from the response from the create game request. The request body should be empty.
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623/move
```
Request body: 
```
{
    "player": {
        "name": "Player Two"
    },
    "move": "PAPER"
}
```
Response (Successful: Http status 200 OK):
```
{
    "message": "Last move made",
    "player": "Player Two",
    "move": "PAPER"
}
```
### Get the state final state of the game (GameEndedState)
Endpoint:
/api/games/{id}

Once both players have made a move, the game result will be visible in the response from the GET endpoint.
Substitute {id} with the gameId from the response from the create game request. The request body should be empty.
```
/api/games/d42da742-9f7e-41ab-86d9-e2712473d623
```
Request body (empty):
```
```
Response (Successful: Http status 200 OK):
```
{
    "gameState": "Game ended",
    "gameResult": "Player Two won by PAPER beating ROCK. Player One lost",
    "playerOne": "Player One",
    "playerTwo": "Player Two",
    "firstMoveBy": "Player One (ROCK)",
    "lastMoveBy": "Player Two (PAPER)"
}
```
## Unsuccessful Requests:
### POST: /api/games
##### Empty, blank or null value of key "name" in the request body will result in a 40O BAD REQUEST response.
```
{
    "HttpStatusCode": 400,
    "errors": [
        "must not be blank"
    ]
}
```
#### Invalid JSON format in the request body will result in a 40O BAD REQUEST response.
```
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Failed to read request",
    "instance": "/api/games"
}
```
### GET: /api/games/{id}
##### Empty gameId in the URL will result in a 404 NOT FOUND response.
```
{
    "timestamp": "2023-07-10T23:13:24.607+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/api/games/"
}
```
#### Incorrect gameId in the URL will result in a 404 NOT FOUND response.
```
{
"errorCode": "GAME_NOT_FOUND",
"errorMessage": "Invalid id: 1234"
}
```

### PATCH: /api/games/{id}/join
##### Empty, blank or null value of key "name" in the request body will result in a 40O BAD REQUEST response.
```
{
    "HttpStatusCode": 400,
    "errors": [
        "must not be blank"
    ]
}
```
#### Incorrect gameId in the URL will result in a 404 NOT FOUND response.
```
{
"errorCode": "GAME_NOT_FOUND",
"errorMessage": "Invalid id: 1234"
}
```
#### Invalid JSON format in the request body will result in a 40O BAD REQUEST response.
```
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Failed to read request",
    "instance": "/api/games/59e5b3fa-25cf-4b7a-99fa-392406f75198/join"
}
```
#### Joining a game that already has two players will result in a 409 CONFLICT response. 
```
{
    "errorCode": "GAME_FULL",
    "errorMessage": "Game full. Cannot join game"
}
```
#### Joining a game that has ended will result in a 409 CONFLICT response.
```
{
    "errorCode": "GAME_ENDED",
    "errorMessage": "Game ended. Cannot join game"
}
```
### PATCH: /api/games/{id}/move
##### Empty, blank or null value of keys values in the request body will result in a 40O BAD REQUEST response.
```
{
    "HttpStatusCode": 400,
    "errors": [
        "must not be blank"
    ]
}
```
#### Incorrect gameId in the URL will result in a 404 NOT FOUND response.
```
{
"errorCode": "GAME_NOT_FOUND",
"errorMessage": "Invalid id: 1234"
}
```
#### Invalid JSON format in the request body will result in a 40O BAD REQUEST response.
```
{
"type": "about:blank",
"title": "Bad Request",
"status": 400,
"detail": "Failed to read request",
"instance": "/api/games/59e5b3fa-25cf-4b7a-99fa-392406f75198/move"
}
```
#### Making a move in a game without a second player will result in a 409 CONFLICT response.
```
{
    "errorCode": "MISSING_PLAYER_TWO",
    "errorMessage": "Move prohibited. Player two not joined"
}
```
#### Player not in the game making a move will result in a 409 CONFLICT response.
```
{
    "errorCode": "PLAYER_NOT_IN_GAME",
    "errorMessage": "Player not in game. Cannot make move"
}
```
#### Player in game making a second move will result in a 409 CONFLICT response.
```
{
    "errorCode": "MULTIPLE_MOVES_PROHIBITED",
    "errorMessage": "Player already made move. Cannot make another move"
}
```
#### Making a move in a game that has ended will result in a 409 CONFLICT response.
```
{
    "errorCode": "GAME_ENDED",
    "errorMessage": "Game ended. Cannot make move"
}
```

## Curl commands for using the API to play a game

### Setup 
Start two terminals. In the first terminal locate the directory of the project and start the server by running the command:
```
mvn spring-boot:run
```
In the second terminal run the following commands to create a new game, join the game, make a move and check the state of the game.

### Commands
Substitute {id} with the gameId from the response from the create game request. Choose name and moves to make inside the curly brackets.
### Create a new game
First player makes request to create and join a new game.
```
 curl -X POST -H "Content-Type: application/json" -d '{"name":"playerOne"}' http://localhost:8080/api/games
```
### Get the state of the game
Replace {id} with the gameId from the response from the create game request.
```
curl -X GET -H "Content-Type: application/json" http://localhost:8080/api/games/{id}
```
### Join a game
Replace {id} with the gameId from the response from the create game request. Second player joins the game.
``` 
curl -X PATCH -H "Content-Type: application/json" -d '{"name": "playerTwo"}' http://localhost:8080/api/games/{id}/join
```
### Make a move
Replace {id} with the gameId from the response from the create game request. Same command for both players.
```
curl -X PATCH -H "Content-Type: application/json" -d '{"player": {"name": "playerOne"}, "move": "ROCK"}' http://localhost:8080/api/games/{id}/move
```
```
curl -X PATCH -H "Content-Type: application/json" -d '{"player": {"name": "playerTwo"}, "move": "SCISSORS"}' http://localhost:8080/api/games/{id}/move
```

## Areas of Improvement

### API and domain model design 
A winner attribute could be added for clarity in addition to the gameResult attribute. Functionality for returning a playerNumber (for move request responses) 
could be added. This would make it clearer in the response to the client which player number the player making a move has. 
RockPaperScissorsGameState implementations could have the gameId as a member field. The gameId value could then be included in the responses from the GET endpoint. 
Implementing a conversion to capitalize the move value received from the request body could make the endpoint for making moves be more fail-safe from the client's point of view.

#### Endpoint Responses
Some responses from the endpoints could be more detailed and descriptive. The responses could include more information. 
Especially the unsuccessful responses. Also, the formatting of the unsuccessful responses for invalid JSON format and 
"Empty, blank or null" could be more coherent with the other responses. 

#### Unique player id
A UUID could be used to identify players, instead of the player name. This would make it possible
to have both players with the same exact name in one game. I could also be good for future persistence for 
identification of returning players.

#### Security - More request input validation
With other future persistence mechanism in place, expanded input validation could be used to guard against 
e.g. injection attacks.

#### DTOs
More DTOs could be used to separate the API from the domain model. This would make it easier to
change the domain model without affecting the API. In the current implementation such classes were avoided to keep the code 
simple and concise.

#### Message Source (Spring)
Could be used for internationalization of messages. And to centralize the handling of response and exception messages.

#### Exception Handling
The exception handling for @Validated and MethodArgumentNotValidException could be more specific. 
PlayerNullException and PlayerMoveNullException are not handled by the exception handler. The reason for this is that 
the @Validated annotation is used in the controller class. Therefore, in the current implementation, with the API as an interface, 
those exceptions are not thrown. But with another interface communicating directly with the service class, they would be needed.

### Logging
No logging functionality is implemented. This could greatly aid in the future development of the API.
Logging could be used for exceptions and errors, and requests and responses. Additional information besides the error codes 
could be added to the exception classes for more detailed logging. Time stamps could be added to the logs for easier debugging.

### Testing
A refactoring of some components would make it easier to test to the full extent. 
More automated tests could be added, with a wider coverage, and written in a more consistent style. 
Integration testning was done with Postman. Perhaps this could have been done with JUnit instead. To keep everything in one place.

### Documentation
Only class-level short, concise comments are used. More detailed comments and javadoc method comments
could be used to make the code more readable and understandable. Not the least if the API grows.
However, my aim has been to write the code in a way that should be easy to understand without extensive comments.  