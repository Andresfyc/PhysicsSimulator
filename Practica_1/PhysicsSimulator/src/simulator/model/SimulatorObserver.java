package simulator.model;

import java.util.List;

public interface SimulatorObserver {
    // Registrarse en:
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc);

    // Reset en:
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc);

    // Cuerpo agregado en el:
    public void onBodyAdded(List<Body> bodies, Body b);

    // Advance en:
    public void onAdvance(List<Body> bodies, double time);

    // Cambiar Delta time en:
    public void onDeltaTimeChanged(double dt);

    // Leyes de fuerza modificadas en:
    public void onForceLawsChanged(String fLawsDesc);

}
