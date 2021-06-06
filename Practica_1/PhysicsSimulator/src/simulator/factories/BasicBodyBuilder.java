package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;

//para crear objetos de la clase Body
public class BasicBodyBuilder extends Builder<Body>{

    public BasicBodyBuilder() {
        _typeTag="basic";
        _desc="Default Body";
    }

    @Override
    protected Body createTheInstance(JSONObject data) throws IllegalArgumentException{
        String id = data.getString("id");
        JSONArray vector= data.getJSONArray("p");
        Vector2D p=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        vector=data.getJSONArray("v");
        Vector2D v=new Vector2D(vector.getDouble(0),vector.getDouble(1));
        double m=data.getDouble("m");

        return new Body(id,v,p,m);
    }

    @Override
    public JSONObject createData() {
        JSONObject data= new JSONObject();
        data.put("id","identifier");
        data.put("v","velocity");
        data.put("p","body position");
        data.put("m","mass");

        return data;
    }

}
