package ro.utcluj.intermediar;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestMessageTest {

    @Test
    void getRequestName() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        String str = r.getRequestName();

        assertEquals("A", str);
    }

    @Test
    void setRequestName() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        r.setRequestName("AC");
        String str = r.getRequestName();

        assertEquals("AC", str);
    }

    @Test
    void getModelName() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        String str = r.getModelName();

        assertEquals("B", str);
    }

    @Test
    void setModelName() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        r.setModelName("BC");
        String str = r.getModelName();

        assertEquals("BC", str);
    }

    @Test
    void getArgs() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);

        List<String> test = r.getArgs();

        assertEquals(args, test);
    }

    @Test
    void setArgs() {
        List<String> args = new ArrayList<>();
        args.add("A");
        RequestMessage r = new RequestMessage("A", "B", args);
        args.add("B");

        r.setArgs(args);
        List<String> test = r.getArgs();

        assertEquals(args, test);
    }
}