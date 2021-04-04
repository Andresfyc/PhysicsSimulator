package simulator.model;


import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.Objects;

public class Body {

    protected String id;
    protected Vector2D velocity;
    protected Vector2D force;
    protected Vector2D position;
    protected double mass;

    public Body(String id, Vector2D velocity, Vector2D position, double mass) {
        this.id = id;
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
    }


    void addForce(Vector2D f){
        force = force.plus(f);
    }

    void resetForce(){
        force = new Vector2D();
    }

    void move(double t){

        Vector2D acceleration;

        if (mass == 0) acceleration = new Vector2D();// si m es cero, entonces se pone â a(0,0).
        else acceleration = force.scale(1/mass); // si no, â= f/m.

        position.plus(velocity.scale(t).plus(acceleration.scale(0.5*t*t)));
        velocity.plus(acceleration.scale(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Objects.equals(id, body.id) ;
    }


    JSONObject state;
    {
        state = new JSONObject();
        state.put("id", getId());
        state.put("m", getMass());
        state.put("p", getPosition());
        state.put("v", getVelocity());
        state.put("f", getForce());

    }

    public JSONObject getState() {
        return state;
    }

    @Override
    public String toString() {
        return getState().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getForce() {
        return force;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }


}