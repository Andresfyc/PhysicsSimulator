package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{


    public MovingTowardsFixedPointBuilder(String _typeTag, String _desc) {
        super("mtfp", "Moving Towards Fixed Point");
    }

    @Override
    JSONObject createData() {
        JSONObject data = new JSONObject();
        return data;
    }

    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        double g = data.has("_g") ? data.getDouble("_g") : 9.81;
        Vector2D c = data.has("c") ? data.getJSONArray("c") : (0,0);
        return new MovingTowardsFixedPoint(g,c);
    }
}
