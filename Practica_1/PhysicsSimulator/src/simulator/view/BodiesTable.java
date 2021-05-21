package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BodiesTable  extends JPanel {
    BodiesTable(Controller ctrl) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black, 2),
                "Bodies",
                TitledBorder.LEFT, TitledBorder.TOP));

// complete
        BodiesTableModel bTable = new BodiesTableModel(ctrl);
        JTable jt = new JTable(bTable);
        JScrollPane scroll = new JScrollPane(jt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        setOpaque(true);

    }
}
