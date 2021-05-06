package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    // ...
    Controller _ctrl;
    private Viewer viewer;
    private BodiesTable bodiesTable;
    private ControlPanel cPanel;

    public MainWindow(Controller ctrl) {
        super("Physics Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        this.setLocation(350,30);
        this.setPreferredSize(new Dimension(700,700));


        this.cPanel = new ControlPanel(_ctrl);
        cPanel.setLayout(new FlowLayout());
        cPanel.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(this.cPanel,BorderLayout.PAGE_START);

        /*CENTER PANEL*/
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2,2));
        mainPanel.add(centerPanel,BorderLayout.CENTER);

        //BODIES TABLE

        this.bodiesTable = new BodiesTable(this._ctrl);
        JPanel sPanel = new JPanel();
        sPanel.setLayout(new GridLayout(1,1));
        sPanel.setBackground(Color.white);
        sPanel.add(bodiesTable);

        centerPanel.add(sPanel);

        //GRAPHYC PANEL

        this.viewer= new Viewer(this._ctrl);
        JPanel iPanel = new JPanel();
        iPanel.setLayout(new GridLayout(1,1));
        iPanel.setBackground(Color.white);
        iPanel.add(viewer);
        centerPanel.add(iPanel);

        mainPanel.add(centerPanel,BorderLayout.CENTER);

        StatusBar statusBar = new StatusBar(_ctrl);
        statusBar.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);

        this.setVisible(true);

// TODO complete this method to build the GUI
// ..
    }
// other private/protected methods
// ...
}