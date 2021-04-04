package simulator.model;

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

    public void addBody(Body b)  {
        if (listBody.contains(b)) throw new IllegalArgumentException("Este cuerpo ya EXISTE");
        listBody.add(b);
    }

    public void advance() {

        for (int i = 0; i < listBody.size(); i++) {
            listBody.get(i).resetForce();
        }
        _forceLaws.apply(listBody);
        for (int i = 0; i < listBody.size(); i++) {
            listBody.get(i).move(_dt);
        }
        _time=_dt;

    }

    JSONObject state;
    {
        state = new JSONObject();
        state.put("time", get_time());
        state.put("bodies", getListBody());

    }

    public JSONObject getState() {
        return state;
    }

    public String toString() {
        return getState().toString();
    }

    public List<Body> getListBody() {
        return listBody;
    }

    public double get_time() {
        return _time;
    }
}
