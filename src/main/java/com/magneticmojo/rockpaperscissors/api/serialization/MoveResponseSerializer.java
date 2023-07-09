package com.magneticmojo.rockpaperscissors.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.api.model.responses.MoveResponse;

import java.io.IOException;

public class MoveResponseSerializer extends StdSerializer<MoveResponse> {

    public MoveResponseSerializer() {
        super(MoveResponse.class);
    }

    @Override
    public void serialize(MoveResponse moveResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("message", moveResponse.movePosition() + " move made");
        jsonGenerator.writeStringField("player", moveResponse.playerMove().player().name());
        jsonGenerator.writeStringField("move", moveResponse.playerMove().move().name());
        jsonGenerator.writeEndObject();

    }
}
