package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{


    public NoForceBuilder() {
    _typeTag="ng";
    _desc="No Force";

    }
//Para crear objetos de la clase

    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        return new NoForce();
    }
}
