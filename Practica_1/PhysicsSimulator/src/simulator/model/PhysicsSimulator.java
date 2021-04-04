package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    private ForceLaws _forceLaws; //ley de fuerza a aplicar
    private List<Body> listBody ; //cuerpos de la simulacion
    private double _dt; //incremento del tiempo
    private double _time= 0.0; //numero de pasos que se ejecuta la simulacion


    public PhysicsSimulator(ForceLaws forceLaws, double time) {
        if (forceLaws == null ) throw new IllegalArgumentException("valor de la fuerza no es valido");
        else _forceLaws = forceLaws;

        if (time < 0 && time > 1 ) throw new IllegalArgumentException("Tiempo real por paso no es valido");
        else _time = time;
    }

    public void addBody(Body b)  {



        for (Body bs: listBody) {
            if (b.getId().equals(bs.getId()))throw new IllegalArgumentException("Cuerpos iguales");
            else listBody.add(b);

        }

    }

    public void advance() {

        for (Body b: listBody) {
            b.resetForce();
        }

        _forceLaws.apply(listBody);

        for (Body bs: listBody) {
            bs.move(_time);
            _dt+=_time;
        }

    }



    JSONObject state;
    {
        state = new JSONObject();
        state.put("time", _dt);
        state.put("bodies", listBody);

    }

    public JSONObject getState() {
        return state;
    }

    public String toString() {
        return getState().toString();
    }
}
