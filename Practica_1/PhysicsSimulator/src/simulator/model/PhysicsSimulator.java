package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    private ForceLaws _forceLaws; //ley de fuerza a aplicar
    private List<Body> listBody ; //cuerpos de la simulacion
    private double _dt; //incremento del tiempo
    private double _time; //numero de pasos que se ejecuta la simulacion


    public PhysicsSimulator(ForceLaws forceLaws, double dt) {
        _forceLaws = forceLaws;
        _time = 0.0;
        _dt=dt;
        this.listBody =new ArrayList<Body>();
    }

//Avance de los cuerpos
    public void advance() throws  IllegalArgumentException{

        //Se resetean todos los cuerpos
        for(Body b:listBody){
            b.resetForce();
        }

        //Aplica las leyes de fuerza
       _forceLaws.apply(listBody);

        //Mueve cada cuerpo con el tiempo real de cada paso
        for(Body b:listBody){
            b.move(_dt);
        }

            //incrementa el tiempo actual en _dt segundos
       _time+=_dt;

    }
    //agrega los cuerpos
    public void addBody(Body b) throws IllegalArgumentException {
        if (listBody.contains(b)) throw new IllegalArgumentException("this body already exists");
        listBody.add(b);
    }

//estados de los cuerpos
    public JSONObject getState() {

        JSONObject state = new JSONObject();
        JSONArray bodies = new JSONArray();
        state.put("time", this._time);
        for (Body b:listBody){
            bodies.put(b.getState());
        }

        state.put("bodies", bodies);

        return state;
    }

    public String toString() {
        return getState().toString();
    }



}
