package EON;
/**
 *
 * @author Team Delvalle
 * Nodo de una lsita enlazada simple.
 */
public class Nodo {
    private int dato;
    private Nodo siguiente;
    private int pos;
    public Nodo() {
        this.dato = 0;
        
        this.siguiente = null;
        this.pos=0;
    }
    public void setDato(int dato) {
        this.dato = dato;
    }
    public int getDato() {
        return this.dato;
    }
    public Nodo getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    public int getPosicion(){
        return pos;
    }
    public void setPosicion(int p){
        pos=p;
    }
}