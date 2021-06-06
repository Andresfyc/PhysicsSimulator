package simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

    public MassEqualStates() {
    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        //Compara si los time son iguales
        if (s1.get("time").equals(s2.get("time"))) return true;

        //Para all cuerpo de la lista de cuerpos de s1 y el de s2 tienen el mismo
        //valor en las claves “id” y “mass”
        for (int i=0; i < s1.length(); i++){
            if ((s1.get("id").equals(s2.get("id"))) && (s1.get("mass").equals(s2.get("mass")))){
                return true;
            }
        }

        return false;
    }
}
