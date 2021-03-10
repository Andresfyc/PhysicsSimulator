package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    ForceLaws lawForce;
    double realTimeStep;
    double currentTime= 0.0;
    List<Body> listBody = new ArrayList<Body>();

    public PhysicsSimulator(ForceLaws lawForce, double realTimeStep) {
        this.lawForce = lawForce;
        this.realTimeStep = realTimeStep;
    }

    public void advance() throws IllegalArgumentException {

        for (Body b: listBody) {
            b.resetForce();
        }

        lawForce.apply(listBody);

        for (Body bs: listBody) {
            bs.move(realTimeStep);
            currentTime+=realTimeStep;
        }

    }

    public void addBody(Body b) throws IllegalArgumentException {

        for (Body bs: listBody) {
            if (b.getId() == bs.getId()){

            }else{
                listBody.add(b);
            }
        }

    }

    JSONObject state;
    {
        state = new JSONObject();
        state.put("time", currentTime);
        state.put("bodies", listBody);

    }

    public JSONObject getState() {
        return state;
    }

    public String toString() {
        return getState().toString();
    }
}
