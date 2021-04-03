package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{


    public MassLosingBodyBuilder(String basic, String default_body) {
        super("mlb", "Mass losing Body");
    }

    @Override
    protected MassLossingBody createTheInstance(JSONObject data) {

        String id=data.getString("id");
        JSONArray vector= data.getJSONArray("p");
        Vector2D p=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        vector=data.getJSONArray("v");
        Vector2D v=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        double m=data.getDouble("m");
        double lossFactor=data.getDouble("lossFactor");
        double lossFrequency=data.getDouble("lossFrequency");

        return new MassLossingBody(id,v,p,m,lossFactor,lossFrequency);

    }

    @Override
    JSONObject createData() {

        JSONObject objeto= new JSONObject();
        JSONArray data=new JSONArray();

        /*
        objeto.put("id","b1");
        data.put(0.0e00);
        data.put(0.0e00);
        objeto.put("p",data);

        data=new JSONArray();
        data.put(0.05e04);
        data.put(0.0e00);
        objeto.put("v",data);

        objeto.put("m",5.97e24);
*/
        return objeto;

    }


}
