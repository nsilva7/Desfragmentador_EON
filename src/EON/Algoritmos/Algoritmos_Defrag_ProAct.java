/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON.Algoritmos;

import EON.Demanda;
import EON.GrafoMatriz;
import EON.ListaEnlazada;
import EON.ListaEnlazadaAsignadas;
import EON.Nodo;
import EON.Resultado;
import EON.Utilitarios.Utilitarios;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author sFernandez
 */
public class Algoritmos_Defrag_ProAct {

    
    public static Resultado Def_FACA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int sgteBloque;//bandera para avisar que tiene que ir al siguiente bloque
        
//        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<>();
        ArrayList<Integer> inicios = new ArrayList<>();
        ArrayList<Integer> fines = new ArrayList<>();
        ArrayList<Integer> indiceKsp = new ArrayList<>();
        ArrayList<Integer> indKSPUbicMenFcmt = new ArrayList<>();

        //Probamos para cada camino, si existe espectro para ubicar la damanda
        int k=0;

        while(k<ksp.length && ksp[k]!=null){
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            //Calcular la ocupacion del espectro para cada camino k
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                   //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            
            //encuentra las posibles asignaciones
            cont=0;
            sgteBloque = 0; 
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1 && sgteBloque == 0){
                    cont++;
                }else if (OE[i]==0){
                    cont=0;
                    sgteBloque = 0;
                }
                //si se encontro un bloque valido, tomamos en cuenta el ksp
                if(cont==demanda.getNroFS()){
                    fines.add(i);
                    inicios.add(i - cont + 1);
                    kspUbicados.add(ksp[k]);
                    indiceKsp.add(k);
                    sgteBloque = 1;
                    cont = 0;
//                    break; //solo agrega el primero que encuentra
                }
            }
            k++;
        }
        
        if (kspUbicados.isEmpty()){ //bloqueo
            //System.out.println("Desubicado");
            return null;
        }
        
        //cuenta los cortes para cada posible asiganción
//        int cutsSlot; //cantidad de cortes
        int ind = 0; //aux indice del kspUbicado actual
        int corte = 999; //cant de cortes de la opcion del ksp
        Resultado r = new Resultado();
        double FcmtAux = -1;
        double Fcmt = 9999999;
        int caminoElegido = -1;
        
        //por cada índice de los posibles caminos de cada KSP ubicado
        for (ListaEnlazada kspUbi : kspUbicados){
            corte = 0;
            for (Nodo n = kspUbi.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                if (inicios.get(ind) != 0 && fines.get(ind) < capacidad - 1) { //para que no tome los bordes sup e inf
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[inicios.get(ind) - 1].getEstado() == 1
                            && G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[fines.get(ind) + 1].getEstado() == 1) {
                        corte = corte + 1;
                    }
                }
            }
            
            //calcula el desalineamiento de cada uno
            int Desalineacion = 0;
            int bandEsRuta = 0;
            for (Nodo n = kspUbi.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                for (int i = 0; i < G.getCantidadDeVertices(); i++) {
                    if(G.acceder(n.getDato(), i)!=null){ //si son vecinos
                        bandEsRuta = 0;
                        for (Nodo nn = kspUbi.getInicio(); nn.getSiguiente().getSiguiente() != null; nn = nn.getSiguiente()) {
                            if(nn.getDato() == i){ //si es parte de la ruta
                                bandEsRuta = 1;
                            }
                        }
                        if(bandEsRuta == 0){
                            for(int fsd = inicios.get(ind); fsd <= inicios.get(ind) + demanda.getNroFS() - 1; fsd++){
                                if (G.acceder(i, n.getDato()).getFS()[fsd].getEstado() == 1) {
                                    Desalineacion = Desalineacion + 1;
                                } else {
                                    Desalineacion = Desalineacion - 1;
                                }
                            }
                        }
                    }
                }
            }
            
            //calcular la capacidad actual
            double capacidadLibre = (double)Utilitarios.contarCapacidadLibre(kspUbi,G,capacidad);
        
            double saltos = (double)Utilitarios.calcularSaltos(kspUbi);
            double vecinos = (double)Utilitarios.contarVecinos(kspUbi,G,capacidad);
            
            FcmtAux = corte + (Desalineacion/(demanda.getNroFS()*vecinos)) + (saltos *(demanda.getNroFS()/capacidadLibre)); 
            
            if (FcmtAux<Fcmt){
                Fcmt = FcmtAux;
                indKSPUbicMenFcmt.clear();
                indKSPUbicMenFcmt.add(ind);
            }else if(FcmtAux==Fcmt){
                indKSPUbicMenFcmt.add(ind);
            }
        
            ind++;
        }
        
        if (indKSPUbicMenFcmt.size() == 1){
            r.setCamino(indiceKsp.get(indKSPUbicMenFcmt.get(0)));
            r.setFin(fines.get(indKSPUbicMenFcmt.get(0)));
            r.setInicio(inicios.get(indKSPUbicMenFcmt.get(0)));
        }else{
            //encontrar el ksp más corto
            int tamKspMasCorto = 999;
            int indKspMasCorto = -1;
            for (int indMenCutsMenDesalig : indKSPUbicMenFcmt) {
                if (kspUbicados.get(indMenCutsMenDesalig).getTamanho() - 1 < tamKspMasCorto){
                    indKspMasCorto = indMenCutsMenDesalig;
                    tamKspMasCorto = kspUbicados.get(indMenCutsMenDesalig).getTamanho() - 1;
                }
            }
            //buscar el indice first fit
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            //Calcular la ocupacion del espectro para cada camino k
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[indiceKsp.get(indKspMasCorto)].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            cont=0; 
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    cont++;
                }else if (OE[i]==0){
                    cont=0;
                }
                //si se encontro un bloque valido, tomamos en cuenta el ksp
                if(cont==demanda.getNroFS()){ //si o si va a encontrar ya que es un ksp en kspUbicados
                    r.setCamino(indiceKsp.get(indKspMasCorto));
                    r.setFin(i);
                    r.setInicio(i - cont + 1);
                    break; //solo el primero que encuentra
                }
            }
        }
        

        return r;
    }
    
    public static Resultado Def_FA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int sgteBloque;//bandera para avisar que tiene que ir al siguiente bloque
        
//        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<>();
        ArrayList<Integer> inicios = new ArrayList<>();
        ArrayList<Integer> fines = new ArrayList<>();
        ArrayList<Integer> indiceKsp = new ArrayList<>();

        //Probamos para cada camino, si existe espectro para ubicar la damanda
        int k=0;

        while(k<ksp.length && ksp[k]!=null){
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            //Calcular la ocupacion del espectro para cada camino k
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                   //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }
            
            //encuentra las posibles asignaciones
            cont=0;
            sgteBloque = 0; 
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1 && sgteBloque == 0){
                    cont++;
                }else if (OE[i]==0){
                    cont=0;
                    sgteBloque = 0;
                }
                //si se encontro un bloque valido, tomamos en cuenta el ksp
                if(cont==demanda.getNroFS()){
                    fines.add(i);
                    inicios.add(i - cont + 1);
                    kspUbicados.add(ksp[k]);
                    indiceKsp.add(k);
                    sgteBloque = 1;
                    cont = 0;
//                    break; //solo agrega el primero que encuentra
                }
            }
            k++;
        }
        
        if (kspUbicados.isEmpty()){ //bloqueo
            //System.out.println("Desubicado");
            return null;
        }
        
        //cuenta los cortes para cada posible asiganción
//        int cutsSlot; //cantidad de cortes
        int ind = 0; //aux indice del kspUbicado actual
        int cutAux = 0; //cant de cortes del camino ksp
        int cuts = 999; //el menor corte, 999 como referencia inicial
        Resultado r = new Resultado();
        int DesalineacionFinal = 999;
        
        //vectores con los menores cortes
        ArrayList<Integer> indKSPUbicMenCuts = new ArrayList<>();
        ArrayList<Integer> indKSPUbicMenCutsMenDesalig = new ArrayList<>();
        
        //por cada índice de los posibles caminos de cada KSP ubicado
        for (ListaEnlazada kspUbi : kspUbicados){
            for (Nodo n = kspUbi.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                if (inicios.get(ind) != 0 && fines.get(ind) < capacidad - 1) { //para que no tome los bordes sup e inf
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[inicios.get(ind) - 1].getEstado() == 1
                            && G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[fines.get(ind) + 1].getEstado() == 1) {
                        cutAux = cutAux + 1;
                    }
                }
            }
            //encuentra el/los menor/es
            if (cutAux < cuts) {
                //si hay un menor al menor, limpia el vector y solo deja ese
                cuts = cutAux;
                indKSPUbicMenCuts.clear();
                indKSPUbicMenCuts.add(ind);
            }else if(cutAux == cuts){
                indKSPUbicMenCuts.add(ind);
            }
            cutAux = 0;
            ind++;
        }
        
        //si hay un solo menor cut entonces es elegido, sino se calcula el alineamiento de los menores
        if (indKSPUbicMenCuts.size() == 1){
            r.setCamino(indiceKsp.get(indKSPUbicMenCuts.get(0)));
            r.setFin(fines.get(indKSPUbicMenCuts.get(0)));
            r.setInicio(inicios.get(indKSPUbicMenCuts.get(0)));
        }else {
            //calcula el desalineamiento de cada uno
            for (int indMenorCuts : indKSPUbicMenCuts) {                
                //calcula el desalineamiento de cada uno
                int Desalineacion = 0;
                int bandEsRuta = 0;
                for (Nodo n = kspUbicados.get(indMenorCuts).getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    for (int i = 0; i < G.getCantidadDeVertices(); i++) {
                        if(G.acceder(n.getDato(), i)!=null){ //si son vecinos
                            bandEsRuta = 0;
                            for (Nodo nn = kspUbicados.get(indMenorCuts).getInicio(); nn.getSiguiente().getSiguiente() != null; nn = nn.getSiguiente()) {
                                if(nn.getDato() == i){ //si es parte de la ruta
                                    bandEsRuta = 1;
                                    break;
                                }
                            }
                            if(bandEsRuta == 0){
                                for(int fsd = inicios.get(indMenorCuts); fsd <= inicios.get(indMenorCuts) + demanda.getNroFS() - 1; fsd++){
                                    if (G.acceder(i, n.getDato()).getFS()[fsd].getEstado() == 1) {
                                        Desalineacion = Desalineacion + 1;
                                    } else {
                                        Desalineacion = Desalineacion - 1;
                                    }
                                }
                            }
                        }
                    }
                }

                if (Desalineacion < DesalineacionFinal) {
                    //si hay un menor al menor, limpia el vector y solo deja ese
                    DesalineacionFinal = Desalineacion;
                    indKSPUbicMenCutsMenDesalig.clear();
                    indKSPUbicMenCutsMenDesalig.add(indMenorCuts);
                }else if(Desalineacion == DesalineacionFinal){
                    indKSPUbicMenCutsMenDesalig.add(indMenorCuts);
                }
            }
            
            //si hay un solo menor cut con menor desalineación entonces es elegido, sino envie al shorter KSP y hace first Fit
            if (indKSPUbicMenCutsMenDesalig.size() == 1){
                r.setCamino(indiceKsp.get(indKSPUbicMenCutsMenDesalig.get(0)));
                r.setFin(fines.get(indKSPUbicMenCutsMenDesalig.get(0)));
                r.setInicio(inicios.get(indKSPUbicMenCutsMenDesalig.get(0)));
            }else { //calcula el shorter KSP y hace first Fit  
                //encontrar el ksp más corto
                int tamKspMasCorto = 999;
                int indKspMasCorto = -1;
                for (int indMenCutsMenDesalig : indKSPUbicMenCutsMenDesalig) {
                    if (kspUbicados.get(indMenCutsMenDesalig).getTamanho() - 1 < tamKspMasCorto){
                        indKspMasCorto = indMenCutsMenDesalig;
                        tamKspMasCorto = kspUbicados.get(indMenCutsMenDesalig).getTamanho() - 1;
                    }
                }
                //buscar el indice first fit
                //Inicializadomos el espectro, inicialmente todos los FSs estan libres
                for(int i=0;i<capacidad;i++){
                    OE[i]=1;
                }
                //Calcular la ocupacion del espectro para cada camino k
                for(int i=0;i<capacidad;i++){
                    for(Nodo n=ksp[indiceKsp.get(indKspMasCorto)].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                        if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                            OE[i]=0;
                            break;
                        }
                    }
                }
                cont=0; 
                for(int i=0;i<capacidad;i++){
                    if(OE[i]==1){
                        cont++;
                    }else if (OE[i]==0){
                        cont=0;
                    }
                    //si se encontro un bloque valido, tomamos en cuenta el ksp
                    if(cont==demanda.getNroFS()){ //si o si va a encontrar ya que es un ksp en kspUbicados
                        r.setCamino(indiceKsp.get(indKspMasCorto));
                        r.setFin(i);
                        r.setInicio(i - cont + 1);
                        break; //solo el primero que encuentra
                    }
                }
            }
        }

        return r;
    }
}
