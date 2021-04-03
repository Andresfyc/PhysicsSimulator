package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{


    public NoForceBuilder() {
    _typeTag="nf";
    _desc="No Force";

    }


    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        return new NoForce();
    }
}
