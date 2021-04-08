package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws {

    protected double G;

    public NewtonUniversalGravitation(double G) {
        this.G = G;
    }

    //Operaciones de la Ley de Newton
    private Vector2D force(Body a, Body b) {
        Vector2D delta = b.getPosition().minus(a.getPosition());
        double dist = delta.magnitude();
        double magnitude = dist>0 ? (G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
        return delta.direction().scale(magnitude);
    }

    //Aplica la fuerza gravitacional uno sobre otro
    @Override
    public void apply(List<Body> bs) {
        Vector2D F;
        for (Body Bi:bs) {
            F=new Vector2D();
            for (Body Bj: bs){
                if(Bi!=Bj){
                    if(Bi.getMass()==0.0){
                        Bi.velocity=new Vector2D();

                    }else{
                        F= F.plus(force(Bi,Bj));


                    }
                }
            }
            Bi.addForce(F);
        }

    }

    @Override
    public String toString() {
        return "NewtonUniversalGravitation{" +
                ", G=" + G +
                '}';
    }
}
