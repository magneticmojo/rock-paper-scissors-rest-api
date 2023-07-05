package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.GameActiveState;

import java.io.IOException;

public class GameActiveStateSerializer extends StdSerializer<GameActiveState> { // TODO @TEST

    public GameActiveStateSerializer() {
        this(null);
    }

    public GameActiveStateSerializer(Class<GameActiveState> t) {
        super(t);
    }

    @Override
    public void serialize(
            GameActiveState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("msg", "MOVE MADE. GAME IS ACTIVE");
        gen.writeStringField("playerOne", state.playerOne().name());
        gen.writeStringField("playerTwo", state.playerTwo().name());
        gen.writeStringField("firstPlayerMove", state.firstPlayerMove().player().name() + " MADE ITS MOVE");
        gen.writeEndObject();
    }
}

