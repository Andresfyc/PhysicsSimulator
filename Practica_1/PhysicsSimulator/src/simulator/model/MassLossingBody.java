package simulator.model;

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
    void move(double t) {
        super.move(t);
        accumulatedTime += t;
        if (accumulatedTime >= lossFrequency ){
            mass=mass*(1-lossFactor);
            accumulatedTime  = 0.0;
        }
    }
}
