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
    private List<JSONObject> _forceLawsInfo; // solo JSON
    public int _selectedLawsIndex;
    private LawsTableModel fTable;
    private JSONObject data;

    public ForceLawsDialog(Frame parent, List<JSONObject> forceLawsInfo) {
        super(parent, true);
        this.setTitle("Force Laws Selection");
        _status = 0;
        _forceLawsInfo = forceLawsInfo;
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        initGUI();
    }


    //************************* LAWS DIALOG  ***************************************//


    public void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel();
        JPanel panelBotones = new JPanel(new FlowLayout());

        JLabel help = new JLabel(
                "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
        this.add(help, BorderLayout.NORTH);


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

//        _selectedLawsIndex = 0;
//        comboBox.setSelectedIndex(_selectedLawsIndex);
//        data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
//        fTable.updateTable(data);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _selectedLawsIndex = comboBox.getSelectedIndex();
                data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
                System.out.println(data.toString() + " comboBox");
                fTable.updateTable(data);
            }
        });

        southPanel.add(new JLabel("Force Law : "));
        southPanel.add(this.comboBox);
        mainPanel.add(southPanel);

        /************************ Botones **************************/

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                _status = 0;
                ForceLawsDialog.this.setVisible(false);
            }

        });

        panelBotones.add(cancel);

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
        this.setLocationRelativeTo(null);
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


        String key = (String) fTable.getValueAt(_selectedLawsIndex,0);
        String value = (String) fTable.getValueAt(_selectedLawsIndex,1);
        String datos = "{" + key + ":" + value + "}";
        data.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
        data.put("data", new JSONObject(datos));

        return data;
    }


}




