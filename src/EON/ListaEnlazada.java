package EON;
/**
 *
 * @author Team Delvalle
 * Lista enlazada necesaria para cumplir principalemnte con la logica del algoritmo KSP y otros algortimos.
 */
public class ListaEnlazada {
    private Nodo inicio;
    private Nodo fin;
    private int tamanho;
    public ListaEnlazada() {
        this.inicio = null;
        this.fin = null;
        this.tamanho = 0;
    }

    public int getTamanho() {
        return tamanho;
    }
    public Nodo getInicio(){
        return this.inicio;
    }
    public Nodo getFin(){
        return this.fin;
    }
    public void insertarAlfinal (int dato) {
        Nodo nodo = new Nodo();

        nodo.setDato(dato);
        nodo.setPosicion(tamanho);
        nodo.setSiguiente(null);

        if (this.fin != null) {
            this.fin.setSiguiente(nodo);
        }
        this.fin = nodo;
        this.tamanho++;

        if (this.tamanho == 1) {
            this.inicio = fin;
        }
    }
    public void insertarAlComienzo (int dato) {
        Nodo nodo = new Nodo();

        nodo.setDato(dato);
        nodo.setPosicion(0);
        nodo.setSiguiente(this.inicio);

        if(this.inicio==null)
            this.fin = nodo;
        
        this.tamanho++;
        this.inicio=nodo;
        
        Nodo n1=this.inicio;
        for(Nodo n=this.inicio.getSiguiente();n!=null;n=n.getSiguiente()){
            n.setPosicion(n1.getPosicion()+1);
            n1=n1.getSiguiente();
        }
        if (this.tamanho == 1) {
            this.inicio = fin;
        }  
    }
    public ListaEnlazada optenerSublista(int i, int j){
        Nodo nodo = new Nodo();
        ListaEnlazada l = new ListaEnlazada();
        nodo=inicio;
        while(nodo.getPosicion()!=i){
            nodo=nodo.getSiguiente();
        }
        
        while(nodo.getPosicion()!=j){
            l.insertarAlfinal(nodo.getDato());
            nodo=nodo.getSiguiente();
        }
        l.insertarAlfinal(nodo.getDato());
        return l;
    }
    
    public Nodo nodo(int pos){
        Nodo n=new Nodo();
        int i=0;
        n=this.inicio;
        while(i!=pos && n!=null){
            n=n.getSiguiente();
            i++;
        }
        if(i==pos){
            return n;
        }else{
            return null;
        }
    }
    
    public boolean comparar(ListaEnlazada l2){
        
        if(getTamanho()!=l2.getTamanho()){
            return false;
        }
        else{
            for(int i=0;i<getTamanho();i++){
                if(nodo(i).getDato()!=l2.nodo(i).getDato()){
                    return false;
                }
            }
        }
        return true;
    }
    public void union(ListaEnlazada l1,ListaEnlazada l2){
        
        Nodo nodo=new Nodo();
        Nodo nodo1=new Nodo();
        for(nodo=l1.getInicio();nodo!=null;nodo=nodo.getSiguiente()){
            insertarAlfinal(nodo.getDato());
        }
        
        for(nodo=l2.getInicio();nodo!=null;nodo=nodo.getSiguiente()){
            boolean ban=true;
            for(nodo1=l1.getInicio();nodo1!=null;nodo1=nodo1.getSiguiente()){
                if(nodo.getDato()==nodo1.getDato()){
                    ban=false;
                    break;
                }
            }
            if(ban){
                insertarAlfinal(nodo.getDato());
            }
        }
    }
}