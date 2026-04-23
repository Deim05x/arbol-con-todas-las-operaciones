/**
 *Deivid Mateo Cañan Pretel - Maria Camila Melo 
 */
public class Nodo<T extends Comparable<T>> {
    public T dato;
    public Nodo<T> izquierdo;
    public Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
