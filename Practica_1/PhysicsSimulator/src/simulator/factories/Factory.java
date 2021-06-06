package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {

	//recibe una structura JSON que describe el objeto a crear
	public T createInstance(JSONObject info);

	//devuelve una lista de objetos JSON, que son “plantillas” para structuras JSON válidas.
	public List<JSONObject> getInfo();
}
