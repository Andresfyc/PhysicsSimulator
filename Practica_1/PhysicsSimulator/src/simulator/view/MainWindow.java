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

        //icono de la ventana - Einstein
        Image icon = Toolkit.getDefaultToolkit().getImage("resources/icons/einstein.png");
        this.setIconImage(icon);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);


        // Panel en general
        JPanel contentPanel=new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
        mainPanel.add(contentPanel,BorderLayout.CENTER);


        // barra de control
        ControlPanel ctrlPanel= new ControlPanel(_ctrl);
        mainPanel.add(ctrlPanel,BorderLayout.PAGE_START);

        // tabla de cuerpos
        BodiesTable bodiesInfo= new BodiesTable(_ctrl);
        bodiesInfo.setPreferredSize(new Dimension(800,300));
        contentPanel.add(bodiesInfo);

        // viewer
        Viewer universeViewer= new Viewer(_ctrl);
        universeViewer.setPreferredSize(new Dimension(800,600));
        contentPanel.add(new JScrollPane(universeViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));


        // barra estados en el pie de la ventana
        StatusBar statusBar= new StatusBar(_ctrl);
        mainPanel.add(statusBar,BorderLayout.PAGE_END);
        statusBar.setPreferredSize(new Dimension(800,30));
        contentPanel.add(statusBar);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No se cierre con la X
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}