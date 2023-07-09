package com.magneticmojo.rockpaperscissors.api.serialization.gamestates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;

import java.io.IOException;

/**
 * Serializer class for converting the GameEndedState object to JSON format.
 * This class extends the StdSerializer class and is specifically designed to handle the serialization
 * of the GameEndedState class.
 */
public class GameEndedStateSerializer extends StdSerializer<GameEndedState> {

    public GameEndedStateSerializer() {
        super(GameEndedState.class);
    }

    @Override
    public void serialize(
            GameEndedState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("message", "Game ended");
        gen.writeStringField("playerOne", state.getPlayerOne().name());
        gen.writeStringField("playerTwo", state.getPlayerTwo().name());
        gen.writeStringField("firstMoveBy", state.getFirstPlayerMove().player().name() + " (" + state.getFirstPlayerMove().move().name() + ")");
        gen.writeStringField("lastMoveBy", state.getLastPlayerMove().player().name() + " (" + state.getLastPlayerMove().move().name() + ")");
        gen.writeStringField("gameResult", state.getGameResult());
        gen.writeEndObject();
    }
}
