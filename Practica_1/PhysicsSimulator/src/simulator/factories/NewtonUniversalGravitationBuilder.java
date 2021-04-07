package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;


public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

    public NewtonUniversalGravitationBuilder() {
       _typeTag="nlug";
       _desc="Newton law of Universal Gravitation";

    }



    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        double G = data.has("G") ? data.getDouble("G") : 6.67E-11;
        return new NewtonUniversalGravitation(G);
    }

    @Override
    public JSONObject createData() {

        JSONObject data= new JSONObject();
        data.put("G","Gravity");
        return data;
    }
}
