package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    private ForceLaws _forceLaws; //ley de fuerza a aplicar
    private List<Body> listBody ; //cuerpos de la simulacion
    private double _dt=0.0; //incremento del tiempo
    private double _time; //numero de pasos que se ejecuta la simulacion


    public PhysicsSimulator(ForceLaws forceLaws, double time) {
        if (forceLaws == null ) throw new IllegalArgumentException("valor de la fuerza no es valido");
        else _forceLaws = forceLaws;

        if (time < 0 && time > 1 ) throw new IllegalArgumentException("Tiempo real por paso no es valido");
        else _time = time;
    }

    public void addBody(Body b)  {

        if (b.getId().equals(listBody.contains(b.id)))throw new IllegalArgumentException("Cuerpos iguales");
        else listBody.add(b);



    }

    public void advance() {

        for (int i = 0; i < listBody.size(); i++) {
            listBody.get(i).resetForce();
            get_forceLaws().apply(listBody);
            listBody.get(i).move(_time);
            _dt+=_time;
        }

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

    public ForceLaws get_forceLaws() {
        return _forceLaws;
    }

    public List<Body> getListBody() {
        return listBody;
    }

    public double get_dt() {
        return _dt;
    }

    public double get_time() {
        return _time;
    }
}
