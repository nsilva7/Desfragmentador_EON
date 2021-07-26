/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON.Utilitarios;
import EON.Demanda;
import EON.GrafoMatriz;
import EON.ListaEnlazada;
import EON.Metricas.Metricas;
import EON.Nodo;
import EON.Resultado;
import static EON.Utilitarios.Utilitarios.asignarFS_Defrag;
import static EON.Utilitarios.Utilitarios.compararRutas;
import static EON.Utilitarios.Utilitarios.copiarGrafo;
import static EON.Utilitarios.Utilitarios.desasignarFS_DefragProAct;
import static EON.Utilitarios.Utilitarios.elegirRuta;
import static EON.Utilitarios.Utilitarios.isInList;
import static EON.Utilitarios.Utilitarios.obtenerFin;
import static EON.Utilitarios.Utilitarios.ordenarProbabilidad;
import static EON.Utilitarios.Utilitarios.realizarRuteo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 *
 * @author Fabi
 */
//un cromosoma representa un individuo de la población que para nosotros es una solución al problema
public class Cromosoma {
    ArrayList<Integer> listaGenes ;
    HashMap<Integer, Integer> genesMap ;//solo para controlar que los genes creados no sean repetidos
    String seLogroMejora;
    Integer nroReconf;
    double mejora;
    GrafoMatriz grafoMejor;
    ArrayList<Integer> indexOrden = new ArrayList<>();
    ArrayList<Resultado> resultadosActualElegidas = new ArrayList<>();
    ArrayList<Resultado> resultadosMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultados
    ArrayList<ListaEnlazada> rutasMejor = new ArrayList<>(); //arrayList que guarda el mejor conjunto de resultado
    ArrayList<Integer> indicesMejor = new ArrayList<>(); //arrayList que 
    int cantidadRutasMejor=0;
    public Cromosoma(){
     listaGenes = new ArrayList<>();
     genesMap = new HashMap<>();//solo para controlar que los genes creados no sean repetidos 
     nroReconf=0;
     seLogroMejora ="NO";
    }

   
   public Cromosoma solucionesCandidatas(ArrayList<ListaEnlazada> rutas,double [][][]topologia,GrafoMatriz G,int capacidadFSporEnlace,ArrayList<Resultado> resultados,
        ArrayList<ListaEnlazada[]> listaKSP, String algoritmoAejecutar,String objetivoAG,int porcentajeLong){
     
        boolean porBfr = false,porMsi = false;
        double bfrGrafo = 0,msiGrafo=0;
        double mejoraActual;
        Resultado rparcial;
        GrafoMatriz copiaGrafo =  new GrafoMatriz(G.getCantidadDeVertices());
        copiaGrafo.insertarDatos(topologia);
        ArrayList<Integer> indexOrden = new ArrayList<>();
        Cromosoma cr = new Cromosoma();
        int cantReruteosIguales = 0; //para sumar la cantidad de reruteos que quedaron en con los mismos caminos (enlaces y FS)
        ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();;  //guarda las rutas elegidas por una hormiga
        ArrayList<Integer> indicesElegidas = new ArrayList<>(); //guarda los indices de las rutas elegidas en el vector rutas
         
        for (int i =0; i<rutas.size() ; i++){
            indexOrden.add(i);
        }
        //se calcula la longitud del cromosoma
        int longCromosoma = Math.round((rutas.size()*porcentajeLong)/100);
        //Selecciona el objetivo del algoritmo ACO
        switch (objetivoAG) {
           case "BFR":
            porBfr = true;
            bfrGrafo = Metricas.BFR(G, capacidadFSporEnlace);
            break;
           case "MSI":
            porBfr = false;
            porMsi = true;
            msiGrafo = Metricas.MSI(G, capacidadFSporEnlace);
            break;
        }

        rutasElegidas.clear();
        indicesElegidas.clear();
        mejoraActual=0;
  
        for(int i=0;i<longCromosoma;i++){
           indicesElegidas.add(indexOrden.get(elegirRutaAG(indicesElegidas,rutas.size())));
           rutasElegidas.add(rutas.get(indicesElegidas.get(i))); 
        }
        
        //while(mejoraActual<mejora && cont<rutas.size()){
           cantReruteosIguales = 0;
           //Crear la copia del grafo original manualmente
           copiarGrafo(copiaGrafo, G, capacidadFSporEnlace);
           desasignarFS_DefragProAct(rutasElegidas, resultados, copiaGrafo, indicesElegidas); //desasignamos los FS de las rutas a reconfigurar                
           //ORDENAR LISTA
           if (rutasElegidas.size()>1){
            ordenarRutas(resultados, rutasElegidas, indicesElegidas, rutasElegidas.size());
           }
           //volver a rutear con las nuevas condiciones mismo algoritmo
           int contBloqueos =0;
           for (int i=0; i<rutasElegidas.size(); i++){
            int fs = resultados.get(indicesElegidas.get(i)).getFin() - resultados.get(indicesElegidas.get(i)).getInicio();
            fs++;
            int tVida = G.acceder(rutas.get(indicesElegidas.get(i)).getInicio().getDato(),rutas.get(indicesElegidas.get(i)).getInicio().getSiguiente().getDato()).getFS()[resultados.get(indicesElegidas.get(i)).getInicio()].getTiempo();
            Demanda demandaActual = new Demanda(rutasElegidas.get(i).getInicio().getDato(), obtenerFin(rutasElegidas.get(i).getInicio()).getDato(), fs, tVida);
            ListaEnlazada[] ksp = listaKSP.get(indicesElegidas.get(i));
            rparcial = realizarRuteo(algoritmoAejecutar,demandaActual,copiaGrafo, ksp,capacidadFSporEnlace);
            if (rparcial != null) {
              asignarFS_Defrag(ksp, rparcial, copiaGrafo, demandaActual, 0);
              //verificar si eligio el mismo camino y fs para no sumar en reruteadas
              if (compararRutas(rparcial,resultados.get(indicesElegidas.get(i)))){
              cantReruteosIguales++;
              }
            } else {
                contBloqueos++;
              }
            }
            //si hubo bloqueo no debe contar como una solucion
            if(contBloqueos==0){
             mejoraActual = Utilitarios.calculoMejoraAG(porMsi,porBfr,copiaGrafo,bfrGrafo,capacidadFSporEnlace,msiGrafo);
            } else {
             mejoraActual = 0;
            }         
            if(mejoraActual>0){
            for(int i= 0;i<indicesElegidas.size(); i++){
              listaGenes.add(indicesElegidas.get(i));
            }
            cr.setListaGenes(listaGenes);
            cr.setNroReconf(listaGenes.size()-cantReruteosIguales);
            cr.setMejora(mejoraActual);
            cr.setSeLogroMejora("NO");
            }else{
                listaGenes.clear();
                genesMap.clear();
                cr.genesMap.clear();
                cr.listaGenes.clear();
            }
      return cr;
    }

      /*Metodo que se encarga de desasignar los FS de una ruta marcada para reconfiguracion en el grafo matriz copia*/
    public static void desasignarFS(ArrayList<ListaEnlazada> rutas, ArrayList<Resultado> r, GrafoMatriz G, ArrayList<Integer> indices) {
        for (int i =0; i<rutas.size(); i++){
            for (Nodo nod = rutas.get(i).getInicio(); nod.getSiguiente().getSiguiente() != null; nod = nod.getSiguiente()) {
                for (int p = r.get(indices.get(i)).getInicio(); p <= r.get(indices.get(i)).getFin(); p++) {
                    if (G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].getEstado() == 1){
                        System.out.println("CONFLICTO AL DESASIGNAR UN SLOT ida. (NO ESTA LUEGO ASIGNADO). Nodo: " + nod.getDato() + ", Posición: " + p );
                    }
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setEstado(1);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setTiempo(0);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).getFS()[p].setConexion(1);
                    G.acceder(nod.getDato(), nod.getSiguiente().getDato()).setUtilizacionFS(p, 0);
                    if (G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].getEstado() == 1){
                        System.out.println("CONFLICTO AL DESASIGNAR UN SLOT vuelta. (NO ESTA LUEGO ASIGNADO). Nodo: " + nod.getDato() + ", Posición: " + p );
                    }
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setEstado(1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setConexion(-1);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).getFS()[p].setTiempo(0);
                    G.acceder(nod.getSiguiente().getDato(), nod.getDato()).setUtilizacionFS(p, 0);
                }
            }
        }
    }

     //Metodo que ordena las rutas elegidas por las hormigas para su posterior re-ruteo 
    //por orden decreciente de cantidad de FS requeridos
    public static void ordenarRutas(ArrayList<Resultado> resultados, ArrayList<ListaEnlazada> rutas, ArrayList<Integer> indices,int rutasElegidasSize){
        Integer aux = 0;
        ListaEnlazada aux2 = new ListaEnlazada();
        for (int i = 0; i <= rutasElegidasSize - 1; i++) {
            for (int j = i + 1; j < rutasElegidasSize; j++) {
                Integer fin = resultados.get(indices.get(j)).getFin()-resultados.get(indices.get(j)).getInicio();
                Integer inicio = resultados.get(indices.get(i)).getFin()-resultados.get(indices.get(i)).getInicio();
                if ( fin > inicio) {
                    //cambia el orden del array de indices
                    aux = indices.get(i);
                    indices.set(i,indices.get(j));
                    indices.set(j, aux);
                    
                    //cambia el orden en el array de rutas
                    aux2 = rutas.get(i);
                    rutas.set(i,rutas.get(j));
                    rutas.set(j, aux2);
                }
            }
        }
    }
    
    public HashMap<Integer, Integer> getGenesMap() {
        return genesMap;
    }

    public void setGenesMap(HashMap<Integer, Integer> genesMap) {
        this.genesMap = genesMap;
    }

    public String getSeLogroMejora() {
        return seLogroMejora;
    }

    public void setSeLogroMejora(String seLogroMejora) {
        this.seLogroMejora = seLogroMejora;
    }
    
    public ArrayList<Integer> getListaGenes() {
        return listaGenes;
    }

    public void setListaGenes(ArrayList<Integer> listaGenes) {
        this.listaGenes = listaGenes;
    }

    public Integer getNroReconf() {
        return nroReconf;
    }

    public void setNroReconf(Integer nroReconf) {
        this.nroReconf = nroReconf;
    }

    public double getMejora() {
        return mejora;
    }

    public void setMejora(double mejora) {
        this.mejora = mejora;
    }

    public GrafoMatriz getGrafoMejor() {
        return grafoMejor;
    }

    public void setGrafoMejor(GrafoMatriz grafoMejor) {
        this.grafoMejor = grafoMejor;
    }

    public ArrayList<Integer> getIndexOrden() {
        return indexOrden;
    }

    public void setIndexOrden(ArrayList<Integer> indexOrden) {
        this.indexOrden = indexOrden;
    }

    public ArrayList<Resultado> getResultadosActualElegidas() {
        return resultadosActualElegidas;
    }

    public void setResultadosActualElegidas(ArrayList<Resultado> resultadosActualElegidas) {
        this.resultadosActualElegidas = resultadosActualElegidas;
    }

    public ArrayList<Resultado> getResultadosMejor() {
        return resultadosMejor;
    }

    public void setResultadosMejor(ArrayList<Resultado> resultadosMejor) {
        this.resultadosMejor = resultadosMejor;
    }

    public ArrayList<ListaEnlazada> getRutasMejor() {
        return rutasMejor;
    }

    public void setRutasMejor(ArrayList<ListaEnlazada> rutasMejor) {
        this.rutasMejor = rutasMejor;
    }

    public ArrayList<Integer> getIndicesMejor() {
        return indicesMejor;
    }

    public void setIndicesMejor(ArrayList<Integer> indicesMejor) {
        this.indicesMejor = indicesMejor;
    }

    public int getCantidadRutasMejor() {
        return cantidadRutasMejor;
    }

    public void setCantidadRutasMejor(int cantidadRutasMejor) {
        this.cantidadRutasMejor = cantidadRutasMejor;
    }

        public Cromosoma buenasSoluciones(ArrayList<ListaEnlazada> rutas,double [][][]topologia,GrafoMatriz G,
            int capacidadFSporEnlace,ArrayList<Resultado> resultados,ArrayList<ListaEnlazada[]> listaKSP, String algoritmoAejecutar,
            int porcentajeLongCRAG,String objetivoAG,int cantidadBuenasSol,ArrayList<Integer> mejorSol,int cont){
            boolean  porBfr = false, porMsi = false;
            double  bfrGrafo = 0, msiGrafo=0;
            double mejoraActual;
            Resultado rparcial;
            GrafoMatriz copiaGrafo =  new GrafoMatriz(G.getCantidadDeVertices());
            copiaGrafo.insertarDatos(topologia);
            Cromosoma cr = new Cromosoma();
            int cantReruteosIguales; //para sumar la cantidad de reruteos que quedaron en con los mismos caminos (enlaces y FS)
            ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();  //guarda las rutas elegidas para un cromosoma
            ArrayList<Integer> indicesElegidas = new ArrayList<>(); //guarda los indices de las rutas elegidas en el vector rutas
            ArrayList<Integer> solucion = new ArrayList<Integer>();

            //se calcula la longitud del cromosoma
            int longCromosoma = Math.round((rutas.size()*porcentajeLongCRAG)/100);
            //Selecciona el objetivo del AG
            switch (objetivoAG) {
             case "BFR":
                 porBfr = true;
                 porMsi = false;
                 bfrGrafo = Metricas.BFR(G, capacidadFSporEnlace);
                 break;
             case "MSI":
                 porBfr = false;
                 porMsi = true;
                 msiGrafo = Metricas.MSI(G, capacidadFSporEnlace);
                 break;
             }
            //Crear una buena solucion,dependiendo de la metrica estudiada
            switch (objetivoAG) {
             case "BFR":
              solucion = crearSolucionBFR(resultados,rutas,longCromosoma,cantidadBuenasSol,mejorSol,G,capacidadFSporEnlace,cont);
              break;
             case "MSI":
              solucion = crearSolucionMSI(resultados,rutas,longCromosoma,cantidadBuenasSol,mejorSol,cont);
              break;
            }
            //Evaluar la solucion propuesta
            rutasElegidas.clear();
            indicesElegidas.clear();
            mejoraActual=0;  
            for(int i=0;i<longCromosoma;i++){
               indicesElegidas.add(solucion.get(i));
               rutasElegidas.add(rutas.get(indicesElegidas.get(i))); 
            }
            cantReruteosIguales = 0;
            //Crear la copia del grafo original manualmente
            copiarGrafo(copiaGrafo, G, capacidadFSporEnlace);
            desasignarFS_DefragProAct(rutasElegidas, resultados, copiaGrafo, indicesElegidas); //desasignamos los FS de las rutas a reconfigurar                
            //Ordena las rutas por requerimiento de FS
            if (rutasElegidas.size()>1){
             ordenarRutas(resultados, rutasElegidas, indicesElegidas, rutasElegidas.size());
            }
            //volver a rutear con las nuevas condiciones mismo algoritmo
            int contBloqueos =0;
            for (int i=0; i<rutasElegidas.size(); i++){
                int fs = resultados.get(indicesElegidas.get(i)).getFin() - resultados.get(indicesElegidas.get(i)).getInicio();
                fs++;
                int tVida = G.acceder(rutas.get(indicesElegidas.get(i)).getInicio().getDato(),rutas.get(indicesElegidas.get(i)).getInicio().getSiguiente().getDato()).getFS()[resultados.get(indicesElegidas.get(i)).getInicio()].getTiempo();
                Demanda demandaActual = new Demanda(rutasElegidas.get(i).getInicio().getDato(), obtenerFin(rutasElegidas.get(i).getInicio()).getDato(), fs, tVida);
                ListaEnlazada[] ksp = listaKSP.get(indicesElegidas.get(i));
                rparcial = realizarRuteo(algoritmoAejecutar,demandaActual,copiaGrafo, ksp,capacidadFSporEnlace);
                if (rparcial != null) {
                  asignarFS_Defrag(ksp, rparcial, copiaGrafo, demandaActual, 0);
                  //verificar si eligio el mismo camino y fs para no sumar en reruteadas
                  if (compararRutas(rparcial,resultados.get(indicesElegidas.get(i)))){
                  cantReruteosIguales++;
                  }
                } else {
                  contBloqueos++;
                }
            }
            //si hubo bloqueo no debe contar como una solucion
            if(contBloqueos==0){
             mejoraActual = Utilitarios.calculoMejoraAG(porMsi,porBfr,copiaGrafo,bfrGrafo,capacidadFSporEnlace,msiGrafo);
             } else {
             mejoraActual = 0;
            }         
            if(mejoraActual>0){
                for(int i= 0;i<indicesElegidas.size(); i++){
                  listaGenes.add(indicesElegidas.get(i));
                }
                cr.setListaGenes(listaGenes);
                cr.setNroReconf(listaGenes.size()-cantReruteosIguales);
                cr.setMejora(mejoraActual);
                cr.setSeLogroMejora("NO");
            }else{//si no es solucion retorna un solucion vacia
                listaGenes.clear();
                cr.listaGenes.clear();}
          return cr;
        }

   //crear una solucion teniendo en cuenta el msi
   public static ArrayList<Integer> crearSolucionMSI(ArrayList<Resultado> resultados, 
        ArrayList<ListaEnlazada> rutas,int longCromosoma,int cantSoluciones,
        ArrayList<Integer> mejorSolucion,int cont){
        double[] valorMSIrutas = new double[rutas.size()];
        ArrayList<Integer> indexOrden = new ArrayList<>();
       // ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> mejorSolucionAux = new ArrayList<>();
        ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();
        ArrayList<Integer> indicesElegidas = new ArrayList<>();
        int rutasQueParticipan = 0;
        int aux=-1;
        double sumatoria;
        double[] probabilidad = new double[rutas.size()];
        for (int i =0; i<mejorSolucion.size() ; i++){
            mejorSolucionAux.add(mejorSolucion.get(i));
        }
        if(cont == 0){
            for (int i =0; i<rutas.size() ; i++){
             valorMSIrutas[i] = resultados.get(i).getFin();//Obtengo los msi de las rutas  
            } 
            for (int i =0; i<valorMSIrutas.length ; i++){
             indexOrden.add(i);
            }
            //ordenar vector valorMSIrutas de acuerdo a su probabilidad
            ordenarRutasMsi(valorMSIrutas, indexOrden);
            int pos = valorMSIrutas.length-1;
            while(indicesElegidas.size()<longCromosoma){
               indicesElegidas.add(indexOrden.get(pos));
               pos = pos -1;
            }
        }else{
        for (int i =0; i<rutas.size() ; i++){
         valorMSIrutas[i] = resultados.get(i).getFin();//Obtengo los msi de las rutas  
        } 
        for (int i =0; i<valorMSIrutas.length ; i++){
          indexOrden.add(i);
        }
        //calcular la probabilidad
        sumatoria=0.0;
        for(int i=0; i<rutas.size(); i++){
          sumatoria = sumatoria+valorMSIrutas[i];
        }
        //for(int i=rutasQueParticipan+1;i<rutas.size();i++){
        for(int i=0;i<rutas.size();i++){
          probabilidad[i] = (valorMSIrutas[i])/sumatoria;
        }
        //ordenar vector indice de acuerdo a su probabilidad
        ordenarProbabilidad(probabilidad, indexOrden);
        for(int i=0;i<longCromosoma;i++){
           indicesElegidas.add(indexOrden.get(elegirRuta(probabilidad, indicesElegidas, indexOrden)));
        }  
        }
        return  indicesElegidas;
   }
   
   public static void ordenarRutasMsi(double[] listaRutasMsi, ArrayList<Integer> orden){
        double auxp;
        int auxi;
        int n = listaRutasMsi.length;
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (listaRutasMsi[i] > listaRutasMsi[j]) {
                    auxp = listaRutasMsi[i];
                    listaRutasMsi[i] = listaRutasMsi[j];
                    listaRutasMsi[j] = auxp;
                    
                    //cambiar el orden del vector de indices
                    auxi = orden.get(i);
                    orden.set(i,orden.get(j));
                    orden.set(j, auxi);
                }
            }
        }
    }
   //Ordenar las rutas por BFR
   public static void ordenarRutasBFR(double[] listaRutasBFR, ArrayList<Integer> orden){
        double auxp;
        int auxi;
        int n = listaRutasBFR.length;
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (listaRutasBFR[i] > listaRutasBFR[j]) {
                    auxp = listaRutasBFR[i];
                    listaRutasBFR[i] = listaRutasBFR[j];
                    listaRutasBFR[j] = auxp;
                    //cambiar el orden del vector de indices
                    auxi = orden.get(i);
                    orden.set(i,orden.get(j));
                    orden.set(j, auxi);
                }
            }
        }
    }
   
    public static int elegirRutaAG(ArrayList<Integer> indices,int rutasActivas){
        Random randomGenerator = new Random();
        int indiceAleatorio = randomGenerator.nextInt(rutasActivas);
        while(isInList(indices,indiceAleatorio)){
           indiceAleatorio = randomGenerator.nextInt(rutasActivas); 
        }
        return indiceAleatorio;
    }
    
     //crear una solucion teniendo en cuenta el BFR
   public static ArrayList<Integer> crearSolucionBFR(ArrayList<Resultado> resultados,ArrayList<ListaEnlazada> rutas,
        int longCromosoma,int cantSoluciones,ArrayList<Integer> mejorSolucion,GrafoMatriz G,int capacidad,int cont){
        double[] valorBFRrutas = new double[rutas.size()];
        ArrayList<Integer> indexOrden = new ArrayList<>();
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> mejorSolucionAux = new ArrayList<>();
        ArrayList<ListaEnlazada> rutasElegidas = new ArrayList<>();
        ArrayList<Integer> indicesElegidas = new ArrayList<>();
        int rutasQueParticipan = 0;
        int aux=-1;
        double sumatoria;
        double[] probabilidad = new double[rutas.size()];
        for (int i =0; i<mejorSolucion.size() ; i++){
            mejorSolucionAux.add(mejorSolucion.get(i));
        }
        if(cont == 0){
            for (int i =0; i<rutas.size() ; i++){  
             valorBFRrutas[i] = Utilitarios.BFRdeRuta(rutas.get(i),capacidad, G);//Obtengo los bfr de las rutas  
            } 
            for (int i =0; i<valorBFRrutas.length ; i++){
             indexOrden.add(i);
            }
            //ordenar vector valorBFRrutas de acuerdo a su probabilidad
            ordenarRutasBFR(valorBFRrutas, indexOrden);
            int pos = valorBFRrutas.length-1;
            while(indicesElegidas.size()<longCromosoma){
               indicesElegidas.add(indexOrden.get(pos));
               pos = pos -1;
            }
        }else{
            for (int i =0; i<rutas.size() ; i++){
            valorBFRrutas[i] = Utilitarios.BFRdeRuta(rutas.get(i),capacidad, G);//Obtengo los bfr de las rutas  
            } 
            for (int i =0; i<valorBFRrutas.length ; i++){
                indexOrden.add(i);
            }
           //calcular la probabilidad
           sumatoria=0.0;
           for(int i=0; i<rutas.size(); i++){
             sumatoria = sumatoria+valorBFRrutas[i];
           }
           //rutasQueParticipan = (rutas.size()*55)/100;//solo las 50 mejores participan en la ruleta
           for(int i=0;i<rutas.size();i++){
             probabilidad[i] = (valorBFRrutas[i])/sumatoria;
           }
           //ordenar vector indice de acuerdo a su probabilidad
           ordenarProbabilidad(probabilidad, indexOrden);
           for(int i=0;i<longCromosoma;i++){
            indicesElegidas.add(indexOrden.get(elegirRuta(probabilidad, indicesElegidas, indexOrden)));
           }
        }
        return  indicesElegidas;
   }
   
}