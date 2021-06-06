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
        Vector2D delta = b.getPosition().minus(a.getPosition()); //creamos un vector de posiciones |~pj − ~pi|
        double dist = delta.magnitude(); // la distancia de delta = |~pj − ~pi|

        // si |~pj − ~pi| > 0 = G* ((mi*mj)/ (|~pj − ~pi|)^2)
        // si no, = 0.0
        double magnitude = dist>0 ? (G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;

        // retorna F~i,j = ~di,j · fi,j
        return delta.direction().scale(magnitude);
    }

    // dos cuerpos Bi y Bj aplican una fuerza gravitacional uno sobre otro
    // se atraen mutuamente.
    @Override
    public void apply(List<Body> bs) {
        Vector2D F;
        for (Body Bi:bs) {
            F=new Vector2D();
            for (Body Bj: bs){
                if(Bi!=Bj){
                    if(Bi.getMass()==0.0){  //si mi es igual a 0,0
                        //ponemos los vectores de velocidad de Bi a ~o = (0, 0)
                        Bi.velocity=new Vector2D(); //
                    }else{
                        // se calcula la fuerza en la funcion force(a,b)
                        F= F.plus(force(Bi,Bj));
                    }
                }
            }
            // agregamos la fuerza a: Bi
            Bi.addForce(F);
        }

    }

    @Override
    public String toString() {
        return "NewtonUniversal Gravitation" +
                " G=" + G ;
    }
}


