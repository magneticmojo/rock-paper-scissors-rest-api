package com.example.rpsapi.api.model.entities;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// GAME SHOULD KEEP TRACK OF EVERYTHING THAT HAPPENS IN THE GAME

// TODO -> NULL CHECKS EVERYWHERE?
public class RPSGame {

    private static final int MAX_PLAYERS = 2;
    private static final int PLAYER_NUMBER_ONE = 1;
    private static final int PLAYER_NUMBER_TWO = 2;
    private String playerOneName;
    private String playerTwoName;
    private String playerOnePlayerID;
    private String playerTwoPlayerID;
    private final String gameId;
    private boolean isGameActive;
    private boolean isGameFinished;
    private String winner;
    private final ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();

    public RPSGame(Player player) {
        this.gameId = UUID.randomUUID().toString();
        addPlayer(player);
    }

    public String getGameID() {
        return gameId;
    }

    public void addPlayer(Player player) {
        if (players.isEmpty()) {
            player.setPlayerNumber(PLAYER_NUMBER_ONE);
            players.put(player.getPlayerID(), player);
            playerOneName = player.getName();
            playerOnePlayerID = player.getPlayerID();
        } else if (players.size() <= MAX_PLAYERS) {
            player.setPlayerNumber(PLAYER_NUMBER_TWO);
            players.put(player.getPlayerID(), player);
            playerTwoName = player.getName();
            playerTwoPlayerID = player.getPlayerID();
            setGameActive(true);
        }
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public String getPlayerOnePlayerID() {
        return playerOnePlayerID;
    }

    public String getPlayerTwoPlayerID() {
        return playerTwoPlayerID;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean isGameActive) {
        this.isGameActive = isGameActive;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public Player getPlayerBy(String playerID) {
        return players.get(playerID);
    }

    public boolean hasPlayer(String playerID) {
        return players.containsKey(playerID);
    }

    public boolean isPlayerOne(String playerID) {
        return players.get(playerID).getPlayerNumber() == PLAYER_NUMBER_ONE;
    }

    public boolean hasPlayerMadeMove(String playerID) {
        return players.get(playerID).hasMove();
    }

    public boolean hasBothPlayersMadeMove() {
        return players.values().stream().allMatch(Player::hasMove);
    }

    public boolean hasNoPlayerMadeMove() {
        return players.values().stream().noneMatch(Player::hasMove);
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner() {
        if (!isGameFinished) {
            throw new IllegalStateException("The game has not finished yet. The winner cannot be determined.");
        }

        Player playerOne = null;
        Player playerTwo = null;

        for (Player player : players.values()) {
            if (player.getPlayerNumber() == 1) {
                playerOne = player;
            } else if (player.getPlayerNumber() == 2) {
                playerTwo = player;
            }
        }

        // todo -> USE ASSERTIONS?
        if (playerOne == null || playerTwo == null) {
            throw new IllegalStateException("The game does not have two players. The winner cannot be determined.");
        }

        Move.Result result = playerOne.getMove().against(playerTwo.getMove());

        switch (result) {
            case WIN -> winner = playerOne.getPlayerID();
            case LOSE -> winner = playerTwo.getPlayerID();
            case DRAW -> winner = "DRAW";
        }
    }

    // get game state

}
