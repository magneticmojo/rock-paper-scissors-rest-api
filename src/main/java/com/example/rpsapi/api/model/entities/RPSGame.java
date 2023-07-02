package com.example.rpsapi.api.model.entities;

// GAME SHOULD KEEP TRACK OF EVERYTHING THAT HAPPENS IN THE GAME
public class RPSGame {

    private int id;
    // todo -> should be a list of players -> List<Player> players = new ArrayList<>();
    private Player player1;
    private Player player2;

    // todo -> create AL and add players to it
    public RPSGame(Player player1) {
        this.player1 = player1;
    }

    // todo -> Maybe the RPSGame -> Create id?
    public void setId(int id) {
        this.id = id;
    }

    // todo -> getPlayer(name) instead of this?
    public Player getPlayer1() {
        return player1;
    }

    // todo -> getPlayer(name) instead of this?
    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getId() {
        // todo -> unsafe since the id might not be set
        return id;
    }

    // todo -> is game full? Better bc can be extended to more than 2 players
    public boolean hasPlayer2Joined() {
        return player2 != null;
    }

    public boolean hasPlayer(String name) {
        // todo -> what if they have the same name?
        return player1.getName().equals(name) || player2.getName().equals(name);
    }
}
