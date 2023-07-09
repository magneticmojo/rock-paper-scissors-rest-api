package com.magneticmojo.rockpaperscissors.api.serialization.responses;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.api.model.responses.MakeMoveResponse;

import java.io.IOException;

public class MakeMoveResponseSerializer extends StdSerializer<MakeMoveResponse> {

    public MakeMoveResponseSerializer() {
        super(MakeMoveResponse.class);
    }

    @Override
    public void serialize(MakeMoveResponse makeMoveResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", makeMoveResponse.movePosition() + " move made");
        jsonGenerator.writeStringField("player", makeMoveResponse.playerMove().player().name());
        jsonGenerator.writeStringField("move", makeMoveResponse.playerMove().move().name());
        jsonGenerator.writeEndObject();

    }
}
