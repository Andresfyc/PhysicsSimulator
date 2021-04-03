package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{


    public MovingTowardsFixedPointBuilder() {
        _typeTag="mtfp";
        _desc="Moving Towards Fixed Point";
    }


    @Override
    protected ForceLaws createTheInstance(JSONObject data) {

        JSONArray V=data.getJSONArray("c");
        Vector2D c = new Vector2D(V.getDouble(0),V.getDouble(1));
        double g = data.has("_g") ? data.getDouble("_g") : 9.81;
        //Vector2D c = data.has("c") ? data.getJSONArray("c") : [0,0]);

        return new MovingTowardsFixedPoint(g,c);
    }

    @Override
    public JSONObject createData() {
        JSONObject ob=new JSONObject();
        JSONArray data = new JSONArray();

        data.put(0.0);
        data.put(0.0);
        ob.put("c",data);

        ob.put("g",9.81);
        return ob;
    }
}
