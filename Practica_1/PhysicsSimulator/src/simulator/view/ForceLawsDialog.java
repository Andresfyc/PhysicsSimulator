package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;
import simulator.model.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ForceLawsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboBox;
    private JTextArea area;
    private int _status;
    private List<JSONObject> _forceLawsInfo;
    private int _selectedLawsIndex;
    private LawsTableModel _dataTableModel;
    private JPanel panel;

    public ForceLawsDialog(List<JSONObject> forceLawsInfo) {
        this.setTitle("Force Laws Selection");
        initGUI(forceLawsInfo);

    }




    //************************* LAWS DIALOG  ***************************************//


    public void initGUI(List<JSONObject> forceLawsInfo) {
        JLabel help = new JLabel(
                "<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
        this.add(help, BorderLayout.NORTH);
        pack();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        panel = new JPanel();

        _dataTableModel = new LawsTableModel();
        JTable jtD = new JTable(_dataTableModel);
        JScrollPane scrollD = new JScrollPane(jtD, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollD);

        panel.add(scrollD);

        BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layout);

        this.comboBox = new JComboBox<String>();

        for (JSONObject s: forceLawsInfo ) {
            this.comboBox.addItem(s.getString("desc"));
        }

        this.comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = (String) comboBox.getSelectedItem();
                area.append(producto + "\n");
            }

        });

        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton cancel = new JButton("Cancel");
//        cancel.addActionListener(new ActionListener(){
//
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                ventanaPrincipal.setListaCompra("");
//
//            }
//
//        });

        panelBotones.add(cancel);

        JButton ok = new JButton("OK");
//        ok.addActionListener(new ActionListener(){
//
//            @Override
//            public void actionPerformed(ActionEvent e ) {
//                ventanaPrincipal.setListaCompra(area.getText());
//
//            }
//
//        });
        panelBotones.add(ok);

        JScrollPane scroll = new JScrollPane(area);
        panel.add(scroll);
        panel.add(this.comboBox);
        panel.add(panelBotones);

        mainPanel.add(panel);

        this.add(mainPanel);
        this.setVisible(false);
        this.pack();
    }


    public void add(String producto) {
        this.comboBox.addItem(producto);

    }

    public void limpia() {
        area.setText("");

    }


}

