package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;


//Moverse hacia un punto fijo
public class MovingTowardsFixedPoint implements ForceLaws {

    private double g;
    private Vector2D c;

    public MovingTowardsFixedPoint(double g, Vector2D c) {
        this.g = g; //gravedad
        this.c =new Vector2D(c); //Punto Fijo
    }

    //para cada cuerpo bi, el método apply añade la fuerza
    @Override
    public void apply(List<Body> bs) {
        for (Body b: bs ) {

            // b se le añade la fuerza con la siguiente formula:
            // F = (direccion(c-p)) * (g*masab)
            b.addForce(c.minus(b.getPosition()).direction().scale(g*b.getMass()));

            //Esto provocará que el cuerpo bi:
            //se mueva hacia ~c con una aceleración g.
        }
    }


    @Override
    public String toString() {
        return "Moving Towards " + c + " with constant acceleration " + g ;

    }
}


