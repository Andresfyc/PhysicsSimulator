package simulator.factories;

import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{

    public MassEqualStatesBuilder(String _typeTag, String _desc) {
        super("mes", "Mass Equal State");
    }

    @Override
    JSONObject createData() {
        return null;
    }


    @Override
    protected StateComparator createTheInstance(JSONObject data) {
        return new MassEqualStates();
    }
}
