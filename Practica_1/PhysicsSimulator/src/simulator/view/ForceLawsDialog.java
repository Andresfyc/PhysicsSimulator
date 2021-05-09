package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ForceLawsDialog extends JDialog {
    private static final long serialVersionUID=1L;

    private int _status;
    private List<JSONObject> _forceLawsInfo;

    private JComboBox<JSONObject> _lawsComboBox;
    private int _selectedLawsIndex;

    private JTable _dataTable;
    private LawsTableModel _dataTableModel;


    //***********************TABLE MODEL*****************************************//

    private class LawsTableModel extends BodiesTableModel {

        LawsTableModel(Controller ctrl) {
            super(ctrl);
            ctrl.getForceLawsInfo();
        }

    }
    //*************************FORCE LAWS DIALOG***************************************//


    public ForceLawsDialog(List<JSONObject> forceLawsInfo) {

        this.setTitle("Force Laws Selection");
        initGUI(forceLawsInfo);

    }

    public void initGUI(List<JSONObject> forceLawsInfo){

        JLabel help = new JLabel(
                "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
       this.add(help,BorderLayout.NORTH);
       pack();
        JPanel mainPanel= new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JPanel panelInferior= new JPanel();
        BoxLayout layout = new BoxLayout(panelInferior,BoxLayout.Y_AXIS);
        panelInferior.setLayout(layout);
        _lawsComboBox= new JComboBox<JSONObject>();

        for(int i=0; i< forceLawsInfo.size();i++){
            _lawsComboBox.addItem(forceLawsInfo.get(i));
        }
            _lawsComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String laws=(String) _lawsComboBox.getSelectedItem();
                        //agregarlo a la tabla
                }
            });
        panelInferior.add(_lawsComboBox);
        mainPanel.add(panelInferior);

        JPanel panelCenter= new JPanel();
    }

}
