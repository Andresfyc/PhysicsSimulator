package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    static private final double g=9.81;

    public MovingTowardsFixedPoint() {
        super();
    }

    @Override
    public void apply(List<Body> bs) {


        for (Body b: bs ) {

            //b.addForce(c.minus(b.getPosition()).direction().scale(g*b.getMass()));
        }

    }

    @Override
    public String toString() {
        return "Moving Towards Fixed Point";
    }
}


