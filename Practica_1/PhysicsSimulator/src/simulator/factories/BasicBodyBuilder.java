package simulator.factories;

import com.sun.org.apache.xpath.internal.operations.String;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

    public BasicBodyBuilder() {
        super("basic", "Default Body");
    }

    @Override
    JSONObject createData() {
        return null;
    }

    @Override
    protected Body createTheInstance(JSONObject data) {
        return null;
    }
}
