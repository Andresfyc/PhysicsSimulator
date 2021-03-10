package simulator.model;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    static private final double g=9.81;

    public MovingTowardsFixedPoint() {
        super();
    }

    @Override
    public void apply(List<Body> bs) {

        for(int i=0; i<bs.size(); i++){
            bs.get(i).setAceleracion(bs.get(i).getAceleracion().direction().scale(-g)); //a=-g*di

        }
    }

    @Override
    public String toString() {
        return "Moving Towards Fixed Point";
    }
}
