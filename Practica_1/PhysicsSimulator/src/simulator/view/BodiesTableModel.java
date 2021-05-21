package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> _bodies;
    static private final String[] columnNames={"id","Mass","Position","Velocity", "Force"};


    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return _bodies.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Body b =_bodies.get(rowIndex);
        String s="";

        switch (columnIndex){
            case 0:
                s=b.getId();
                break;
            case 1:
                s=" "+b.getMass();
                break;
            case 2:
                s=b.getPosition().toString();
                break;
            case 3:
                s=b.getVelocity().toString();
                break;
            case 4:
                s=b.getForce().toString();
                break;
        }
        return s;
    }


    private void update (List<Body> bodies){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                _bodies=bodies;
                fireTableStructureChanged();
            }
        });
    }

    // Metodos del SimulatorObserver

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        update(bodies);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        update(bodies);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        update(bodies);
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        update(bodies);
    }

    @Override
    public void onDeltaTimeChanged(double dt) {
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
    }
}
