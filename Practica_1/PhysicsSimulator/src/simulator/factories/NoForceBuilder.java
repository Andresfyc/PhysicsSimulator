package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{


    public NoForceBuilder(String _typeTag, String _desc) {

        super("nf", "No Force");
    }

    @Override
    JSONObject createData() {
        return null;
    }

    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        return new NoForce();
    }
}
