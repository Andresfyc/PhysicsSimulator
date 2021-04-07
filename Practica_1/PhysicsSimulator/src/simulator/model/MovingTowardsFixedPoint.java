package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double g;
    private Vector2D c;


    public MovingTowardsFixedPoint(double g, Vector2D c) {
        this.g = g;
        this.c =new Vector2D(c);
    }

    @Override
    public void apply(List<Body> bs) {

        for (Body b: bs ) {
            b.addForce(c.minus(b.getPosition()).direction().scale(g*b.getMass()));
        }

    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public Vector2D getC() {
        return c;
    }

    public void setC(Vector2D c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Moving Towards " + c + "with constant acceleration " + g ;

    }
}


