package com.magneticmojo.rockpaperscissors.api.serialization.responses;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.JoinGameResponse;

import java.io.IOException;

public class JoinGameResponseSerializer extends StdSerializer<JoinGameResponse> {


    protected JoinGameResponseSerializer() {
        super(JoinGameResponse.class);
    }

    @Override
    public void serialize(JoinGameResponse joinGameResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", "Player " + joinGameResponse.playerNumber() + " joined");
        jsonGenerator.writeStringField("player", joinGameResponse.player().name());
        jsonGenerator.writeStringField("gameID", joinGameResponse.gameId());
        jsonGenerator.writeEndObject();
    }
}
