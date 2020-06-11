package ro.utcluj.intermediar.actions;

import org.junit.jupiter.api.Test;
import ro.utcluj.intermediar.RequestMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TransformTest {

    @Test
    void serialize() {
        String aux = "AUX";

        String test = Transform.serialize((Object) aux);

        assertNotEquals(null, test);
    }

    @Test
    void desReqM() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        String res = Transform.serialize(r);
        RequestMessage rm = Transform.desReqM(res);
        String com = rm.getModelName();

        assertEquals("B", com);
    }

}