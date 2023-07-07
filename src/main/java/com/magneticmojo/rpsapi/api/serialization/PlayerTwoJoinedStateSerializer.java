package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.PlayerTwoJoinedState;

import java.io.IOException;

/**
 * Serializer class for converting the PlayerTwoJoinedState object to JSON format.
 * This class extends the StdSerializer class and is specifically designed to handle the serialization
 * of the PlayerTwoJoinedState class.
 */
public class PlayerTwoJoinedStateSerializer extends StdSerializer<PlayerTwoJoinedState> {

    public PlayerTwoJoinedStateSerializer() {
        super(PlayerTwoJoinedState.class);
    }

    @Override
    public void serialize(
            PlayerTwoJoinedState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("message", state.playerTwo().name() + " joined game");
        gen.writeStringField("playerOne", state.playerOne().name());
        gen.writeStringField("playerTwo", state.playerTwo().name());
        gen.writeEndObject();
    }
}

