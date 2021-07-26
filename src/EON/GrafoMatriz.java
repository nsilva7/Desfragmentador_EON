package EON;

import EON.Utilitarios.Utilitarios;
import java.util.ArrayList;

/**
 *
 * @author Team Delvalle
 * El grafo en su forma de matriz de adyacencia para representar la topologia de una red.
 * Almacena:
 * Los datos de los enlaces para cada vertice i,j 
 * La cantidad de vertices 
 * La capacidad total que es la catidad de FSs disponibles por enlace en la red.
 * El ancho de cada FS.
 */
public class GrafoMatriz {
    
    private Enlace [][] vertices;
    private boolean [] marcas;
    private int cantidadVertices;
    private int capacidadTotal;
    private double anchoFS;
    
public GrafoMatriz(int V){
      
      this.marcas = new boolean[V];
      this.vertices = new Enlace[V][V];
      this.cantidadVertices=V;
      this.capacidadTotal=0;
      this.anchoFS=0;
    
    }
    public GrafoMatriz(){
        
        this.cantidadVertices=0;
        this.marcas=null;
        this.vertices=null;
    }
    
    public GrafoMatriz insertarDatos(double [][][]v){
        double nro=0,distancia=0,espectro=0,ancho=0;
        
        for(int i=0;i<this.vertices.length;i++){
            for(int j=0;j<this.vertices.length;j++){
                if(v[i][j][1]!=0){
                    nro=v[i][j][0];
                    distancia=v[i][j][1];
                    espectro=v[i][j][2];
                    this.capacidadTotal=(int)espectro;
                    ancho=v[i][j][3];
                    this.anchoFS=ancho;
                    this.vertices[i][(int)nro]=new Enlace(i,(int)nro,(int)espectro,(int)distancia,ancho);
                    this.vertices[(int)nro][i]=new Enlace((int)nro,i,(int)espectro,(int)distancia,ancho);
                 }
            }
        }
       return this;
    }
    public void marcar(int i){
        marcas[i]=true;
    }
    public boolean getMarca(int i){
        return marcas[i];
    }
    public Enlace acceder(int i, int j){
        return this.vertices[i][j];
    }
    public int getCantidadDeVertices(){
        return this.cantidadVertices;
    }
    public int getCapacidadTotal(){
        return this.capacidadTotal;
    }
    public double getAnchoFS(){
        return this.anchoFS;
    }
    public void setCapacidadTotal(int c){
        this.capacidadTotal=c;
         for(int i=0;i<this.vertices.length;i++){
            for(int j=0;j<this.vertices.length;j++){
                if(this.acceder(i, j)!=null){
                    this.acceder(i, j).setEspectro(c);
                }
            }
        }   
    }
    public void setAnchoFS(double a){
        this.anchoFS=a;
        for(int i=0;i<this.vertices.length;i++){
            for(int j=0;j<this.vertices.length;j++){
                if(this.acceder(i, j)!=null){
                    this.acceder(i, j).setAnchoFS(a);
                }
            }
        }
    }
    
    public int getCantidadEnlaces(){
        int cont = 0;
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i=0; i<this.cantidadVertices; i++){
            for (int j=0; j<this.cantidadVertices; j++){
                if (this.acceder(i, j)!=null){
                    if (!Utilitarios.isInList(lista, j)){
                        cont++;
                    }
                }
            }
            lista.add(i);
        }
        return cont;
    }
    public  void restablecerFS(){
        for(int i=0;i<this.getCantidadDeVertices();i++){
            for(int j=0;j<this.getCantidadDeVertices();j++){
                if(this.acceder(i, j)!=null){
                    for(int k=0;k<this.acceder(i, j).getFS().length;k++){
                        this.acceder(i, j).getFS()[k].setEstado(1);
                        this.acceder(i,j).getFS()[k].setTiempo(0);
                        this.acceder(i, j).getFS()[k].setPropietario(-1);
                    }
                }
            }
        }
    }
    public double entropia(){
        
        double uelink=0;
        double entropy=0;
        int countlinks=0;
        for(int i=0;i<this.getCantidadDeVertices();i++){
            for(int j=0;j<this.getCantidadDeVertices();j++){
                int UEcont=0;
                if(this.acceder(i, j)!=null){
                    for(int kk=0;kk<this.acceder(i, j).getFS().length-1;kk++){
                        //System.out.println("fs tamano: "+ this.acceder(i, j).getFS().length);
                        //System.out.println("fs tamano: "+ this.acceder(i, j).getFS()[kk]);
                        //System.out.println("fs-utili--> "+ this.acceder(i, j).getFS()[kk].getEstado() +"::"+ this.acceder(i, j).getUtilizacion()[kk]);
                        if(this.acceder(i, j).getFS()[kk].getEstado()!=this.acceder(i, j).getFS()[kk+1].getEstado()){
                            UEcont++;      
                        }
                    }
                    uelink=uelink+(double)UEcont;//(this.acceder(i, j).getFS().length-1));
                    countlinks++;
                    //System.in.read();
                }
            }
        }
        //System.out.println("cantidad de links::::: "+ countlinks);
        entropy=uelink/countlinks;
        return entropy;
    }
    
    public void ResetRed(){
        for(int ii=0; ii<this.getCantidadDeVertices() ;ii++ )
        {
            for(int jj=0; jj<this.getCantidadDeVertices();jj++ )
            {
                if (this.acceder(ii, jj) != null){
                    for(int ci=0;ci<this.acceder(ii, jj).getFS().length;ci++)
                    {
                        this.acceder(ii, jj).getFS()[ci].setEstado(1);
                        this.acceder(ii, jj).getFS()[ci].setTiempo(0);
                        //System.out.println(G[8].acceder(ii, jj).getVertice1()+"......");
                    }                          
                }
            }
        }
    }
}
