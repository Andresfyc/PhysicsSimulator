package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends RuntimeException{

    private static final long serialVersionUID=1L;
    private JSONObject _actual;
    private JSONObject _expected;
    private JSONObject _ob1;
    private JSONObject _ob2;
    private int _step;

    public NotEqualStatesException(JSONObject exp, JSONObject act, JSONObject ob1, JSONObject ob2, int step) {
       super("States are different at step "+ step + System.lineSeparator() +
               "Actual: " + act + System.lineSeparator() +
               "Expected: " + exp + System.lineSeparator() +
               "Actual Body: " + ob1 + System.lineSeparator() +
               "Expected Body: " + ob2 + System.lineSeparator()
            );

       _actual=act;
       _expected=exp;
       _ob1=ob1;
       _ob2=ob2;
    }


    public JSONObject getActual() {
        return _actual;
    }

    public JSONObject getExpected() {
        return _expected;
    }

    public JSONObject getOb1() {
        return _ob1;
    }

    public JSONObject getOb2() {
        return _ob2;
    }

    public int getStep() {
        return _step;
    }
}
