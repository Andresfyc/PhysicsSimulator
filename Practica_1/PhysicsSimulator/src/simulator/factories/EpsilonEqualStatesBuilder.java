package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

    public EpsilonEqualStatesBuilder() {
        _typeTag="epseq";
        _desc="Epsilon Equal State Comparator";

    }
    //Para crear objetos de la clase
    @Override
    public JSONObject createData() {
        JSONObject data = new JSONObject();
        data.put("eps", "the allowed error");
        return data;
    }

    @Override
    protected StateComparator createTheInstance(JSONObject jsonObject) {
        double eps = jsonObject.has("eps") ? jsonObject.getDouble("eps") : 0.1;
        return new EpsilonEqualStates(eps);
    }
}
