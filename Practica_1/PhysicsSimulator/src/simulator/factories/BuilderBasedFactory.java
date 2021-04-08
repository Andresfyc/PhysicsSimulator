package simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T>{

    private List<Builder<T>> _builders;
    private List<JSONObject> _factoryElements;

    public BuilderBasedFactory(List<Builder<T>> builders) {
        _builders = new ArrayList<Builder<T>>(builders);
        _factoryElements = new ArrayList<JSONObject>();

        for(Builder<T> b: _builders)
            _factoryElements.add(b.getBuilderInfo());
    }



    @Override
    public T createInstance(JSONObject info) throws IllegalArgumentException {

        if(info==null) throw new IllegalArgumentException("CreateInstance: null");

        for(Builder<T> b: _builders) {

            T a=b.createInstance(info);
            if(a!=null) return a;

        }
        throw new IllegalArgumentException("Unable to create instance");
    }

    @Override
    public List<JSONObject> getInfo() {
        return _factoryElements;

    }
}
