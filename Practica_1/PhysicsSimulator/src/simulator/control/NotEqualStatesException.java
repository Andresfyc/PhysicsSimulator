package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends RuntimeException{

    private static final long serialVersionUID=1L;
    private JSONObject _actual;
    private JSONObject _expected;

    private int _step;

    public NotEqualStatesException(JSONObject exp, JSONObject act, int step) {
       super("States are different at step "+ step + System.lineSeparator() +
               "Actual:   " + act + System.lineSeparator() +
               "Expected: " + exp + System.lineSeparator()
            );

       _actual=act;
       _expected=exp;
       _step = step;
    }


    public JSONObject getActual() {
        return _actual;
    }

    public JSONObject getExpected() {
        return _expected;
    }


    public int getStep() {
        return _step;
    }
}
