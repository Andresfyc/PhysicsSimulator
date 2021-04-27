package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {


    private List<Body> _bodies;
    static private final String[] columnNames={"id","Mass","Position","Velocity", "Force"};
    private String[] columnData;

    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        ctrl.addObserver(this);
        this.columnData=new String[columnNames.length];
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
        return columnNames[column].toString();
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        this.columnData[0]=_bodies.get(rowIndex).getId().toString();
        this.columnData[1]= String.valueOf(_bodies.get(rowIndex).getMass());
        this.columnData[2]= String.valueOf(_bodies.get(rowIndex).getPosition());
        this.columnData[3]= String.valueOf(_bodies.get(rowIndex).getVelocity());
        this.columnData[4]= String.valueOf(_bodies.get(rowIndex).getForce());
        return this.columnData[columnIndex];
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

        _bodies=bodies;
        fireTableStructureChanged();
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
    _bodies=bodies;
    fireTableStructureChanged();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _bodies=bodies;
        fireTableStructureChanged();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        _bodies=bodies;
        fireTableStructureChanged();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
