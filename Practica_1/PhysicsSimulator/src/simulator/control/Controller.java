package simulator.control;

import com.sun.org.apache.xpath.internal.operations.NotEquals;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {

    PhysicsSimulator ps;
    Factory<Body>  fb;

    public Controller(PhysicsSimulator ps, Factory<Body> fb) {
        this.ps = ps;
        this.fb = fb;
    }


    public void  loadBodies(InputStream in){

        JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
        JSONArray bodies = jsonInupt.getJSONArray("bodies");

        for (int i = 0; i < bodies.length() ; i++) {
            ps.addBody(fb.createInstance(bodies.getJSONObject(i)));
        }
    }

    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp){

        JSONObject expOutJO = null;
        if (expOut != null) expOutJO = new JSONObject(new JSONTokener(expOut));
        if (out == null){
            out = new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            };
        }

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");
        JSONObject currState = null;
        JSONObject expState = null;

        currState = ps.getState();
        p.println(currState);

        if (expOutJO != null){
            expState = expOutJO.getJSONArray("states").getJSONObject(0);
            if (!cmp.equal(expState,currState));
               // throw new NotEqualStatesException(expState,currState,0);
        }

        // for para comparar el resto de pasos y lanzar excepcion


        p.println("]");
        p.println("}");
    }


}
