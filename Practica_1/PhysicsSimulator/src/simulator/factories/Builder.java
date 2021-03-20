package simulator.factories;

import com.sun.org.apache.xpath.internal.operations.String;
import org.json.JSONObject;

abstract class Builder <T extends Object>{

    protected String _typeTag; // tipo de objeto a construir
    protected String _desc;   // descripcion del objeto

    public Builder(java.lang.String _typeTag, java.lang.String _desc) {
    }

    public T createInstance(JSONObject info){
        T b=null;
        if (_typeTag != null && _typeTag.equals(info.getString("type"))) {
            b = createTheInstance(info.getJSONObject("data"));
        }
        return b;
    }

    public JSONObject getBuilderInfo(){
        JSONObject info= new JSONObject();
        info.put("type", _typeTag);
        info.put("data", createData());
        info.put("desc", _desc );
        return info;
    }

    abstract JSONObject createData();

    protected abstract T createTheInstance(JSONObject jsonObject);
}
