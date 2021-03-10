package simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

    public MassEqualStates() {
    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        if (s1.get("time").equals(s2.get("time"))) return true;

        for (int i=0; i < s1.length(); i++){
            if ((s1.get("id").equals(s2.get("id"))) && (s1.get("mass").equals(s2.get("mass")))){
                return true;
            }
        }

        return false;
    }
}
