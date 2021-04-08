package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{


    public MassLosingBodyBuilder() {
        _typeTag="mlb";
        _desc="Mass losing Body";

    }
//Crea los obejtos de la clase
    @Override
    protected Body createTheInstance(JSONObject data) {

        String id = data.getString("id");
        JSONArray vector= data.getJSONArray("p");
        Vector2D p=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        vector=data.getJSONArray("v");
        Vector2D v=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        double m=data.getDouble("m");
        double lossFactor=data.getDouble("factor");
        double lossFrequency=data.getDouble("freq");

        return new MassLossingBody(id,v,p,m,lossFactor,lossFrequency);

    }

    @Override
    public JSONObject createData() {

            JSONObject data= new JSONObject();
            data.put("id","indentifier");
            data.put("v","velocity");
            data.put("p","body position");
            data.put("m","mass");
            data.put("freq", "frecuency");
            data.put("factor","factory");



        return data;

    }


}
