package com.magneticmojo.rockpaperscissors.api.serialization.responses;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.JoinGameResponse;

import java.io.IOException;

/**
 * Serializer class for converting the JoinGameResponse object to JSON format.
 */
public class JoinGameResponseSerializer extends StdSerializer<JoinGameResponse> {


    protected JoinGameResponseSerializer() {
        super(JoinGameResponse.class);
    }

    @Override
    public void serialize(JoinGameResponse joinGameResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", "Player two joined");
        jsonGenerator.writeStringField("playerTwo", joinGameResponse.player().name());
        jsonGenerator.writeEndObject();
    }
}
