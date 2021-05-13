package simulator.view;



public class LawsInfo {

    private String _key;
    private String _value;
    private String _description;

    public LawsInfo(String _key, String _value, String _description) {
        this._key = _key;
        this._value = _value;
        this._description = _description;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public String get_description() {
        return _description;
    }



}
