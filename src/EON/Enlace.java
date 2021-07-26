
package EON;

/**
 *
 * @author Team Delvalle
 * 
 * La clase se encarga de almacenar los enlaces de un grafo. Almacenando su vertices origen y fu
 * el espectro correspondiente(Un vector de FSs), la distancia entre los vertices, el ancho de los
 * FSs y una variable para identificar si el enlace sigue o no en utilizacion en el grafo
 * 
 */
public class Enlace {
    
    private int vert1;
    private int vert2;
    private int espectro;
    private FrecuencySlots [] fs;
    private int distancia;
    private double ancho;
    private boolean hab;
    //vector de utilizacion del espectro
    private int [] utilizacion;
    
    
    public Enlace(int v1,int v2,int esp,int d,double a){
        this.vert1=v1;
        this.vert2=v2;
        this.espectro=esp;
        this.ancho=a;
        this.fs=new FrecuencySlots[(int)(this.espectro/this.ancho)];
        for(int i=0;i<(int)((int)this.espectro/this.ancho);i++){
            this.fs[i]=new FrecuencySlots(this.ancho);
        }
        this.utilizacion=new int[(int)(this.espectro/this.ancho)];
        for(int i=0;i<(int)((int)this.espectro/this.ancho);i++){
            this.utilizacion[i]=0;
        }
        this.distancia=d;
        this.hab=true;
    }
    public Enlace(){
        this.vert1=0;
        this.vert2=0;
        this.espectro=0;
        this.fs=null;
        this.distancia=0;
        this.hab=true;
    }
    
    public void setVertice1(int v1){
        this.vert1=v1;
    }
    public int getVertice1(){
        return this.vert1;
    }
    public void setVertice2(int v2){
        this.vert2=v2;
    }
    public int getVertice2(){
        return this.vert2;
    }
    public void setEspectro(int e){
       this.espectro=e;
       this.fs=new FrecuencySlots[(int)(this.espectro/this.ancho)];
       for(int i=0;i<(int)(this.espectro/this.ancho);i++){
            this.fs[i]=new FrecuencySlots(this.ancho);
        }
    }
    public int [] getUtilizacion(){
        return this.utilizacion;
    }
    public void setUtilizacionFS(int i, int n){
        this.utilizacion[i]=n;
    }
    public int getEspectro(){
       return  this.espectro;
    }
    public int getDistancia(){
        return this.distancia;
    }
    public void setDistancia(int d){
        this.distancia=d;
    }
    public boolean getEstado(){
        return this.hab;
    }
    public void setEstado(boolean e){
        this.hab=e;
    }
    public void setAnchoFS(double a){
        this.ancho=a;
        this.fs=new FrecuencySlots[(int)(this.espectro/this.ancho)];
        for(int i=0;i<(int)(this.espectro/this.ancho);i++){
            this.fs[i]=new FrecuencySlots(this.ancho);
        }
    }
    public double getAnchoFS(){
        return this.ancho;
    }
    public FrecuencySlots[] getFS(){
        return this.fs;
    }
    
}
