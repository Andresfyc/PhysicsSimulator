package simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T>{

    private List<Builder<T>> _builders;
    private List<JSONObject> _factoryElements;

    public BuilderBasedFactory(List<Builder<T>> builders) {
        this._builders = new ArrayList<>(builders);
        this._factoryElements = new ArrayList<JSONObject>();
    }



    @Override
    public T createInstance(JSONObject info) {
        if(info == null) throw
        new IllegalArgumentException("Invalid value for createInstance: null");

        for (Builder<T> bs:_builders ) {
            bs.createInstance(info);
        }

        return null;
    }

    @Override
    public List<JSONObject> getInfo() {
        return _factoryElements;
    }

    private List<JSONObject> getJSONlist(){
        List<JSONObject> _factElem =new ArrayList<JSONObject>();
        for (Builder<T> b: _builders){
            _factElem.add(b.getBuilderInfo());
        }
        return _factElem;
    }
}
