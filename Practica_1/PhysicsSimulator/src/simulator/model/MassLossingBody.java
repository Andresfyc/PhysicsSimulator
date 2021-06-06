package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class MassLossingBody extends Body{

    //un número entre 0 y 1 que representa el factor de pérdida de masa.
    protected double lossFactor;

    //un número positivo que indica el intervalo de tiempo (en
    //segundos) después del cual el objeto pierde masa
    protected double lossFrequency;

    //tiempo acumulado
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
            // la nueva masa será m ∗ (1 − lossFactor)
            mass=mass*(1-lossFactor);
            // tiempo acumulado en 0.
            accumulatedTime  = 0.0;
        }
    }

    //Devuelve la perdida de la masa
    public double getLossFactor() {
        return lossFactor;
    }

    //devuelve el tiempo despues del cual el objeto pierde masa
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
