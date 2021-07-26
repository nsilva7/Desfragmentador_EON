
package EON;

/**
 *
 * @author Fer
 * Clase creada para que EONS soporte Modulacion de trafico
 * El unico atributo de la clase es la matriz tablaModulacion que es una matriz de 4x3 que almacena cual es el alcance, y la eficiencia en bit/s/Hz.
 * En este caso, se considera la eficienca de transporte de datos segun la modulacion por ranura de FS. 
 * El utilizado en esta simulacion sigue las pruebas realizdas en este paper: "Traffic and Power-Aware Protection Scheme in Elastic Optical Networks".
 * Tipo de Modulacion           Alcance (km)             Eficiencia (en Gbps para cada ranura de FS de ancho 12.5 GHz)
 * 1 (BPSK)                    4000                        12.5 
 * 2 (QPSK)                     2000                        25
 * 3 (8-QAM)                    1000                        37.5
 * 4 (16- QAM)                  500                         50
 */
public class Modulacion {
    
    private double [][]tablaModulacion;
    
    public Modulacion(){
        double [][] tabla  = {{1,4000,12.5},{2,2000,25},{3,1000,37.5},{4,500,50}}; //tabla de modulacion predeterminada
        this.tablaModulacion=tabla;
    }
    public Modulacion(double [][] t){ //definido por el usuario
        this.tablaModulacion=t;
    }
    /*
    * Obtiene el tipo de modulacion segun la distancia(km) siguiendo la tablaModulacion
    */
    public int getTipoDeModulacion(int distancia){
        
        if(distancia<=tablaModulacion[3][1]){ // se puede 16-QAM?
            return (int)tablaModulacion[3][0];
        }else if(distancia<=tablaModulacion[2][1]){ // se puede 8-QAM?
            return (int)tablaModulacion[2][0];
        }else if(distancia<=tablaModulacion[1][1]){ // se puede QPSK?
            return (int)tablaModulacion[1][0];
        }
        return 1; // si ninguno se pudo, se considera BPSK
    }
    /*
    * Obteniene el Nro de FS necesarios segun la distancia entre el nodo origen y fin, y el ancho de banda requerido.
    * La formula utilizada fue obtenida de "Distance-adaptive routing and spectrum assignment in  OFDM-based flexible transparent optical networks". 
    * Importante decir que en este paper y en otros se realiza en siguiente pasa cada par de nodos de camino, mientras que en otros caso, solo se realiza
    * para el camino completo, aque se considera para el camino completo
    */
    public int getNroFS(int B, int distancia){
        int modulacion=getTipoDeModulacion(distancia);
        Double N = B/(modulacion*12.5);
        if(N<1){
            N++;
        }
        return N.intValue();
    }
    /*
    * Para los casos en que no se considera modulacion, por defecto se utiliza  BPSK.
    */
    public int getNroFSbpsk(int B){
        Double N;
        N= B/(12.5);
        if(N<1){
            N++;
        }
        return N.intValue();
    }
    
}
