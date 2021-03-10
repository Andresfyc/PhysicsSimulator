package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

    private double fij;

    static private final double G=6.67E-11;

    public NewtonUniversalGravitation(double fij) {
        this.fij = fij;
    }

    @Override
    public void apply(List<Body> bs) {


        for (int x=0; x<bs.size();x++){
                Vector2D force=new Vector2D(bs.get(x).getPosition());
                for (int y=0; y<bs.size();y++){
                    if (x!=y){
                        force=force.plus(GravityForce(bs.get(x),bs.get(y)));
                        if(bs.get(x).getMass()!=0){
                            bs.get(x).setAceleracion(force.scale(1/bs.get(x).getMass()));
                        }else{
                            bs.get(x).setAceleracion(new Vector2D(bs.get(x).getAceleracion()));
                            bs.get(x).setVelocity(new Vector2D(bs.get(x).getVelocity()));
                        }
                    }
                }
        }


    }

    private Vector2D GravityForce(Body i, Body j){

        fij=G*((i.getMass()*j.getMass())/Math.pow(j.getPosition().distanceTo(i.getPosition()),2));
        Vector2D distance=j.getPosition().minus(i.getPosition()).direction();
        Vector2D Force=distance.scale(fij);
        return Force;

    }

    @Override
    public String toString() {
        return "NewtonUniversalGravitation{" +
                "fij=" + fij +
                '}';
    }
}
