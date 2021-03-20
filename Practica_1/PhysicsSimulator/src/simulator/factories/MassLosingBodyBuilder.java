package simulator.factories;

import org.json.JSONObject;
import simulator.model.Body;

public class MassLosingBodyBuilder extends Builder<Body>{


    public MassLosingBodyBuilder(String basic, String default_body) {
        super("mlb", "Mass losing Body");
    }

    @Override
    JSONObject createData() {
        return null;
    }

    @Override
    protected Body createTheInstance(JSONObject jsonObject) {
        return null;
    }
}
