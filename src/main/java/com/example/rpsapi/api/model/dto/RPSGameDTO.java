package com.example.rpsapi.api.model.dto;

// todo -> PlayerDTO inside RPSGameDTO or String?
// todo -> RPSGAMDTO should have a Map with the information of the game
public record RPSGameDTO(String id, String player1, String player2) {
}
