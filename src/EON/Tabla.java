package EON;
/**
 *
 * @author Team FDelvalle
 * La clase es utilizada como un auxiliar para el algoritmo de Dijsktra, se encarga de almacenar para 
 * cada i-esimo vertice la distancia al origen, por cual verice debe ir, y si ya fue marcado como visitado o no. 
 */
public class Tabla{
    int [] distancia;
    int [] origen;
    boolean [] marcas;
    int tamaño;
    public Tabla(int n){
        distancia= new int[n];
        origen= new int [n];
        marcas = new boolean [n];
        
        tamaño=n;
        for(int i=0; i<n;i++){
            distancia[i]=-1;
            origen[i]=-1;
        }
        
    }
    public void setDistancia(int v, int p){
        distancia[v]=p;
    }
    public void setOrigen(int v,int o){
        origen[v]=o;
    }
    public void marcar(int v){
        marcas[v]=true;
    }
    public int getDistancia(int v){
        return distancia[v];
    }
    public int getOrigen(int v){
        return origen[v];
    }
    public boolean getMarca(int v){
        return marcas[v];
    }   
    public int mayor(){ 
        int mayor,vertice;
        int j=0;
        mayor=-1;
        vertice=0;
        for(int i=j+1; i<tamaño;i++){
            if(!getMarca(i) && getDistancia(i)!=-1 && mayor<getDistancia(i)){
                mayor=getDistancia(i);
                vertice=i;
            }
        }
        return vertice;
    }
    
    public int menor(){
        
        int menor=0, vertice=-1;
       for(int i=0; i<tamaño;i++){
            if(!getMarca(i) && getDistancia(i)!=-1){
                menor=getDistancia(i);
                vertice=i;
                break;
            }
        }
       
        for(int i=0; i<tamaño;i++){
            if(!getMarca(i) && getDistancia(i)!=-1 && menor>getDistancia(i)){
                menor=getDistancia(i);
                vertice=i;
            }
        }
        return vertice;
    }
    
}
