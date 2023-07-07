##Rock Paper Scissors API
The Rock Paper Scissors API is a RESTful API that provides a platform to play the 
classic game of Rock, Paper, Scissors. 
It is developed in Java using Spring Boot and designed to be a starting point 
for a larger project that others should be able to continue working on.

###Pre-requisites
To run this project, you need to have:

Java 17 on your machine

Apache Maven 

##Building the Project
Clone the repository:

https://github.com/MagneticMojo/RockPapperScissors.git

Build the project:
With Maven:
```
mvn clean install
```

##Running the Application
Running with Maven: 
You can start the application directly from Maven:

```
mvn spring-boot:run
```

###Running as a Standalone JAR
You can also package the application as a standalone JAR and run it:

```
java -jar target/rpsapi-0.0.1-SNAPSHOT.jar
```
Replace rpsapi-0.0.1-SNAPSHOT.jar with the actual name of the JAR file.

##API Usage
The API provides the following endpoints:

#### POST /api/games : Create a new game.
#### GET /api/games/{id} : Get the state of a game.
#### PATCH /api/games/{id}/join : Join a game.
#### PATCH /api/games/{id}/moves : Make a move.

##Example usage of the API:
### Create a new game
POST /api/games

Request: 
```
{
    "name": "Player 1"
}
```

Response:
```
{
    "message": "Rock-Papper-Scissors game created",
    "id": "947b4c85-2b5b-46f8-976c-f2c6ad538a60"
}
```
### Get the state of a game
GET /api/games/{id}

Request: 
```
{
    "id": "947b4c85-2b5b-46f8-976c-f2c6ad538a60"
}
```
Response: 
```
{
    "message": "Player 1 created new game",
    "playerOne": "Player One"
}
```
### Join a game
PATCH /api/games/{id}/join
Request: 
```
{
    "name": "Player 2"
}
```
Response: 
```
{
    "message": "Player 2 joined game",
    "playerOne": "Player 1",
    "playerTwo": "Player 2"
}
```
### Make a move
PATCH /api/games/{id}/move
Request: 
```
{
    "player": {
        "name": "Player 1"
    },
    "move": "ROCK"
}

```
Response: 
```
{
    "message": "Player 1 made the first move",
    "playerOne": "Player 1",
    "playerTwo": "Player 2",
    "firstMoveBy": "Player 1"
}
```

### Make a move (second player)
PATCH /api/games/{id}/move
Request: 
```
{
    "player": {
        "name": "Player 2"
    },
    "move": "PAPER"
}

```
Response: 
```
{
    "message": "Game ended",
    "playerOne": "Player 1",
    "playerTwo": "Player 2",
    "firstMoveBy": "Player 1 (ROCK)",
    "lastMoveBy": "Player 2 (PAPER)",
    "gameResult": "Player 2 won by PAPER beating ROCK. Player 1 lost"
}
```

## Areas of Improvement

### API

#### Specialize responses to client
In the current implementation, both participating client receive the same information 
in the response body for each request. Move is shown to none of them, and an improvement would 
be to implement functionality for reveling the client's own move to the client, and hiding 
the opponent's move from the client.

#### Last Move gets Game Ended information directly
I would have been nice for the client to first see the Move made, then upon making a GET request 
receiving the response of the game results. However, this would probably involve adding 
more concrete state records. 

#### Unique player id
A UUID could be used to identify players, instead of the player name. This would make it possible
to have both players with the same exact name. I could also be good for future persistence for 
identification of returning players.

#### Security - More request input validation
With a future persistence mechanism in place, input validation could be used to guard from 
injection attacks.

#### DTOs
More DTOs could be used to separate the API from the domain model. This would make it easier to
change the domain model without affecting the API.

#### Exception Handling
The exception handling for @Validated and MethodArgumentNotValidException could be more specified.

#### Message Source (Spring)
Could be used for internationalization of messages. And to centralize the handling of response and exception messages.

### Documentation
Only class-level short, concise comments are used. More detailed comments and javadoc method comments 
could be used to make the code more readable and understandable. Not the least if the API grows. 
However, the code is written in a way that should be easy to understand without comments.  

### Logging
No logging functionality is implemented. This could greatly aid in the future development of the API.
Logging could be used for exceptions and errors, and requests and responses. Error codes could be added to the 
exception classes for more detailed logging. Time stamps could be added to the logs for easier debugging.

### Testing
A refactoring of some of the components would make it easier to test to the full extent. 
I am learning TDD, but did not use it for this project due to time constraints and the ease of old habits. 
If it would have been used from the start, the code would definitely have been more testable, and probably more robust and safe.

More automated tests could be added, with a wider coverage. 

Integration testning was not done. 
