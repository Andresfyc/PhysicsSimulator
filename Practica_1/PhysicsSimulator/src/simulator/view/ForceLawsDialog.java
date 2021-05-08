package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;

import javax.swing.*;
import java.util.List;

public class ForceLawsDialog extends JDialog {
    private static final long serialVersionUID=1L;

    private int _status;
    private List<JSONObject> _forceLawsInfo;

    private JComboBox<String> _lawsComboBox;
    private int _selectedLawsIndex;

    private JTable _dataTable;
    private LawsTableModel _dataTableModel;


    //***********************TABLE MODEL*****************************************//

    private class LawsTableModel extends BodiesTableModel{

        LawsTableModel(Controller ctrl) {
            super(ctrl);
        }
    }
}
