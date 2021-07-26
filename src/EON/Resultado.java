
package EON;

/**
 *
 * @author Team Delvalle
 * Clase que representa el resultado que se optiene despues de la ejecucion del cualquier algoritmo RSA.
 * Almacena:
 * El camino a utilzar entre los posibles caminos retornados por el algoritm KSP
 * El vertice origen y fin
 * Y la variable CP que es utilizada para calcular la contiguidadd del espectro en los Algorimos RSA.
 */
public class Resultado {
        private int camino;
        private int inicio;//fs de inicio 
        private int fin;//fs de fin
        private float cp;
        private Integer capacidadFS;
        public Resultado(){
            this.camino=0;
            this.inicio=0;
            this.fin=0;
            this.cp=0;
            this.capacidadFS=0;
        }

    public Integer getCapacidadFS() {
        return capacidadFS;
    }

    public void setCapacidadFS(Integer capacidadFS) {
        this.capacidadFS = capacidadFS;
    }
        public int getCamino() {
            return this.camino;
        }
        public int getInicio() {
            return this.inicio;
        }
        public int getFin() {
            return this.fin;
        }
        public float getCp() {
            return this.cp;
        }
        
        public void setCamino(int c) {
             this.camino=c;
        }
        public void setInicio(int i) {
             this.inicio=i;
        }
        public void setFin(int f) {
             this.fin=f;
        }
        public void setCp(float cp) {
             this.cp=cp;
        }
        
}
