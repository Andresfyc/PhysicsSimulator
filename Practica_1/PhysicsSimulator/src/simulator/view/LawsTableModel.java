package simulator.view;


import org.json.JSONObject;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LawsTableModel extends AbstractTableModel {

    private List<LawsInfo> _forceLaws;
    static private final String[] columnNames={"Key","Value","Description"};

    LawsTableModel() {
        _forceLaws = new ArrayList<LawsInfo>();
    }

    public void updateTable(JSONObject data){
        clear();
        for (String key: data.keySet()) {
            LawsInfo li = new LawsInfo(key, "", data.getString(key));
            _forceLaws.add(li);
        }
        fireTableStructureChanged();
    }

    public void clear(){
       _forceLaws.clear();
       this.fireTableStructureChanged();
    }

    @Override
    public int getRowCount() {
        return _forceLaws.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        LawsInfo li = _forceLaws.get(rowIndex);
        li.set_value(o.toString());
    }

    public boolean isCellEditable(int  rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LawsInfo li =_forceLaws.get(rowIndex);
        String s="";

        switch (columnIndex){
            case 0:
                s = li.get_key();
                break;
            case 1:
                s = li.get_value();
                break;
            case 2:
                s = li.get_description();
                break;
        }
        return s;

    }

    public String toString() {
        String s = "";
        for (int i=0; i < _forceLaws.size(); i++){
            s = s + _forceLaws.get(i) + "\n";
        }
        return s;
    }


}
