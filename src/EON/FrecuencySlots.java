
package EON;

/**
 *
 * @author Team Delvalle
 * Clase encargada de representar un FSs en particular. Almacena en ancho del FS, su estado en el grafo(Si esta en utilizacion o no)
 * y el tiempo que necesita ser utilizado en la red.
 */
public class FrecuencySlots {
    private double anchoFS;
    private int estado;
    private int tiempo;
    private int conex;
    //propietario
    private int propietario;
 
    public FrecuencySlots(double a){
        this.anchoFS=a;
        this.estado=1;
        this.tiempo=0;
         /******/
        this.propietario=-1;
    }
   
    public double getAncho(){
        return this.anchoFS;
    }
    public void setEstado(int a){
        this.estado=a;
    }
    public int getEstado(){
        return this.estado;
    }
    public int getTiempo(){
        return this.tiempo;
    }
    public void setTiempo(int t){
        this.tiempo=t;
    }
    public void setConexion(int a){
        this.conex=a;
    }
    public int getConexion(){
        return this.conex;
    }
    
    public void setPropietario(int prop){
        this.propietario = prop;
    }
    public int getPropietario(){
        return this.propietario;
    }
}
