package simulator.misc;

import org.json.JSONArray;

public class Vector2D {

	double _x;
	double _y;

	// create the zero vector
	// crea el vector cero
	public Vector2D() {
		_x = _y = 0.0;
	}

	// copy constructor
	// constructor de copias
	public Vector2D(Vector2D v) {
		_x = v._x;
		_y = v._y;
	}

	// create a vector from an array
	// crea un vector a partir de una matriz
	public Vector2D(double x, double y) {
		_x = x;
		_y = y;
	}

	// return the inner product of this Vector a and b
	// devuelve el producto interno de este vector a y b
	public double dot(Vector2D that) {
		return _x * that._x + _y * that._y;
	}

	// return the length of the vector
	// devuelve la longitud del vector
	public double magnitude() {
		return Math.sqrt(dot(this));
	}

	// return the distance between this and that
	// devuelve la distancia entre esto y aquello
	public double distanceTo(Vector2D that) {
		return minus(that).magnitude();
	}

	// create and return a new object whose value is (this + that)
	// crea y devuelve un nuevo objeto cuyo valor es (esto + aquello)
	public Vector2D plus(Vector2D that) {
		return new Vector2D(_x + that._x, _y + that._y);
	}

	// create and return a new object whose value is (this - that)
	// crea y devuelve un nuevo objeto cuyo valor es (esto - aquello)
	public Vector2D minus(Vector2D that) {
		return new Vector2D(_x - that._x, _y - that._y);
	}

	// return the corresponding coordinate
	// devuelve la coordenada correspondiente
	public double getX() {
		return _x;
	}
	public double getY() {
		return _y;
	}

	// create and return a new object whose value is (this * factor)
	// crea y devuelve un nuevo objeto cuyo valor es (este * factor)
	public Vector2D scale(double factor) {
		return new Vector2D(_x * factor, _y * factor);
	}

	// return the corresponding unit vector
	// devuelve la direccion del vector correspondiente
	public Vector2D direction() {
		if (magnitude() > 0.0)
			return scale(1.0 / magnitude());
		else
			return new Vector2D(this);
	}

	public JSONArray asJSONArray() {
		JSONArray a = new JSONArray();
		a.put(_x);
		a.put(_y);
		return a;
	}

	// return a string representation of the vector
	// devuelve una representación de cadena del vector
	public String toString() {
		return "[" + _x + "," + _y + "]";
	}

}
