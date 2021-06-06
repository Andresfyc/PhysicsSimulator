package simulator.view;


import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ForceLawsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboBox;
    public int _status;
    private List<JSONObject> _forceLawsInfo;
    public int _selectedLawsIndex;
    private LawsTableModel fTable;
    private JSONObject data;

    public ForceLawsDialog(Frame parent, List<JSONObject> forceLawsInfo) {
        super(parent, true);
        this.setTitle("Force Laws Selection");
        _status = 0;
        _forceLawsInfo = forceLawsInfo;
        _selectedLawsIndex=0;
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        initGUI();
    }


    //************************* LAWS DIALOG  ***************************************//


    public void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel();
        JPanel panelBotones = new JPanel(new FlowLayout());


        /************************** Label ******************************/

        JLabel help = new JLabel(
                "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
        this.add(help, BorderLayout.NORTH);

        /************************** Tabla ******************************/

        fTable = new LawsTableModel();
        JTable jtF = new JTable(fTable);
        JScrollPane scrollD = new JScrollPane(jtF, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollD);
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);

        /************************** ComboBox ******************************/

        this.comboBox = new JComboBox<String>();
        for (JSONObject s : _forceLawsInfo) {
            this.comboBox.addItem(s.getString("desc"));
        }

//        comboBox.setSelectedIndex(_selectedLawsIndex);
//        data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
//        fTable.updateTable(data);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _selectedLawsIndex = comboBox.getSelectedIndex();
                data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
                fTable.updateTable(data);
            }
        });

        southPanel.add(new JLabel("Force Law : "));
        southPanel.add(this.comboBox);
        mainPanel.add(southPanel);

        /************************ Boton Cancelar **************************/

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                _status = 0;
                ForceLawsDialog.this.setVisible(false);
            }

        });

        panelBotones.add(cancel);

        /************************ Boton OK **************************/

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem() != null) {
                    _status = 1;
                    ForceLawsDialog.this.setVisible(false);
                }
            }
        });

        panelBotones.add(ok);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(600, 300));
        this.setLocationRelativeTo(null);// centrar ventana
        this.add(mainPanel);
    }

    public int open() {
        //pack();
        setVisible(true);
        return _status;
    }

    @Override
    public String toString() {
        return fTable.toString();
    }

    public JSONObject getData() {
        JSONObject laws =  new JSONObject();
        String datos="{";
        for (int i = 0; i < data.length(); i++) {
            String key = (String) fTable.getValueAt(i, 0);
            String value = (String) fTable.getValueAt(i, 1);
            if (!value.isEmpty()){
                datos +=  key + ":" + value;
                if(i+1 < data.length()){
                    datos += ",";
                }
            }

        }
        datos += "}";

        //System.out.println(datos);
        laws.put("data", new JSONObject(datos));
        laws.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));

        System.out.println(data.toString());
        return laws;

    }
}




