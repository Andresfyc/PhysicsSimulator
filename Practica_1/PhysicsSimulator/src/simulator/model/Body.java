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
        this.velocity = new Vector2D(velocity);
        this.position = new Vector2D(position);
        this.mass = mass;
        force=new Vector2D();
    }

    //Añade la fuerza
    void addForce(Vector2D f){
        force = force.plus(f);
    }
    //resetea la fuerza
    void resetForce(){
        force = new Vector2D();
    }

    //Mueve el cuerpo
    void move(double t){

        Vector2D acceleration;

        if (mass == 0) acceleration = new Vector2D();// si m es cero, entonces se pone â a(0,0).
        else acceleration = force.scale(1/mass); // si no, â= f/m.

        position=position.plus(velocity.scale(t).plus(acceleration.scale(t*t/2)));
        velocity=velocity.plus(acceleration.scale(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Objects.equals(id, body.id) ;
    }


        // devuelve los estados del cuerpo
    public JSONObject getState() {
        JSONObject stateBody = new JSONObject();
        stateBody.put("id",getId());
        stateBody.put("m", getMass());
        stateBody.put("p", getPosition().asJSONArray());
        stateBody.put("v", getVelocity().asJSONArray());
        stateBody.put("f", getForce().asJSONArray());
        return stateBody;
    }

    @Override
    public String toString() {
        return getState().toString();
    }



//identificador del cuerpo
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Devuelve el vector velocidad
    public Vector2D getVelocity() {
        return velocity;
    }

    //devuelve el vector fuerza
    public Vector2D getForce() {
        return force;
    }

    //Devuelve el vector Posición
    public Vector2D getPosition() {
        return position;
    }

//devuelve la masa del cuerpo
    public double getMass() {
        return mass;
    }


}