package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    Controller _ctrl;

    public MainWindow(Controller ctrl) {
        super("Physics Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);

        JPanel contentPanel=new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
        mainPanel.add(contentPanel,BorderLayout.CENTER);

        ControlPanel ctrlPanel= new ControlPanel(_ctrl);
        BodiesTable bodiesInfo= new BodiesTable(_ctrl);

        Viewer universeViewer= new Viewer(_ctrl);
        StatusBar statusBar= new StatusBar(_ctrl);

        mainPanel.add(ctrlPanel,BorderLayout.PAGE_START);
        mainPanel.add(statusBar,BorderLayout.PAGE_END);


        bodiesInfo.setPreferredSize(new Dimension(800,300));
        contentPanel.add(bodiesInfo);

        universeViewer.setPreferredSize(new Dimension(800,600));
        contentPanel.add(new JScrollPane(universeViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        statusBar.setPreferredSize(new Dimension(800,30));
        contentPanel.add(statusBar);


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
// other private/protected methods

}