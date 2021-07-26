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
public class Algoritmos_Defrag {
    
    /*
    Algoritmo MSGD: Routing:KSP(2) SA:FF
    @Param
        G: Matriz de la topologia
        demanda: la demanada (origen, destino, nroFS)
        ksp: los k caminos mas cortos que satisfacen la demanda
        capacidad: capacidade de FS por enlace especificada en el simulador
    */
    public static Resultado Def_MSGD(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        int inicio=0, fin=0,cont; 
        int demandaColocada=0;
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres 1=libre 0=ocupado
        /*for(int i=0;i<capacidad;i++){
            OE[i]=1;
        }
        */
        
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            //Inicializadomos el espectro, inicialmente todos los FSs estan libres POR CADA CAMINO 1=libre 0=ocupado
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }

            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0){
                        OE[i]=0;
                        break;
                    }
                }
            }

            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            demandaColocada=1;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado. r fin e inicio son los indices del FS a usar
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    
    public static Resultado Def_FACA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<ListaEnlazada>();
        ArrayList<Integer> inicios = new ArrayList<Integer>();
        ArrayList<Integer> fines = new ArrayList<Integer>();
        ArrayList<Integer> indiceKsp = new ArrayList<Integer>();

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
           
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            fines.add(fin);
                            inicios.add(inicio);
                            demandaColocada=1;
                            kspUbicados.add(ksp[k]);
                            indiceKsp.add(k);
                            //inicio=fin=cont=0;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                    demandaColocada = 0;
                    break;
                }
            }
            k++;
        }
        
        /*if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }*/
        
        if (kspUbicados.isEmpty()){
            //System.out.println("Desubicado");
            return null;
        }
        
        int [] cortesSlots = new int [2];
        double corte = -1;
        double Fcmt = 9999999;
        double FcmtAux = -1;
        
        int caminoElegido = -1;

        //controla que exista un resultado
        boolean nulo = true;

        ArrayList<Integer> indiceL = new ArrayList<Integer>();
        
        //contar los cortes de cada candidato
        for (int i=0; i<kspUbicados.size(); i++){
            cortesSlots = Utilitarios.nroCuts(kspUbicados.get(i), G, capacidad);
            if (cortesSlots != null){
                corte = (double)cortesSlots[0];
                
                indiceL = Utilitarios.buscarIndices(kspUbicados.get(i), G, capacidad);
                
                
                //contar los desalineamientos
                double desalineamiento = (double)Utilitarios.contarDesalineamiento(kspUbicados.get(i), G, capacidad, cortesSlots[1]);
                
                double capacidadLibre = (double)Utilitarios.contarCapacidadLibre(kspUbicados.get(i),G,capacidad);
                
                double saltos = (double)Utilitarios.calcularSaltos(kspUbicados.get(i));
                
                
                double vecinos = (double)Utilitarios.contarVecinos(kspUbicados.get(i),G,capacidad);
                

                
                FcmtAux = corte + (desalineamiento/(demanda.getNroFS()*vecinos)) + (saltos *(demanda.getNroFS()/capacidadLibre)); 
                
                if (FcmtAux<Fcmt){
                    Fcmt = FcmtAux;
                    caminoElegido = i;
                }
                
                nulo = false;
                if (caminoElegido==-1){
                    System.out.println("Camino Elegido = -1 ..................");
                }
                
            }
        }
        
        if (caminoElegido==-1){
            System.out.println("Camino Elegido = -1 ..................");
        }
        //caminoElegido = Utilitarios.contarCuts(kspUbicados, G, capacidad);
    
        if (nulo || caminoElegido==-1){
            return null;
        }
        
        Resultado r= new Resultado();
        /*r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);*/
        
        r.setCamino(indiceKsp.get(caminoElegido));
        r.setFin(fines.get(caminoElegido));
        r.setInicio(inicios.get(caminoElegido));
        return r;
    }
    
     public static Resultado Def_FA(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<ListaEnlazada>();
        ArrayList<Integer> inicios = new ArrayList<Integer>();
        ArrayList<Integer> fines = new ArrayList<Integer>();
        ArrayList<Integer> indiceKsp = new ArrayList<Integer>();

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
           
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            fines.add(fin);
                            inicios.add(inicio);
                            demandaColocada=1;
                            kspUbicados.add(ksp[k]);
                            indiceKsp.add(k);
                            //inicio=fin=cont=0;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                    demandaColocada=0;
                    break;
                }
            }
            k++;
        }
        
        /*if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }*/
        
        if (kspUbicados.isEmpty()){
            //System.out.println("Desubicado");
            return null;
        }
        
        
        
        int caminoElegido = Utilitarios.contarCuts(kspUbicados, G, capacidad);
    
        Resultado r= new Resultado();
        /*r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);*/
        
        r.setCamino(indiceKsp.get(caminoElegido));
        r.setFin(fines.get(caminoElegido));
        r.setInicio(inicios.get(caminoElegido));
        return r;
    }
     
    /*
     Algoritmo de Defragmentacion PCF
    @param
        G - red actual
        demanda - demanda a ser ubicada
        ksp - lista de caminos mas cortos
        capacidad - cantidad de FS por enlace
    @return
        resultado para ubicar la demanda en la red
    */
    public static Resultado PCF(GrafoMatriz G, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        //*Definicion de variables las variables
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        ArrayList<ListaEnlazada> kspUbicados = new ArrayList<ListaEnlazada>();
        ArrayList<Integer> inicios = new ArrayList<Integer>();
        ArrayList<Integer> fines = new ArrayList<Integer>();
        ArrayList<Integer> indiceKsp = new ArrayList<Integer>();

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
           
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            fines.add(fin);
                            inicios.add(inicio);
                            demandaColocada=1;
                            kspUbicados.add(ksp[k]);
                            indiceKsp.add(k);
                            //inicio=fin=cont=0;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                    demandaColocada = 0;
                    break;
                }
            }
            k++;
        }
        
        /*if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }*/
        
        if (kspUbicados.isEmpty()){
            //System.out.println("Desubicado");
            return null;
        }
        
        int [] cortesSlots = new int [2];
        double corte = -1;
        double Fcmt = 9999999;
        double FcmtAux = -1;
        
        int caminoElegido = -1;

        //controla que exista un resultado
        boolean nulo = true;

        ArrayList<Integer> indiceL = new ArrayList<Integer>();
        
        //contar los cortes de cada candidato
        for (int i=0; i<kspUbicados.size(); i++){
            cortesSlots = Utilitarios.nroCuts(kspUbicados.get(i), G, capacidad);
            if (cortesSlots != null){
                
                corte = (double)cortesSlots[0];
                
                indiceL = Utilitarios.buscarIndices(kspUbicados.get(i), G, capacidad);
                
                double saltos = (double)Utilitarios.calcularSaltos(kspUbicados.get(i));
                
                double slotsDemanda = demanda.getNroFS();
                
                //contar los desalineamientos
                double desalineamiento = (double)Utilitarios.contarDesalineamiento(kspUbicados.get(i), G, capacidad, cortesSlots[1]);
                
                double capacidadLibre = (double)Utilitarios.contarCapacidadLibre(kspUbicados.get(i),G,capacidad);
                
               
                
                
               // double vecinos = (double)Utilitarios.contarVecinos(kspUbicados.get(i),G,capacidad);
                

                
                //FcmtAux = corte + (desalineamiento/(demanda.getNroFS()*vecinos)) + (saltos *(demanda.getNroFS()/capacidadLibre)); 
                
                FcmtAux = ((saltos*slotsDemanda) + corte + desalineamiento)/capacidadLibre;
                
                if (FcmtAux<Fcmt){
                    Fcmt = FcmtAux;
                    caminoElegido = i;
                }
                
                nulo = false;
                if (caminoElegido==-1){
                    System.out.println("Camino Elegido = -1 ..................");
                }
                
            }
        }
        
        if (caminoElegido==-1){
            System.out.println("Camino Elegido = -1 ..................");
        }
        //caminoElegido = Utilitarios.contarCuts(kspUbicados, G, capacidad);
    
        if (nulo || caminoElegido==-1){
            return null;
        }
        
        Resultado r= new Resultado();
        /*r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);*/
        
        r.setCamino(indiceKsp.get(caminoElegido));
        r.setFin(fines.get(caminoElegido));
        r.setInicio(inicios.get(caminoElegido));
        return r;
    }
    
    
        /*
     Algoritmod de Defragmentacion Double Window Defragmentation
    @param
        dR - ultima demanda cargada
        G - red actual
        lea - lista de todas las conexiones establecidas
        capacidad - capacida de FS  por enlace
    */
    public static void DWD(Demanda dR, GrafoMatriz G, ArrayList<ListaEnlazadaAsignadas> lea, int capacidad){
        
        int indexDem= Utilitarios.buscarDemandaLifetime(lea,dR, G);
        if (indexDem != -1){
            Demanda d = lea.get(indexDem).getDemanda();
            ListaEnlazada[] ksp=Utilitarios.KSP(G, d.getOrigen(), d.getDestino(), 5); 
            Resultado rNueva = Utilitarios.buscarEspacio(ksp,d, G, capacidad);
            if (rNueva!=null){
                Utilitarios.asignarFS_saveRoute(ksp,rNueva,G,d,lea,-1);
                Utilitarios.limpiarCaminoAnterior(lea.get(indexDem),G,indexDem,capacidad, lea);
                
            }
        
        }
        
    }
    
      /*
        MSGD - Algoritmo de Defragmentacion basado en una formula que calcula el 
        spectrum gain. El de mayor spectrum gain es el elegido para ser reruteado
        @Param
            lea - todos los caminos en la red
            caminosEliminados - caminos candidatos para el posible reruteo
             R - probabilidad de reruteo
    */
    public static ListaEnlazadaAsignadas MSGD(ArrayList<ListaEnlazadaAsignadas> cCandidatos, ListaEnlazadaAsignadas caminoLibre, int R){
        
        //vector de spectrum gain de los candidatos
        int[] sgain = new int[cCandidatos.size()];
        
        //inicializar vector
        for (int i=0; i<sgain.length; i++){
            sgain[i] = -1;
        }
        
        Random rdm =new Random();
        //si supera la probabilidad de reruteo
        if (rdm.nextInt(10)<=R){
            
            int saltosEliminado = Utilitarios.calcularSaltos(caminoLibre.getListaAsignada());
            for (int i=0; i<cCandidatos.size(); i++){
                //Si esta vivo
                if (cCandidatos.get(i).getEstado()==1){
                    int saltosCandidato = Utilitarios.calcularSaltos(cCandidatos.get(i).getListaAsignada());
                    int candidatoFS = cCandidatos.get(i).getDemanda().getNroFS();
                    sgain[i] = (saltosCandidato - saltosEliminado )*candidatoFS;
                            
                }
            }
        }
        
        //buscar el de mayor spectrum gain
        int mayor=sgain[0];
        int index = 0;
        for (int i=0; i<sgain.length; i++){
            if (sgain[i] > mayor){
                mayor = sgain[i];
                index = i;
            }
        }
        
        //Si ya es el optimo
        if (mayor>0){
            return cCandidatos.get(index);
        }else{
            return null;
        }

    }
    
    
    
    /*
     Algoritmo de Defragmentacion Make Before Break
    @param
        d - Demanda a ser asignada
        G - topologia de la red
        lea - lista de todas las conexiones
        capacidadE - cantidad de FS por enlace
        ksp - lista de caminos mas cortos
    @return
        r - resultado con la nueva asignacion
    */
    public static Resultado MBBR(Demanda d, GrafoMatriz G, GrafoMatriz Gaux, ArrayList<ListaEnlazadaAsignadas> lea, int capacidadE, ListaEnlazada [] ksp){
        
        int caminoMasCorto = 0;
        ArrayList<Integer> caminosMovidos = new ArrayList<>();
        ListaEnlazada [] kspResults;
        
        //se busca el caminos mas corto
        for (int i=1; i<ksp.length; i++){
            if (Utilitarios.calcularSaltos(ksp[i])<Utilitarios.calcularSaltos(ksp[caminoMasCorto])){
                caminoMasCorto = i;
            }
        }
        
        caminosMovidos = Utilitarios.obtenerCaminosMovidos(ksp[caminoMasCorto], G, d);
        
        if (caminosMovidos.size()==0)
            System.out.println("Que bola");
        
        Resultado [] results = Utilitarios.buscarCaminosAlternativos(caminosMovidos, lea, G, Gaux, capacidadE);
        
        if (results!=null){
            for (int i=0; i<results.length; i++){
                kspResults = Utilitarios.KSP(G, lea.get(caminosMovidos.get(i)).getDemanda().getOrigen(), 
                        lea.get(caminosMovidos.get(i)).getDemanda().getDestino(), 5);
                Utilitarios.asignarFS_saveRoute(kspResults, results[i], G, d, lea, -1);
                
                Utilitarios.limpiarCaminoAnterior(lea.get(caminosMovidos.get(i)),G,caminosMovidos.get(i),capacidadE, lea);
            }
            Resultado r = new Resultado();
            r.setCamino(caminoMasCorto);
            r.setInicio(0);
            r.setFin(d.getNroFS()-1);
            
            return r;
        }
        return null;
    }
    
    /*
     Algoritmo de Defragementacion Comprehensive Bandwidth Defragmentation
    @param
        G - red actual
        Gaux - red auxiliar
        lea - lista de todas las conexiones en la red
        selectos - lista de conexiones a ser reruteadas
    */
    
    public static void CBD(GrafoMatriz G, GrafoMatriz Gaux, ArrayList<ListaEnlazadaAsignadas> lea, int[][] selectos, int capacidad){
        
        Utilitarios.cargarEnGrafo(G,Gaux,capacidad);
        for (int i=0; i<selectos.length; i++){
            Utilitarios.borrarCaminos(Gaux,selectos[i][1], lea, capacidad);
        }
        ListaEnlazada [] ksp;
        Resultado r;
        Resultado [] results = new Resultado[selectos.length];
        Demanda d;
        for (int i=0; i<selectos.length; i++){
            d = lea.get(selectos[i][1]).getDemanda();
            
            ksp = Utilitarios.KSP(Gaux, d.getOrigen(), d.getDestino(), 5);
            r = Utilitarios.buscarEspacioCBD(ksp, d, selectos[i][0], Gaux, capacidad);
            if (r!=null){
                Utilitarios.asignarFS_Defrag(ksp,r,Gaux,d,-1);
                results[i] = r;
            }else{
                results[i] = new Resultado();
                results[i].setCamino(-1);
            }
        }
            
        for (int i=0; i<results.length; i++){
            if (results[i].getCamino()!=-1){
                //Utilitarios.borrarCaminos(G, selectos[i], lea, capacidad);
                
                d = lea.get(selectos[i][1]).getDemanda();
                ksp = Utilitarios.KSP(G, d.getOrigen(), d.getDestino(), 5);
                Utilitarios.asignarFS_saveRoute(ksp,results[i],G,d,lea, -1);
                Utilitarios.limpiarCaminoAnterior(lea.get(selectos[i][1]), G, selectos[i][1], capacidad, lea);
                //System.out.println("Se movio " + selectos[i][1] + " ahora es " + (lea.size()-1));
                
            }
        }
    }

    
    
       /*
    * Algoritmo KSP-Fist Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * 
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
    */
    public static Resultado KSP_FF_Algorithm_MBBR(GrafoMatriz G, GrafoMatriz Gaux, Demanda demanda,ListaEnlazada [] ksp,int capacidad){
        
        /*Definicion de variables las variables*/
        int inicio=0, fin=0,cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada=0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int [] OE= new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k=0;
        while(k<ksp.length && ksp[k]!=null && demandaColocada==0){
            for(int i=0;i<capacidad;i++){
                OE[i]=1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for(int i=0;i<capacidad;i++){
                for(Nodo n=ksp[k].getInicio();n.getSiguiente().getSiguiente()!=null;n=n.getSiguiente()){
                    if(G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0 ||
                            Gaux.acceder(n.getDato(),n.getSiguiente().getDato()).getFS()[i].getEstado()==0 ){
                        OE[i]=0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
            */
            inicio=fin=cont=0;
            for(int i=0;i<capacidad;i++){
                if(OE[i]==1){
                    inicio=i;
                    for(int j=inicio;j<capacidad;j++){
                        if(OE[j]==1){
                            cont++;
                        }
                        else{
                            cont=0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if(cont==demanda.getNroFS()){
                            fin=j;
                            demandaColocada=1;
                            break;
                        }
                    }
                }
                if(demandaColocada==1){
                        break;
                }
            }
            k++;
        }
        
        if(demandaColocada==0){
                return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
        */
        Resultado r= new Resultado();
        r.setCamino(k-1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }
    
}
