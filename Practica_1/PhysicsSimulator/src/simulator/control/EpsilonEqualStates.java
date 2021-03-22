package simulator.control;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{

    double eps;

    public EpsilonEqualStates(double eps) {
        this.eps = eps;
    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {
        Vector2D v1;
        Vector2D v2;

        if (s1.get("time").equals(s2.get("time"))) return true;

        for (int i=0; i < s1.length(); i++){
            if (s1.get("id").equals(s2.get("id"))) return true;
            if (Math.abs(s1.getDouble("m") - s2.getDouble("m")) <= eps) return true;


            if (Math.abs(s1.getDouble("p") - s2.getDouble("p")) <= eps) return true;
            if (Math.abs(s1.getDouble("v") - s2.getDouble("v")) <= eps) return true;
            if (Math.abs(s1.getDouble("f") - s2.getDouble("f")) <= eps) return true;
        }

        return false;

    }
}
