package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

    public EpsilonEqualStatesBuilder(String _typeTag, String _desc) {
        super("epseq", "Epsilon Equal State Comparator");
    }

    @Override
    JSONObject createData() {
        JSONObject data = new JSONObject();
        data.put("eps", "the allowed error");
        return data;
    }

    @Override
    protected StateComparator createTheInstance(JSONObject jsonObject) {
        double eps = jsonObject.has("eps") ? jsonObject.getDouble("eps") : 0.0;
        return new EpsilonEqualStates(eps);
    }
}
