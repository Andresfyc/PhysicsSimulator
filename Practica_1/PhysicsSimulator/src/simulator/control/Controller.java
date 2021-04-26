package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Controller {

    PhysicsSimulator ps;
    Factory<Body>  fb;
    Factory<ForceLaws> fl;

    public Controller(PhysicsSimulator ps, Factory<Body> fb, Factory<ForceLaws> fl) {
        this.ps = ps;
        this.fb = fb;
        this.fl=fl;
    }

    //carga los cuerpos
    public void  loadBodies(InputStream in){

        JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
        JSONArray bodies = jsonInupt.getJSONArray("bodies");

        for (int i = 0; i < bodies.length() ; i++) {
            ps.addBody(fb.createInstance(bodies.getJSONObject(i)));
        }
    }

    public void reset(){
       ps.reset();
    }
    public void SetDeltaTime(double dt){
        ps.setDeltaTime(dt);
    }
    public void addObserver(SimulatorObserver o){
        ps.addObserver(o);
    }
    public List<JSONObject> getForceLawsInfo(){
        return fl.getInfo();
    }
    public void setForceLaws(JSONObject info){
        ForceLaws gr = this.fl.createInstance(info);
        ps.setForceLawsLaws(gr);
    }

    //Ejecuta el simulador en (N) pasos
    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException{
        JSONObject expOutJO = null;
        if (expOut != null) expOutJO = new JSONObject(new JSONTokener(expOut));
        if (out == null){
            out = new OutputStream() {
                @Override
                public void write(int b) throws  IOException {

                }
            };
        }

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        JSONObject currState = null;
        JSONObject expState = null;

        //comparación de los estados iniciales
        currState = ps.getState();
        p.println(currState);
        if (expOutJO != null){
            expState = expOutJO.getJSONArray("states").getJSONObject(0);
            if (!cmp.equal(expState,currState))
                throw new NotEqualStatesException(expState,currState,0);
        }
         //comparación de los estados restantes
             for (int i = 1; i <= n; i++) {
                 ps.advance();

                 currState = ps.getState();
                 p.println(","+currState);
                if (expOutJO != null){
                     expState = expOutJO.getJSONArray("states").getJSONObject(i);
                     if (!cmp.equal(expState,currState))
                     throw new NotEqualStatesException(expState,currState, i);
                 }
        }
        p.println("]");
        p.println("}");
    }


}
