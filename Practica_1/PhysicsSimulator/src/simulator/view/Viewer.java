package simulator.view;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Viewer extends JComponent implements SimulatorObserver {

    private int _centerX;
    private int _centerY;
    private double _scale;
    private List<Body> _bodies;
    private boolean _showHelp;
    private boolean _showVectors;

    Viewer(Controller ctrl) {
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
// TODO add border with title
        _bodies = new ArrayList<>();
        _scale = 1.0;
        _showHelp = true;
        _showVectors = true;
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '-':
                        _scale = _scale * 1.1;
                        repaint();
                        break;
                    case '+':
                        _scale = Math.max(1000.0, _scale / 1.1);
                        repaint();
                        break;
                    case '=':
                        autoScale();
                        repaint();
                        break;
                    case 'h':
                        _showHelp = !_showHelp;
                        repaint();
                        break;
                    case 'v':
                        _showVectors = !_showVectors;
                        repaint();
                        break;
                    default:
                }
            }
        });

        addMouseListener(new MouseListener() {

            @Override
            public void mouseEntered(MouseEvent e) {
                requestFocus();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
// use 'gr' para dibujar, no 'g' --- da mejores resultados
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // calcular el centro
        _centerX = getWidth() / 2;
        _centerY = getHeight() / 2;

// TODO dibuja una cruz en el centro
// TODO dibujar cuerpos (con vectores si _showVectors es verdadero)
// TODO dibujar ayuda si _showHelp es verdadero
    }

    // otros métodos privados / protegidos

    private void autoScale() {
        double max = 1.0;
        for (Body b : _bodies) {
            Vector2D p = b.getPosition();
            max = Math.max(max, Math.abs(p.getX()));
            max = Math.max(max, Math.abs(p.getY()));
        }
        double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
        _scale = max > size ? 4.0 * max / size : 1.0;
    }

// Este método dibuja una línea desde (x1, y1) a (x2, y2) con una flecha.
// La flecha es de alto hy ancho w.
// Los dos últimos argumentos son los colores de la flecha y la línea

    private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, //
                                   Color lineColor, Color arrowColor) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - w, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;
        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        g.setColor(lineColor);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(arrowColor);
        g.fillPolygon(xpoints, ypoints, 3);
    }

// SimulatorObserver methods

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
}