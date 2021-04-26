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

    //Para crear objetos de la clase
    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        Vector2D c;
        if (data.has("c")) {
            JSONArray vector = data.getJSONArray("c");
            c = new Vector2D(vector.getDouble(0), vector.getDouble(1));
        }else{
            c=new Vector2D();
        }

      double g = data.has("g") ? data.getDouble("g") : 9.81;


        return new MovingTowardsFixedPoint(g,c);
    }

    @Override
    public JSONObject createData() {
        JSONObject data= new JSONObject();
        data.put("c","The point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
        data.put("g","The length of the acceleration vector (a number)");
        return data;
    }
}
