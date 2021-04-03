package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double _g;
    private Vector2D _c;


    public MovingTowardsFixedPoint(double g, Vector2D c) {
        _g = g;
        _c =new Vector2D(c);
    }

    @Override
    public void apply(List<Body> bs) {

        for (Body b: bs ) {
            b.addForce(_c.minus(b.getPosition()).direction().scale(_g*b.getMass()));
        }

    }

    public double get_g() {
        return _g;
    }

    public Vector2D get_c() {
        return _c;
    }

    public void set_g(double _g) {
        this._g = _g;
    }

    public void set_c(Vector2D _c) {
        this._c = _c;
    }

    @Override
    public String toString() {
        return "Moving Towards " + _c + "with constant acceleration " + _g ;

    }
}


