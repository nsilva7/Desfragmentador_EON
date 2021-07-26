/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package EON.Metricas;
import EON.*;
import EON.Utilitarios.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author sFernandez
 */
public class Metricas {
    
    /*
    Bandwidth Fragmentation Ratio - Mide la cantidad de bloques de FS por enlace
    @param
    G - red actual
    capacidad - cantdiad de FS por enlace
    
    */
    public static double BFR(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double contOcupados = 0;
        double contSeguido = 0;
        double mayorSeguido = 0;
        double sumaEnlaces = 0;
        double [][] maxBlocks = new double[G.getCantidadDeVertices()][G.getCantidadDeVertices()];
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0 ;j<G.getCantidadDeVertices() ; j++){
                if (!Utilitarios.isInList(lista, j)){
                    if (G.acceder(i, j)!=null){
                        for (int k=0; k<capacidad; k++){
                            //1= libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==1){
                                contSeguido++;
                            }else{
                                if (contSeguido>mayorSeguido){
                                    mayorSeguido = contSeguido;
                                }
                                contSeguido = 0;
                                contOcupados++;
                            }
                        }
                        if (contSeguido>mayorSeguido){
                            mayorSeguido = contSeguido;
                        }
                        
                        if (contOcupados==capacidad){
                            maxBlocks[i][j] = 0;
                        }else{
                            maxBlocks[i][j] = 1 - (mayorSeguido/(capacidad-contOcupados));
                        }
                        
                        if (maxBlocks[i][j]==1){
                            System.out.println("MaxBlock= "+mayorSeguido + " / (352 - subm(b) : "+ contOcupados);
                        }
                        
                    }else{
                        maxBlocks[i][j] = -1;
                    }
                    contOcupados = 0;
                    mayorSeguido = 0;
                    contSeguido = 0;
                }
            }
            lista.add(i);
        }
        
        lista = new ArrayList<>();
        
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (!Utilitarios.isInList(lista, j) && maxBlocks[i][j]!=-1 ){
                    
                    sumaEnlaces = sumaEnlaces + maxBlocks[i][j];
                    
                }
            }
            lista.add(i);
        }
        if (sumaEnlaces==G.getCantidadEnlaces()){
            System.out.println("");
        }
        
        return (sumaEnlaces/G.getCantidadEnlaces());
        
        
        
    }
    
    /*
    Maximu Used Index-Slot retorna el mayor indice usado en un enlace
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    */
    public static double MSI(GrafoMatriz G, int capacidad){
        ArrayList<Integer> lista = new ArrayList<>();
        double sumaIndices = 0;
        //ArrayList<Integer> indices = new ArrayList<>();
        double [] indices = new double[G.getCantidadEnlaces()];
        int c= 0; 
        for (int i=0; i<G.getCantidadEnlaces(); i++){
            indices[i] = 0;
        }
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (G.acceder(i, j)!=null){
                    if (!Utilitarios.isInList(lista, j)){
                        for (int k=capacidad-1; k>-1; k--){
                            // 1 = libre 0 = Ocupado
                            if (G.acceder(i, j).getFS()[k].getEstado()==0){
                                //indices.add(k);
                                indices[c] = k;
                                c++;
                                break;
                            }
                        }
                    }
                }
            }
            lista.add(i);
        }
        
        
        
        for (int i=0; i<indices.length; i++){
            sumaIndices = sumaIndices + indices[i];
        }
        
        return (sumaIndices/G.getCantidadEnlaces());
    }
    
    /*
    Path Consecutiveness - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    ListaEnlazada[] caminos - todos los caminos de dos enlaces de la red
    */
    public static double PathConsecutiveness (ListaEnlazada[] caminos, int capacidad, GrafoMatriz G, int FSMinPC){
        double suma=0;
        double promedio;
        int ind = 0; //variable para saber cuantos de los caminos enviados son válidos para hacer el promedio
        
        int OE[] = new int[(capacidad)];
        int sgteBloque;//bandera para avisar que tiene que ir al siguiente bloque
        int cgb = 0;//contador global de bloques
        double PCaux;
        double sum, cfs;
        int contFSMinPC = 0; //contador para saber si tiene el mínimo de espacio para ser considerado libre
        
        for(ListaEnlazada camino : caminos){ //solo toma en cuenta los no null
            if (camino != null){
                //Inicializadomos el espectro, inicialmente todos los FSs estan libres
                for(int i=0;i<capacidad;i++){
                    OE[i]=1;
                }
                
                //Calcular la ocupacion del espectro para cada camino k
                for(int i=0;i<capacidad;i++){
                    for(Nodo n=camino.getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                       //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                        if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                            OE[i]=0;
                            break;
                        }
                    }
                }
                
                //ocupa los bloques con cant de FS menores al mínimo
                for(int i=0;i<capacidad;i++){
                    contFSMinPC = 0; //reset
                    if(OE[i] == 1){
                        for(int j=i;j<capacidad;j++){
                            if(OE[i] == 1){
                                contFSMinPC++;
                            }else{
                                break;
                            }
                        }
                        if(contFSMinPC < FSMinPC){
                            //poner como ocupados
                            for(int k=0;k < contFSMinPC;k++){
                                OE[i + k]=0;
                            }
                        }
                        i = i + contFSMinPC; //para que ya no controle los siguientes que ya controló
                    }
                }

                //calcular cantidad de bloques libres
                sgteBloque = 0; 
                cgb = 0;
                for(int i=0;i<capacidad;i++){
                    if(OE[i]==1 && sgteBloque == 0){
                        cgb++;
                        sgteBloque = 1;
                    }else if (OE[i]==0){
                        sgteBloque = 0;
                    }
                }
                
                //calcular joins y fs libres
                cfs = 0;
                sum = 0;
                for(int i=0;i<capacidad - 1;i++){ //recorre hasta el penúltimo fs
                    sum += OE[i] * OE[i+1];
                    if(OE[i]==1){
                        cfs++;
                    }
                }
                //para el ultimo fs
                if(OE[capacidad - 1] == 1){
                    cfs++;
                }
                if(cgb==0){
                    PCaux=0;
                }else{
                    PCaux = (sum / cgb) * (cfs / capacidad);
                }
                suma = suma + PCaux;
                ind++;
            }
        }
        promedio = suma/ind;
        return promedio;
    }
    
    /*
    Entropia por el porcentaje de Uso del enlace - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    ListaEnlazada[] caminos - todos los caminos de dos enlaces de la red
    */
    public static double EntropiaPorUso (ListaEnlazada[] caminos, int capacidad, GrafoMatriz G){
        double uelink=0;
        double entropy=0;
        double total=0;
        double promedio=0, uso = 0;
        int countlinks=0;
        int k = 0, suma = 0;
        int OE[] = new int[(capacidad)];
        Nodo t;
        while (k<caminos.length && caminos[k]!=null){
                    for (t = caminos[k].getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                        int UEcont=0;
                        if(G.acceder(t.getDato(), t.getSiguiente().getDato())!=null){
                            for(int kk=0;kk<G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1;kk++){
                                if(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk].getEstado()!=G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[kk+1].getEstado()){
                                    UEcont++;
                                }
                            }
                            uelink=uelink+(double)UEcont;//(G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length-1));
                            countlinks++;
                        }
                    }
                    entropy=uelink/countlinks;
                    //inicializa el espectro
                    //Calcular el procentaje de uso
//                    for (int w = 0; w < (capacidad); w++) {
//                        OE[w] = 1;
//                    }
                    Nodo n = caminos[k].getInicio();
                    int Total = (G.acceder(n.getDato(), n.getSiguiente().getDato())).getFS().length;
                    //calcula la ocupacion real del espectro
                    for (int j = 0; j < Total; j++) {
                        for (n = caminos[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                            if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[j].getEstado() == 0) {
//                                OE[j] = 0;
                                suma++;
                                break;
                            }
                        }
                    }
                    uso = suma / capacidad;
                    total= total + (entropy*uso);
                    k++;
        }
        promedio = total/caminos.length;
        return promedio;
    }
    
    /*
    Porcentaje de uso de los FS en todo el grafo - Usado en DefragProAct
    @param
    G - red actual
    capacidad - cantidad de FS por enlace
    */
    public static double PorcUsoGrafo (GrafoMatriz G){
        double contUso = 0;
        double contTotal = 0;
        for(int i=0;i<G.getCantidadDeVertices();i++){
            for(int j=0;j<G.getCantidadDeVertices();j++){
                if(j>i && G.acceder(i, j)!=null){
                    for (FrecuencySlots f : G.acceder(i, j).getFS()) {
                        contTotal++;
                        if (f.getEstado() == 0) {
                            contUso++;
                        }
                    }
                }
            }
        }

        double porcUso = contUso/contTotal;
        return porcUso;
    }

    public static double shf(GrafoMatriz G, int capacidad) {
        double shf = 0;
        double sf = 0;
        int cantidadEnlaces = 0;
        for (int i=0; i<G.getCantidadDeVertices(); i++){
            for (int j=0; j<G.getCantidadDeVertices(); j++){
                if (G.acceder(i, j)!=null){
                    cantidadEnlaces++;
                    sf=0;
                    for (int k=capacidad-1; k>-1; k--){
                            // 1 = libre 0 = Ocupado
                        if (G.acceder(i, j).getFS()[k].getEstado()==1){
                            sf++;
                        }else{
                            if(sf != 0)  //hasta 1   *  [0, 5.86]
                                shf += ((sf / capacidad) * Math.log(capacidad / sf));
                            sf = 0;
                        }
                    }
                }
            }
        }
        return shf/cantidadEnlaces;
    }
}
