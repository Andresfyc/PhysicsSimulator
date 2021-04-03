package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;


public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

    public NewtonUniversalGravitationBuilder() {
        TypeTag="nlug"
        super("nlug", "Newton law of Universal Gravitation");
    }

    @Override
    JSONObject createData() {
        return null;
    }

    @Override
    protected ForceLaws createTheInstance(JSONObject data) {
        double G = data.has("_G") ? data.getDouble("_G") : 6.67E-11;
        return new NewtonUniversalGravitation(G);
    }
}
