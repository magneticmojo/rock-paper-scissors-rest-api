package com.magneticmojo.rockpaperscissors.api.serialization.responses;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.CreateGameResponse;

import java.io.IOException;

public class CreateGameResponseSerializer extends StdSerializer<CreateGameResponse> {

    protected CreateGameResponseSerializer() {
        super(CreateGameResponse.class);
    }

    @Override
    public void serialize(CreateGameResponse createGameResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", "Rock-Papper-Scissors game created");
        jsonGenerator.writeStringField("gameId", createGameResponse.id());
        jsonGenerator.writeEndObject();

    }
}
