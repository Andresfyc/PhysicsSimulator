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

    private JFileChooser fileC;

    ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        _ctrl.addObserver(this);

        this.openButton = new JButton();
        this.lawsButton = new JButton();
        this.exitButton = new JButton();
        this.stopButton = new JButton();
        this.runButton = new JButton();

        initGUI();
    }

    private void initGUI() {
// TODO construya la barra de herramientas agregando botones, etc.
        JToolBar toolBar = new JToolBar();

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
                popUp();
            }
        });

         stopButton.setToolTipText("Stop execution");
        stopButton.setIcon(new ImageIcon(uploadImage("resources/icons/stop.png")));
        stopButton.setPreferredSize(new Dimension(36,36));
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _stopped=true;
            }
        });

        exitButton.setToolTipText("Exit");
        exitButton.setIcon(new ImageIcon(uploadImage("resources/icons/exit.png")));
        exitButton.setPreferredSize(new Dimension(36,36));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });


        //Steps
        this.steps = new JSpinner(new SpinnerNumberModel(5,0,10000,100));
        this.steps.setToolTipText("Steps to execute: 1-10000");
        this.steps.setMaximumSize(new Dimension(70,70));
        this.steps.setMinimumSize(new Dimension(70,70));
        this.steps.setValue(0);


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

       //ADD

        toolBar.add(openButton);
        toolBar.add(lawsButton);
        toolBar.add(runButton);
        toolBar.add(stopButton);
        toolBar.add(new JLabel("Steps: "));
        toolBar.add(this.steps);
        toolBar.add(new JLabel("Time: "));
        toolBar.add(this.time);
        toolBar.add(Box.createHorizontalStrut(270));
        toolBar.add(exitButton);
        this.add(toolBar);
    }

    // otros métodos privados / protegidos

    private void run_sim(int n) {
        if (n > 0 && !_stopped) {
            try {
                _ctrl.run(1);
            } catch (Exception e) {
// TODO muestra el error en un cuadro de diálogo
                dialogError(e.getMessage());
// TODO habilitar todos los botones
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
        this.fileC=new JFileChooser();
        int deVal = this.fileC.showOpenDialog(null);
        if(deVal == JFileChooser.APPROVE_OPTION){
            File file= this.fileC.getSelectedFile();
            try{
                InputStream input=new FileInputStream(file);
                _ctrl.reset();
                _ctrl.loadBodies(input);
            }catch (Exception ex){
                dialogError("Error reading the file: "+ex.getMessage());
            }
        }
    }

    private void popUp(){
        List<JSONObject> lis= _ctrl.getForceLawsInfo();
        String[] forceLaws = new String[lis.size()];
        JSONObject[] opt = new JSONObject[lis.size()];
        int pos=0;

        for(int i =0; i<lis.size();i++){
            forceLaws[i]= lis.get(i).getString("desc") + " ("+lis.get(i).getString("type")+")";
            opt[i]=lis.get(i);
        }

        JFrame jf = new JFrame();
        String info= (String) JOptionPane.showInputDialog(jf,"Select Force laws to be used","Force laws Selector",JOptionPane.QUESTION_MESSAGE,null,forceLaws,forceLaws[0]);

        if (info==null){ info=forceLaws[0]; }
        if (info.charAt(0)=='N'){
            if (info.charAt(1)==('A')){
                pos=2;
            }
        }else{
            pos=1;
        }
        _ctrl.setForceLaws(opt[pos]);
    }

    // métodos SimulatorObserver

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

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
    public void close(){
        int msj= JOptionPane.showOptionDialog(new JFrame(),"Are sure you want to quit?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, null, null);
        if(msj==0){
            System.exit(0);
        }
    }


}