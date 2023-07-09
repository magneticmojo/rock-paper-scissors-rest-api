package com.magneticmojo.rockpaperscissors.api.serialization.gamestates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerOneJoinedState;

import java.io.IOException;

/**
 * Serializer class for converting the PlayerOneJoinedState object to JSON format.
 * This class extends the StdSerializer class and is specifically designed to handle the serialization
 * of the PlayerOneJoinedState class.
 */
public class PlayerOneJoinedStateSerializer extends StdSerializer<PlayerOneJoinedState> {

    public PlayerOneJoinedStateSerializer() {
        super(PlayerOneJoinedState.class);
    }

    @Override
    public void serialize(
            PlayerOneJoinedState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("message", state.getPlayerOne().name() + " created new game");
        gen.writeStringField("playerOne", state.getPlayerOne().name());
        gen.writeEndObject();
    }
}
