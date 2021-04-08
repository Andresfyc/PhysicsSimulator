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


    public void advance() throws  IllegalArgumentException{


        for(Body b:listBody){
            b.resetForce();
        }

       _forceLaws.apply(listBody);
        for(Body b:listBody){
            b.move(_dt);
        }

       _time+=_dt;

    }
    public void addBody(Body b) throws IllegalArgumentException {
        if (listBody.contains(b)) throw new IllegalArgumentException("this body already exists");
        listBody.add(b);
    }


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
