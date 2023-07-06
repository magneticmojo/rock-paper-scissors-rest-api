package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.PlayerOneJoinedState;

import java.io.IOException;

public class PlayerOneJoinedStateSerializer extends StdSerializer<PlayerOneJoinedState> { // TODO @TEST

    public PlayerOneJoinedStateSerializer() {
        this(null);
    }

    public PlayerOneJoinedStateSerializer(Class<PlayerOneJoinedState> t) {
        super(t);
    }

    @Override
    public void serialize(
            PlayerOneJoinedState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("message", state.playerOne().name() + " created new game");
        gen.writeStringField("playerOne", state.playerOne().name());
        gen.writeEndObject();
    }
}
