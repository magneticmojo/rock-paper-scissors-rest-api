package com.magneticmojo.rockpaperscissors.api.serialization.gamestates;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.FirstMoveMadeState;

import java.io.IOException;

/**
 * Serializer class for converting the FirstMoveMadeState object to JSON format.
 * This class extends the StdSerializer class and is specifically designed to handle the serialization
 * of the FirstMoveMadeState class.
 */
public class FirstMoveMadeStateSerializer extends StdSerializer<FirstMoveMadeState> {

    public FirstMoveMadeStateSerializer() {
        super(FirstMoveMadeState.class);
    }

    @Override
    public void serialize(
            FirstMoveMadeState state,
            JsonGenerator gen,
            SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeStringField("message", state.getFirstPlayerMove().player().name() + " made the first move");
        gen.writeStringField("playerOne", state.getPlayerOne().name());
        gen.writeStringField("playerTwo", state.getPlayerTwo().name());
        gen.writeStringField("firstMoveBy", state.getFirstPlayerMove().player().name());
        gen.writeEndObject();
    }
}

