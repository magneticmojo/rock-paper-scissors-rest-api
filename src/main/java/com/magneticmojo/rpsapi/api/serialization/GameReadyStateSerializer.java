package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.GameReadyState;

import java.io.IOException;
// TODO HANDLE IOEXCEPTION
public class GameReadyStateSerializer extends StdSerializer<GameReadyState> {

    public GameReadyStateSerializer() {
        this(null);
    }

    public GameReadyStateSerializer(Class<GameReadyState> t) {
        super(t);
    }

    @Override
    public void serialize(
            GameReadyState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("msg", "PLAYER TWO JOINED. GAME IS READY");
        gen.writeStringField("playerOne", state.playerOne().name());
        gen.writeStringField("playerTwo", state.playerTwo().name());
        gen.writeEndObject();
    }
}

