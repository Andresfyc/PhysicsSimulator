package simulator.view;

public class LawsInfo {

    private String _key;
    private String _value;
    private String _description;

    public LawsInfo(String key, String value, String description) {
        this._key = key;
        this._value = value;
        this._description = description;
    }

    public String get_key() {
        return _key;
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
