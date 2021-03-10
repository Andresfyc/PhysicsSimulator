package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws {

    private double fij;

    static private final double G=6.67E-11;

    public NewtonUniversalGravitation(double fij) {
        this.fij = fij;
    }

    @Override
    public void apply(List<Body> bs) {
        Vector2D F;
        for (Body Bi:bs) {
            F=new Vector2D();
            for (Body Bj: bs){
                if(Bi!=Bj){
                    fij=0;
                    if(Bi.getMass()==0.0){
                        Bi.velocity=new Vector2D();

                    }else{
                        fij=(G*Bi.getMass()*Bj.getMass())/Math.pow(Bi.getPosition().distanceTo(Bj.getPosition()),2);
                        F=F.plus(Bj.getPosition().minus(Bi.getPosition()).direction().scale(fij));
                    }
                }
            }
            Bi.addForce(F);
        }

    }

    @Override
    public String toString() {
        return "NewtonUniversalGravitation{" +
                "fij=" + fij +
                '}';
    }
}
