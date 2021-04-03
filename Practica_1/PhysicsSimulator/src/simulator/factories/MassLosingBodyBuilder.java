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

    @Override
    protected Body createTheInstance(JSONObject data) {

        String id=data.getString("id");
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

        JSONObject objeto= new JSONObject();
        JSONArray data=new JSONArray();

        objeto.put("id","b1");
        data.put(-3.5e10);
        data.put(0.0e00);
        objeto.put("p",data);

        data=new JSONArray();
        data.put(0.0e00);
        data.put(1.4e03);
        objeto.put("v",data);

        objeto.put("m",3.0e28);
        objeto.put("freq",1e3);
        objeto.put("factor",1e-3);


        return objeto;

    }


}
