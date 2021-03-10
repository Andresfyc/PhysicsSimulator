package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{

    protected double lossFactor;
    protected double lossFrequency;
    protected double c;

    public MassLossingBody(String id, Vector2D velocity, Vector2D position, double masa, double lossFactor, double lossFrequency) {
        super(id, velocity, position, masa);
        this.lossFactor = lossFactor;
        this.lossFrequency = lossFrequency;
        this.c = 0.0;
    }

    @Override
    void move(double t) {
        super.move(t);
        c += t;
        if (c >= lossFrequency ){
            mass=mass*(1-lossFactor);
            c = 0.0;
        }
    }
}
