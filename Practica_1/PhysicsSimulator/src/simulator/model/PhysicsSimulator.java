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


    public void advance() {


        for(Body b:listBody){
            b.resetForce();
        }
//      for (int i = 0; i < listBody.size(); i++) {
//           listBody.get(i).resetForce();
//        }
       _forceLaws.apply(listBody);
        for(Body b:listBody){
            b.move(_dt);
        }

//        for (int i = 0; i < listBody.size(); i++) {
//           listBody.get(i).move(_dt);
//       }
       _time+=_dt;

    }
    public void addBody(Body b)  {
        if (listBody.contains(b)) throw new IllegalArgumentException("Este cuerpo ya EXISTE");
        listBody.add(b);
    }

//    JSONObject state;
//    {
//        state = new JSONObject();
//        state.put("time", this._time);
//        state.put("bodies", this.listBody);
//
//    }

    public JSONObject getState() {



     JSONObject state = new JSONObject();
        JSONArray bodies = new JSONArray();
        state.put("time", this._time);
        for (Body b:listBody){
            bodies.put(b.getState());
        }
//        for (int i = 0; i < listBody.size(); i++) {
//            bodies.put(listBody.get(i).getState());
//
//
//        }
        state.put("bodies", bodies);

        return state;
    }

    public String toString() {
        return getState().toString();
    }



}
