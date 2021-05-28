package simulator.model;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSOutput;
import simulator.model.SimulatorObserver;
public class PhysicsSimulator implements SimulatorObserver{

    private ForceLaws _forceLaws; //ley de fuerza a aplicar
    private List<Body> listBody ; //cuerpos de la simulacion
    private double _dt; //incremento del tiempo
    private double _time; //numero de pasos que se ejecuta la simulacion
    private List<SimulatorObserver> observer;


    public PhysicsSimulator(ForceLaws forceLaws, double dt) {
        _forceLaws = forceLaws;
        _time = 0.0;
        _dt=dt;
        this.listBody =new ArrayList<Body>();
        this.observer= new ArrayList<SimulatorObserver>();
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

        // notificación onAdvance a todos los observadores.
        for (SimulatorObserver ob:observer) {
            ob.onAdvance(listBody,_time);
        }

    }


    public void reset(){
        listBody.clear();
        _time=0.0;
        for (SimulatorObserver ob:observer) {
            // envia una notificacion a todos los observadores.
            ob.onReset(listBody,_time,_dt,_forceLaws.toString());
        }
    }

    public void setDeltaTime(double dt)throws IllegalArgumentException {
        this._dt = dt;

        //notificación onDeltaTimeChanged a todos los observadores.
        for (SimulatorObserver ob:observer) {
            ob.onDeltaTimeChanged(_dt);
        }
    }

    // cambia las leyes de fuerza del simulador a forceLaws
    public void setForceLawsLaws(ForceLaws forceLaws)throws IllegalArgumentException {
        this._forceLaws = forceLaws;

        // notificación onForceLawsChanged a todos los observadores.
        for (SimulatorObserver ob:observer) {
            ob.onForceLawsChanged(_forceLaws.toString());
        }
    }


    //  añade o a la lista de observadores, si es que no está ya en ella.
    public void addObserver(SimulatorObserver o){
        if (!observer.contains(o)) {
            observer.add(o);
            // envia una notificación solo al que se acaba de registrar
            o.onRegister(listBody, _time,_dt,_forceLaws.toString());
        }
    }

    //agrega los cuerpos
    public void addBody(Body b) throws IllegalArgumentException {
        if (listBody.contains(b)) throw new IllegalArgumentException("Este cuerpo ya existe");
        listBody.add(b);
        for (SimulatorObserver ob:observer) {
            // envia una notificacion a todos los observadores
            ob.onBodyAdded(listBody,b);
        }
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


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
