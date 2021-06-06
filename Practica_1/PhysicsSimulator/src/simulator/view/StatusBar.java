package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements SimulatorObserver {

    private JLabel _currTime; // for current time
    private JLabel _currLaws; // for gravity laws
    private JLabel _numOfBodies; // for number of bodies


    StatusBar(Controller ctrl) {
     initGUI();
     ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));

        // TODO complete the code to build the tool bar
        currTime();
        numBodies();
        currLaws();
    }

// other private/protected methods
    /*** currTime - Tiempo actual ***/
    public void currTime(){
        _currTime = new JLabel("Time: "+0);
        _currTime.setMinimumSize(new Dimension(100,10));
        _currTime.setMaximumSize(new Dimension(100,10));
        _currTime.setPreferredSize(new Dimension(100,10));
        this.add(_currTime);
        //this.add(Box.createHorizontalStrut(55));
    }

    /*** currTime - Tiempo actual ***/
    public void numBodies(){
        _numOfBodies = new JLabel("Bodies: "+0);
        _numOfBodies.setMinimumSize(new Dimension(100,10));
        _numOfBodies.setMaximumSize(new Dimension(100,10));
        _numOfBodies.setPreferredSize(new Dimension(100,10));
        this.add(_numOfBodies);
        //this.add(Box.createHorizontalStrut(40));
    }

    /*** currLaws - Leyes actual ***/
    public void currLaws(){
        _currLaws = new JLabel("Laws: "+ " ");
        _currLaws.setMinimumSize(new Dimension(300,10));
        _currLaws.setMaximumSize(new Dimension(300,10));
        _currLaws.setPreferredSize(new Dimension(300,10));
        this.add(_currLaws);
    }

// SimulatorObserver methods

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies.setText("Bodies: " + bodies.size());
        _currTime.setText("Time: " + time);
        _currLaws.setText("Laws:  "+ fLawsDesc);

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies.setText(String.valueOf(0));
        _numOfBodies.setText("Time: " + time);
        _currLaws.setText("Laws:  "+ fLawsDesc);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _numOfBodies.setText("Bodies: "+ bodies.size());
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
    _currTime.setText("Time: "+ time);
    }

    @Override
    public void onDeltaTimeChanged(double dt) {
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        _currLaws.setText("Laws: "+ fLawsDesc);
    }

}