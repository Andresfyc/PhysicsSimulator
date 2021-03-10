package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double g=9.81;
    private Vector2D _c;


    public MovingTowardsFixedPoint(double g, Vector2D c) {
        this.g = g;
        //this._c = _c;
        _c =new Vector2D(c);
    }

    @Override
    public void apply(List<Body> bs) {

        for (Body b: bs ) {
            b.addForce(_c.minus(b.getPosition()).direction().scale(g*b.getMass()));
        }

    }

    @Override
    public String toString() {
        return "Moving Towards Fixed Point";
    }
}


