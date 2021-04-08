package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class MassLossingBody extends Body{

    protected double lossFactor;
    protected double lossFrequency;
    protected double accumulatedTime;

    public MassLossingBody(String id, Vector2D velocity, Vector2D position, double mass, double lossFactor, double lossFrequency) {
        super(id, velocity, position, mass);
        this.lossFactor = lossFactor;
        this.lossFrequency = lossFrequency;
        this.accumulatedTime = 0.0;
    }

    @Override
    void move(double t) throws IllegalArgumentException {
        super.move(t);
        accumulatedTime += t;
        if (accumulatedTime >= lossFrequency ){
            mass=mass*(1-lossFactor);
            accumulatedTime  = 0.0;
        }
    }

    public double getLossFactor() {
        return lossFactor;
    }

    public double getLossFrequency() {
        return lossFrequency;
    }

    public JSONObject getState(){
        JSONObject jo=super.getState();
        jo.put("freq", getLossFrequency());
        jo.put("factor", getLossFactor());
        return jo;
}
}
