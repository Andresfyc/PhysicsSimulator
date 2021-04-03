package simulator.factories;

import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{

    public MassEqualStatesBuilder() {

        _typeTag="masseq";
        _desc="Mass Equal State";
    }

    @Override
    public JSONObject createData() {
        return null;
    }


    @Override
    protected StateComparator createTheInstance(JSONObject data) {
        return new MassEqualStates();
    }
}
