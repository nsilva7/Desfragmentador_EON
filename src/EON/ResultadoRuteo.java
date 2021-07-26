
package EON;

/**
 *
 * @author Fer
 */
/*
* Clase que representa el resultado obtenido por un algoritmo de ruteo
* Atributos:
* ruta: Lista enlazada que almacena la ruta seleccionada por el algoritmo de ruteo 
* nroCaminoKsp: indica el nro de camino entre los k-caminos mas cortos escogido por el algoritmo. 0 el mas corto k el mas largo
* demanda: en ella se almacenan el origen, destino, ancho de banda y el nroFS utilizados(Este ultimo se calcula dentro del algoritmo de ruteo)
*/
public class ResultadoRuteo {
    
    private ListaEnlazada ruta;
    private int nroCaminoKsp;
    private Demanda demanda;
    
    public ResultadoRuteo(ListaEnlazada l, int k, Demanda d){
        this.ruta=l;
        this.nroCaminoKsp=k;
        this.demanda=d;
    }
    public ResultadoRuteo(){
        this.ruta=null;
        this.nroCaminoKsp=0;
        this.demanda=null;
    }
    public ListaEnlazada getRuta(){
        return this.ruta;
    }
    public int getNroCaminoKsp(){
        return this.nroCaminoKsp;
    }
    public Demanda getDemanda(){
        return this.demanda;
    }
    public void setRuta(ListaEnlazada l){
        this.ruta = l;
    }
    public void setNroCaminoKsp(int k){
        this.nroCaminoKsp = k;
    }
    public void setDemanda(Demanda d){
        this.demanda = d;
    }
    
}

