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
    _currTime = new JLabel("Time: "+0);
    _numOfBodies = new JLabel("Bodies: "+0);
    _currLaws = new JLabel("Laws: "+ " ");
     initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));
        this.add(_currTime);
        this.add(Box.createHorizontalStrut(40));
        this.add(_numOfBodies);
        this.add(Box.createHorizontalStrut(40));
         this.add(_currLaws);
// TODO complete the code to build the tool bar
    }

// other private/protected methods
// ...
// SimulatorObserver methods
// ...
    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies.setText("Bodies: "+String.valueOf(bodies.size()));
       _currTime.setText("Time: " +time);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _numOfBodies.setText(String.valueOf(0));
        _numOfBodies.setText(String.valueOf("Time: "+time));
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _numOfBodies.setText("Bodies: "+String.valueOf(bodies.size()));
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
    _currTime.setText("Time: "+String.valueOf(time));
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        _currLaws.setText("Laws "+ fLawsDesc);
    }

}