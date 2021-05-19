package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {

    private Controller _ctrl;
    private boolean _stopped;
    private JSpinner steps;
    private JTextField time;
    JButton openButton;
    JButton lawsButton;
    JButton exitButton;
    JButton stopButton;
    JButton runButton;
    ForceLawsDialog fcl;
    private JFileChooser fc;

    ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        _ctrl.addObserver(this);
        this.openButton = new JButton();
        this.lawsButton = new JButton();
        this.exitButton = new JButton();
        this.stopButton = new JButton();
        this.runButton = new JButton();
        this.fc=null;

        initGUI();
    }

    private void initGUI() {
// TODO construya la barra de herramientas agregando botones, etc.

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); //impide que se pueda mover de sitio.

        //carga fichero

        openButton.setToolTipText("Load an event file");
        openButton.setIcon(new ImageIcon(uploadImage("resources/icons/open.png")));
        openButton.setPreferredSize(new Dimension(36,36));
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFiles();
            }
        });

        //seleccionar ley de gravedad

        lawsButton.setToolTipText("Select the force laws");
        lawsButton.setIcon(new ImageIcon(uploadImage("resources/icons/physics.png")));
        lawsButton.setPreferredSize(new Dimension(36,36));
        lawsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               selectLaws();

            }
        });


        //parar
        stopButton.setToolTipText("Stop execution");
        stopButton.setIcon(new ImageIcon(uploadImage("resources/icons/stop.png")));
        stopButton.setPreferredSize(new Dimension(36,36));
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _stopped=true;
            }
        });


        //salir
        exitButton.setToolTipText("Exit");
        exitButton.setIcon(new ImageIcon(uploadImage("resources/icons/exit.png")));
        exitButton.setPreferredSize(new Dimension(36,36));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });


        //Steps
        this.steps = new JSpinner(new SpinnerNumberModel(9000,1,10000,100));
        this.steps.setToolTipText("Steps to execute: 1-10000");
        this.steps.setMaximumSize(new Dimension(80,40));
        this.steps.setMinimumSize(new Dimension(80,40));
        this.steps.setValue(9000);



        //time

       this.time = new JTextField("0",5);
       this.time.setToolTipText("Current time");
       this.time.setMaximumSize(new Dimension(70,70));
       this.time.setMinimumSize(new Dimension(70,70));
       this.time.setEditable(true);



       // Descativar Botones
       runButton.setToolTipText("All buttons are disabled except Stop");
       runButton.setIcon(new ImageIcon(uploadImage("resources/icons/run.png")));
       runButton.setPreferredSize(new Dimension(36,36));
       runButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               try{
                   if(Double.parseDouble(time.getText())>0){
                       _ctrl.SetDeltaTime(Double.parseDouble(time.getText()));
                       int n= Integer.parseInt(steps.getValue().toString());
                       lawsButton.setEnabled(false);
                       openButton.setEnabled(false);
                       exitButton.setEnabled(false);
                       _stopped=false;
                       run_sim(n);
                   }else{
                    dialogError("The value time must be positive");
                   }
               }catch (Exception ex){

                   dialogError(ex.getMessage());
               }
           }
       });




        toolBar.add(openButton);
        toolBar.addSeparator();
        toolBar.add(lawsButton);
        toolBar.addSeparator();
        toolBar.add(runButton);
        toolBar.add(stopButton);
        toolBar.addSeparator();
        toolBar.add(new JLabel("Steps: "));
        toolBar.add(steps);
        toolBar.addSeparator();
        toolBar.add(new JLabel("Delta-Time: "));
        toolBar.add(time);
        toolBar.addSeparator(new Dimension(300,10));
        toolBar.add(exitButton);
       // toolBar.add(Box.createHorizontalStrut(300));
        this.add(toolBar);
    }

    // otros métodos privados / protegidos

    private void run_sim(int n) {
        if (n > 0 && !_stopped) {
            try {
                _ctrl.run(1);
            } catch (Exception e) {
                // TODO muestra el error en un cuadro de diálogo
                // TODO habilitar todos los botones

                dialogError(e.getMessage());
                lawsButton.setEnabled(false);
                openButton.setEnabled(false);
                exitButton.setEnabled(false);
                runButton.setEnabled(false);
                stopButton.setEnabled(false);

                _stopped = true;
                return;
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    run_sim(n - 1);
                }
            });
        } else {
            _stopped = true;
            openButton.setEnabled(true);
            lawsButton.setEnabled(true);
            exitButton.setEnabled(true);
            // TODO habilitar todos los botones
        }
    }

    //Logica Botones
    private void uploadFiles(){
        this.fc=new JFileChooser();
        int deVal = this.fc.showOpenDialog(null);
        if(deVal == JFileChooser.APPROVE_OPTION){
            this.fc.setCurrentDirectory(new File("."));
            File file= this.fc.getSelectedFile();
            try{
                InputStream is=new FileInputStream(file);
                _ctrl.reset();
                _ctrl.loadBodies(is);
            }catch (Exception e){
                dialogError("Error reading the file: "+e.getMessage());
            }
        }else{
            System.out.println("Load cancelled by user");//pendiente
        }
    }

    public void selectLaws(){

        if (fcl == null){
            fcl = new ForceLawsDialog((Frame)SwingUtilities.getWindowAncestor(this),_ctrl.getForceLawsInfo());
        }
        int status = fcl.open();
        if (status == 1){
            try {
                JSONObject law = fcl.getData();
                _ctrl.setForceLaws(law);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(this.getParent(),"Algo ha salido mal: " + e.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // métodos SimulatorObserver

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _ctrl.SetDeltaTime(dt);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }

    public Image uploadImage(String path){
        return Toolkit.getDefaultToolkit().createImage(path);
    }
    public void dialogError(String string){
        JOptionPane.showMessageDialog(null,string);
    }
    public void quit(){
        int msj= JOptionPane.showOptionDialog(new JFrame(),"Are sure you want to quit?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, null, null);
        if(msj==0){
            System.exit(0);
        }
    }


}