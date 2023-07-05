package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.GameCreatedState;

import java.io.IOException;

public class GameCreatedStateSerializer extends StdSerializer<GameCreatedState> { // TODO @TEST

    public GameCreatedStateSerializer() {
        this(null);
    }

    public GameCreatedStateSerializer(Class<GameCreatedState> t) {
        super(t);
    }

    @Override
    public void serialize(
            GameCreatedState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("msg", "PLAYER ONE CREATED AND JOINED GAME");
        gen.writeStringField("playerOne", state.playerOne().name());
        gen.writeEndObject();
    }
}
