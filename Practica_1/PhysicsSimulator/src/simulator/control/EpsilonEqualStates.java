package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{

    double eps;

    public EpsilonEqualStates(double eps) {
        this.eps = eps;
    }

    //Compara los estados
    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        if(s1.getDouble("time")!= s2.getDouble("time")) return false;
        JSONArray s1_array=s1.getJSONArray("bodies");
        JSONArray s2_array=s2.getJSONArray("bodies");
        Vector2D v1=new Vector2D();
        Vector2D v2=new Vector2D();

        if(s2_array.length()!=s1_array.length()) return false;

        for(int i=0; i<s1_array.length();i++){
            if (!s1_array.getJSONObject(i).getString("id").equals(s2_array.getJSONObject(i).getString("id"))) return false;
            if(Math.abs(s1_array.getJSONObject(i).getDouble("m")-s2_array.getJSONObject(i).getDouble("m"))>eps) return false;
            v1=new Vector2D(s1_array.getJSONObject(i).getJSONArray("p").getDouble(0),s1_array.getJSONObject(i).getJSONArray("p").getDouble(1));
            v2=new Vector2D(s2_array.getJSONObject(i).getJSONArray("p").getDouble(0),s2_array.getJSONObject(i).getJSONArray("p").getDouble(1));

            if(v1.distanceTo(v2)>eps) return false;

            v1=new Vector2D(s1_array.getJSONObject(i).getJSONArray("v").getDouble(0),s1_array.getJSONObject(i).getJSONArray("v").getDouble(1));
            v2=new Vector2D(s2_array.getJSONObject(i).getJSONArray("v").getDouble(0),s2_array.getJSONObject(i).getJSONArray("v").getDouble(1));

            if(v1.distanceTo(v2)>eps) return false;

            v1=new Vector2D(s1_array.getJSONObject(i).getJSONArray("f").getDouble(0),s1_array.getJSONObject(i).getJSONArray("f").getDouble(1));
            v2=new Vector2D(s2_array.getJSONObject(i).getJSONArray("f").getDouble(0),s2_array.getJSONObject(i).getJSONArray("f").getDouble(1));

            if(v1.distanceTo(v2)>eps) return false;
        }
        return true;
    }
}
