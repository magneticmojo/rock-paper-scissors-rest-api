package com.magneticmojo.rpsapi.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rpsapi.api.state.MoveMadeAwaitingLastMoveState;

import java.io.IOException;

public class MoveMadeAwaitingLastMoveStateSerializer extends StdSerializer<MoveMadeAwaitingLastMoveState> { // TODO @TEST

    public MoveMadeAwaitingLastMoveStateSerializer() {
        this(null);
    }

    public MoveMadeAwaitingLastMoveStateSerializer(Class<MoveMadeAwaitingLastMoveState> t) {
        super(t);
    }

    @Override
    public void serialize(
            MoveMadeAwaitingLastMoveState state,
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

