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

    public ForceLawsDialog(Frame parent, List<JSONObject> forceLawsInfo) {
        super(parent, true);
        this.setTitle("Force Laws Selection");
        _status = 0;
        initGUI(forceLawsInfo);
    }




    //************************* LAWS DIALOG  ***************************************//


    public void initGUI(List<JSONObject> forceLawsInfo) {

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel southPanel = new JPanel();
        JPanel panelBotones = new JPanel(new FlowLayout());


        JLabel help = new JLabel(
                "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
        this.add(help, BorderLayout.NORTH);


        fTable = new LawsTableModel();
        JTable jtF = new JTable(fTable);
        JScrollPane scrollD = new JScrollPane(jtF, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollD);

        mainPanel.add(scrollD);
        BoxLayout layout  = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
        mainPanel.setLayout(layout);


        this.comboBox = new JComboBox<String>();
        for (JSONObject s: forceLawsInfo ) {
            this.comboBox.addItem(s.getString("desc"));
        }

        this.comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                fTable.addElement(_forceLawsInfo.get(index).toString(), _forceLawsInfo.get(index).toString());
            }
        });

        southPanel.add(new JLabel("Force Law : "));
        southPanel.add(this.comboBox);

//        area = new JTextArea(15,15);
//        area.setEditable(false);
//        JScrollPane scroll = new JScrollPane(area);
//        this.add(scroll);

        mainPanel.add(southPanel);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                _status=0;
                ForceLawsDialog.this.setVisible(true);
            }

        });

        panelBotones.add(cancel);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e ) {
                if (comboBox.getSelectedItem() != null){
                    _status=1;
                    ForceLawsDialog.this.setVisible(false);
                }

            }

        });




        panelBotones.add(ok);

        mainPanel.add(panelBotones,BorderLayout.SOUTH);


        setMinimumSize(new Dimension(600,500));
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        //this.setVisible(false);
//        this.pack();
    }




    public int open() {
        pack();
        setVisible(true);
        return _status;
    }

    @Override
    public String toString() {
        return fTable.toString();
    }

    public JSONObject getLaws() {
        return null;
    }
}

