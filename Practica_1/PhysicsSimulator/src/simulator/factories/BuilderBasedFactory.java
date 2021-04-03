package simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T>{

    private List<Builder<T>> _builders;
    //private List<JSONObject> _factoryElements;

    public BuilderBasedFactory(List<Builder<T>> builders) {
        this._builders = new ArrayList<Builder<T>>(builders);
        //this._factoryElements = new ArrayList<JSONObject>();
    }



    @Override
    public T createInstance(JSONObject info) {
        T buil;
        for (int i = 0; i < _builders.size(); i++) {
            buil=_builders.get(i).createInstance(info);
            if (buil != null){
                return buil;
            }
        }
        throw new IllegalArgumentException("Modelo desconocido");
    }

    @Override
    public List<JSONObject> getInfo() {
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (int i=0; i<_builders.size(); i++){
            list.add(_builders.get(i).getBuilderInfo());
        }
        return list;
    }


}
