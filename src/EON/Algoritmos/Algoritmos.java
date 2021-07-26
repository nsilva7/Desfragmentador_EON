package EON.Algoritmos;

import EON.*;
import EON.ResultadoRuteo;
import EON.Demanda;
import EON.FrecuencySlots;
import EON.GrafoMatriz;
import EON.ListaEnlazada;
import EON.Nodo;
import EON.Resultado;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import EON.Utilitarios.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Fernando
 */
public class Algoritmos {

    public static ResultadoRuteo Ruteo_FA(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {
        int i, j, des, suma, k;
        ResultadoRuteo resultado = null;
        ArrayList<Enlace> paresLinks = new ArrayList<Enlace>();
        ArrayList<Integer> minimo = new ArrayList<Integer>();
        ArrayList<Integer> desalineamiento = new ArrayList<Integer>();
        ArrayList<Integer> cuts = new ArrayList<Integer>();
        ArrayList<Integer> desMin = new ArrayList<Integer>();
        desalineamiento = new ArrayList();
        Nodo n;
        des = suma = 0;
        //Para cada ruta candidata en ksp calcular el numero de cuts
        for (i = 0; i < ksp.length; i++) {
            for (n = ksp[i].getInicio(); n == ksp[i].getFin(); n = n.getSiguiente()) {
                //cuts[i] = calcularCuts();
            }
        }
        minimo = Utilitarios.encontrarMinimo(cuts); //guarda la posicion k de la ruta ksp con cuts minimo
        //si hay un solo minimo se retorna el resultado
        k = -1;
        if (minimo.size() == 1) {
            k = minimo.get(0);
            resultado = new ResultadoRuteo(ksp[k], k, demanda);
            return resultado;
        } else {
            //calculo de misalignment
            for (i = 0; i < minimo.size(); i++) {
                //generar pares de links
                for (j = 0; j < paresLinks.size(); j = j + 2) {
                    //para cada link pair calcular misalignment
                    //des = des + calcularDesalineamiento (paresLinks.get(j))
                }
                desalineamiento.add(des);
            }
            desMin = Utilitarios.encontrarMinimo(desalineamiento); //guarda la posicion k de la ruta ksp con misalignment minimo
            if (desMin.size() == 1) {
                k = desMin.get(0);
                resultado = new ResultadoRuteo(ksp[k], k, demanda);
                return resultado;
            } else {
                k = Utilitarios.encontrarRutaMasCorta(ksp, desMin);
                resultado = new ResultadoRuteo(ksp[k], k, demanda);
                return resultado;
            }
        }
    }

    public static Resultado MPSC_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {
        int k = 0;
        int ban = 0;
        float cp;
        int OE[] = new int[(capacidad)];
        int bloque1 = 0, bloque2 = 0;
        Resultado r = new Resultado();
        while (ksp.length > k && ksp[k] != null) {
            for (int w = 0; w < (capacidad); w++) {
                OE[w] = 1;
            }
            Nodo t = ksp[k].getInicio();
            int Total = (G.acceder(t.getDato(), t.getSiguiente().getDato())).getFS().length;
            float mayorcp = 0;
            for (int j = 0; j < Total; j++) {
                for (t = ksp[k].getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                    if (G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[j].getEstado() == 0) {
                        OE[j] = 0;
                        break;
                    }
                }
            }
            int vector[] = new int[2];
            List<int[]> BloquesE = new LinkedList<int[]>();
            int cgb = 0;//contador global de bloques 
            for (int c = 0; c < OE.length; c++) {//de cuanto y cuantos bloques espectrales libres hay
                int i = 0, f = 0;
                if (OE[c] == 1) {
                    cgb++;
                    i = f = c;
                    for (; c < OE.length; c++) {
                        if (OE[c] == 0 || c == OE.length - 1) {
                            f = c;
                        }
                    }
                    if (f - i + 1 >= demanda.getNroFS()) {//bloque que puede utilizarse
                        vector[0] = i;
                        vector[1] = f;
                        BloquesE.add(vector);
                        ban = 1;
                    }
                }
            }
            int aux[] = new int[2];
            for (int l = 0; l < BloquesE.size(); l++) {
                int cgbcopia = cgb;
                float sum = 0, cfs = 0;//contador  freciencia slots 
                aux = BloquesE.get(l);
                if ((aux[1] - aux[0] + 1) - demanda.getNroFS() == 0) {//al ubicarse la demanda si puede existir un bloque
                    cgbcopia = cgb - 1;
                }
                for (int j = 0; j < OE.length - 1; j++) {
                    if (((j + 1) < aux[0] || j > (aux[0] + demanda.getNroFS() - 1) && OE[j] == 1)) {// && OE[j]==0 && OE[j+1]==0){
                        sum += OE[j] * OE[j + 1];
                        cfs++;
                    }
                }
                if (OE[OE.length - 1] == 1) {
                    cfs++;
                }
                cp = (sum / cgbcopia) * (cfs / OE.length);
                if (cp > mayorcp) {
                    mayorcp = cp;
                    bloque1 = aux[0];
                    bloque2 = aux[0] + demanda.getNroFS() - 1;
                }
            }
            if (mayorcp > r.getCp()) {//guardar bloque camino cp
                r.setCamino(k);
                r.setInicio(bloque1);
                r.setFin(bloque2);
                r.setCp(mayorcp);
            }
            k++;
        }
        if (ban == 1) {
            return r;
        } else {
            return null;
        }
    }

    public static Resultado MTLSC_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {
        int k = 0;
        int ban = 0;
        float cl;
        int OE[] = new int[(capacidad)];

        int bloque1 = 0, bloque2 = 0;
        Resultado r = new Resultado();
        //ListaEnlazada ksp[]=new ListaEnlazada[3];

        //comienzo
        while (ksp.length > k && ksp[k] != null) {
            for (int w = 0; w < (capacidad); w++) {
                OE[w] = 1;
            }
            Nodo t = ksp[k].getInicio();
            int Total = (G.acceder(t.getDato(), t.getSiguiente().getDato())).getFS().length;
            float mayorcl = 0;
            for (int j = 0; j < Total; j++) {
                for (t = ksp[k].getInicio(); t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                    if (G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS()[j].getEstado() == 0) {
                        OE[j] = 0;
                        break;
                    }
                }
            }
            int vector[] = new int[2];
            List<int[]> BloquesE = new LinkedList<int[]>();
            int cgb = 0;//contador global de bloques 
            for (int c = 0; c < OE.length; c++) {//de cuanto y cuantos bloques espectrales libres hay
                int i = 0, f = 0;
                if (OE[c] == 1) {
                    cgb++;
                    i = f = c;
                    for (; c < OE.length; c++) {
                        if (OE[c] == 0 || c == OE.length - 1) {
                            f = c;
                        }
                    }
                    if (f - i + 1 >= demanda.getNroFS()) {//bloque que puede utilizarse
                        vector[0] = i;
                        vector[1] = f;
                        BloquesE.add(vector);
                        ban = 1;
                    }
                }
            }
            int aux[] = new int[2];
            t = ksp[k].getInicio();
            FrecuencySlots enlace[] = new FrecuencySlots[G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS().length];
            for (int l = 0; l < BloquesE.size(); l++) {
                int cgbcopia = cgb;

                float sum = 0, cfs = 0;
                cl = 0;
                for (; t.getSiguiente().getSiguiente() != null; t = t.getSiguiente()) {
                    mayorcl = 0;
                    enlace = G.acceder(t.getDato(), t.getSiguiente().getDato()).getFS();
                    //contador  freciency slots 
                    aux = BloquesE.get(l);
                    if ((aux[1] - aux[0] + 1) - demanda.getNroFS() == 0) {//al ubicarse la demanda si puede existir un bloque
                        cgbcopia = cgb - 1;
                    }
                    for (int j = 0; j < enlace.length - 1; j++) {
                        if ((((j + 1) < aux[0] || j > (aux[0] + demanda.getNroFS() - 1))) && enlace[j].getEstado() == 1) {// && OE[j]==0 && OE[j+1]==0){
                            sum += enlace[j].getEstado() * enlace[j + 1].getEstado();
                            cfs++;
                        }
                    }
                    if (enlace[enlace.length - 1].getEstado() == 1) {
                        cfs++;
                    }
                    cl = (sum / cgbcopia) * (cfs / OE.length);
                    if (cl > mayorcl) {
                        mayorcl = cl;
                        bloque1 = aux[0];
                        bloque2 = aux[0] + demanda.getNroFS() - 1;
                    }
                }

            }

            if (mayorcl > r.getCp()) {//guardar bloque camino cp
                r.setCamino(k);
                r.setInicio(bloque1);
                r.setFin(bloque2);
                r.setCp(mayorcl);
            }
            k++;
        }
        if (ban == 1) {
            return r;
        } else {
            return null;
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
    public static Resultado KSP_FF_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaColocada == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
             */
            inicio = fin = cont = 0;
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if (cont == demanda.getNroFS()) {
                            fin = j;
                            demandaColocada = 1;
                            break;
                        }
                    }
                }
                if (demandaColocada == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(k - 1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }

    /*
    * Algoritmo Random Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
     */
    public static Resultado KSP_RF_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        int[] auxiliar = new int[capacidad];
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaColocada == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
            * La busqueda se realiza de forma aleatoria, probando desde cada indice del espectro,
            * si tenemos un bloque de FSs que satisfaga la demanda.
             */
            auxiliar = Utilitarios.listaDeNumeros(capacidad);
            inicio = fin = cont = 0;
            int n = 0;
            int i = 0;
            while (i < capacidad && demandaColocada == 0) {
                inicio = auxiliar[i];
                for (int j = inicio; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        cont = 0;
                        break;
                    }
                    //si se encontro un bloque valido, salimos de todos los bloques
                    if (cont == demanda.getNroFS()) {
                        fin = j;
                        demandaColocada = 1;
                        break;
                    }
                }
                i++;
            }
            k++;
        }
        if (demandaColocada == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(k - 1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }

    /*
    * Algoritmo Last Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
     */
    public static Resultado KSP_LF_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaColocada == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda desde la ultimas posiciones del espectro.
             */
            inicio = fin = cont = 0;
            for (int i = capacidad - 1; i > 0; i--) {
                if (OE[i] == 1) {
                    fin = i;
                    for (int j = fin; j > 0; j--) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if (cont == demanda.getNroFS()) {
                            inicio = j;
                            demandaColocada = 1;
                            break;
                        }
                    }
                }
                if (demandaColocada == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(k - 1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }

    /*
    * Algoritmo FAR-Exact Fit, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
    * Paramentros:
    *   GrafoMatriz G: Topologia representada en forma de un grafo(Matriz de adjacencia).
    *   Demanda demanda: Solicitud con un otigen o, destino d y n FSs solicitados.
    *   ListaEnlazada [] ksp: Lista enlazada con los k caminos mas cortos.
    *   int capacidad: capacidad de cada enlaca en la topologia.
    * Retorna:
    *   Resultado: Es una estructura que representa el indice i origen, y j destino utilizados del espectro.
                 Retorna null en caso de que no exista espectro disponible.
     */
    public static Resultado FAR_EF_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont, inicio2 = 0, fin2 = 0; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaExact = 0, demandaColocada = 0; // banderas para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaExact == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca exactamente la demanda.
             */
            inicio = fin = cont = 0;
            if (demandaColocada == 0) {
                inicio2 = fin2 = 0;
            }
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    //Si aun no se encontro ningun bloque de FS que cumple,
                    // guadamos tambien en el inicio auxiliar.
                    if (demandaColocada == 0) {
                        inicio2 = i;
                    }
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, qeu satisface exactamente la demanda, salimos de todos los bucles
                        if (cont == demanda.getNroFS() && i + cont + 1 < capacidad && OE[i + cont + 1] == 0) {
                            fin = j;
                            demandaExact = 1;
                            break;
                        }
                        //Guardamos el fin auxiliar del primer bloque de FS encontrado que cumple,
                        // para el caso que no se encuentre uno que exactamente cumpla la demanda
                        if (cont == demanda.getNroFS() && demandaColocada == 0) {
                            fin2 = j;
                            demandaColocada = 1;
                        }
                    }
                }
                if (demandaExact == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0 && demandaExact == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }

        /*Bloque contiguoo exacto encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        if (demandaExact == 1) {
            r.setCamino(k - 1);
            r.setFin(fin);
            r.setInicio(inicio);
            return r;
        }
        /*
        *No se encontro bloque contiguo exacto, por lo tanto, utilizamos First Fit
         */
        r.setCamino(k - 1);
        r.setFin(fin2);
        r.setInicio(inicio2);
        return r;

    }

    /*
    * Algoritmo KSP-Least Used, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
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
    public static Resultado KSP_LU_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int bloqueEncontrado = 0; // bandera para verificar si ya se encontro un bloqeu de FS libre.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        int[] util = new int[capacidad]; //utilizacion de los FSs.
        int[] bloque = new int[2]; // almacena la informacion de los indices inicio y fin del bloqeu con FSs libres
        int[] bloqueAux = new int[2]; // bloque auxiliar
        List bloques = new LinkedList(); // lista de bloques de FSs libres
        int pos_menor = 0; //posicion del bloque menor en la lista de bloques
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaColocada == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
 /*Calcular la utilizacion de los FSs en el espectro*/
            for (int i = 0; i < capacidad; i++) {
                int mayor = 0;
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                    }
                    if (mayor < G.acceder(n.getDato(), n.getSiguiente().getDato()).getUtilizacion()[i]) {
                        mayor = G.acceder(n.getDato(), n.getSiguiente().getDato()).getUtilizacion()[i];
                    }
                }
                util[i] = mayor;
                //System.out.println("mayor "+i+" = "+mayor);
            }
            /*
            *Obtener los bloques libres en el espectro
             */
            inicio = 0;
            fin = 0;
            for (int i = 0; i < capacidad; i++) {
                cont = 0;
                int j = i;
                for (; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        break;
                    }
                }
                // si se encontro un bloque de FSs libre que cumplen con la demanda
                // vemos hasta donde llega el bloque
                if (cont >= demanda.getNroFS()) {
                    demandaColocada = 1;
                    inicio = i;
                    if (j < capacidad) {
                        fin = j;
                    } else {
                        fin = capacidad - 1;
                    }
                    bloque[0] = inicio;
                    bloque[1] = fin;
                    bloques.add(bloque);
                }
                i += cont;
            }
            /*Ya se tiene todos los bloques libres del espectro*/
            if (demandaColocada == 1) {
                /*
                *Realizar los calculos de la utilziacion de cada bloque
                 */
                //calculamos la utilizacion para la variable pivot(menor)
                // para despues, apartir de este, obtener el menor.
                int menor = 0;
                bloqueAux = (int[]) bloques.get(0);
                for (int j = bloqueAux[0]; j <= bloqueAux[1]; j++) {
                    menor += util[j];
                }
                int acumular = 0;

                for (int i = 1; i < bloques.size(); i++) {
                    bloqueAux = (int[]) bloques.get(i);
                    acumular = 0;
                    for (int j = bloqueAux[0]; j <= bloqueAux[1]; j++) {
                        acumular += util[j];
                    }
                    if (menor > acumular) {
                        menor = acumular;
                        pos_menor = i;
                    }
                    //System.out.println("Pos menor = "+pos_menor);
                }
                /*Ya se sabe sabe cual es el bloque a asignar, esta marcado por el pos_menor
                 *Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
                 * y retornamos el resultado
                 */
                //System.out.println("Pos menor = "+pos_menor);
                bloque = (int[]) bloques.get(pos_menor);
                Resultado r = new Resultado();
                r.setCamino(k);
                r.setFin(bloque[1]);
                r.setInicio(bloque[0]);
                return r;
            }
            k++;
        }
        return null;
    }

    /*
    * Algoritmo KSP-Most Used, utilizando para el ruteo los k camino mas cortos desde el nodo origen al final.
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
    public static Resultado KSP_MU_Algorithm(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int bloqueEncontrado = 0; // bandera para verificar si ya se encontro un bloqeu de FS libre.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        int[] util = new int[capacidad]; //utilizacion de los FSs.
        int[] bloque = new int[2]; // almacena la informacion de los indices inicio y fin del bloqeu con FSs libres
        int[] bloqueAux = new int[2]; // bloque auxiliar
        List bloques = new LinkedList(); // lista de bloques de FSs libres
        int pos_mayor = 0; //posicion del bloque menor en la lista de bloques
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres
        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < 3 && ksp[k] != null && demandaColocada == 0) {
            /*Calcular la ocupacion del espectro para cada camino k*/
 /*Calcular la utilizacion de los FSs en el espectro*/
            for (int i = 0; i < capacidad; i++) {
                int mayor = 0;
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                    }
                    if (mayor < G.acceder(n.getDato(), n.getSiguiente().getDato()).getUtilizacion()[i]) {
                        mayor = G.acceder(n.getDato(), n.getSiguiente().getDato()).getUtilizacion()[i];
                    }
                }
                util[i] = mayor;
                //System.out.println("mayor "+i+" = "+mayor);
            }
            /*
            *Obtener los bloques libres en el espectro
             */
            inicio = 0;
            fin = 0;
            for (int i = 0; i < capacidad; i++) {
                cont = 0;
                int j = i;
                for (; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        break;
                    }
                }
                // si se encontro un bloque de FSs libre que cumplen con la demanda
                // vemos hasta donde llega el bloque
                if (cont >= demanda.getNroFS()) {
                    demandaColocada = 1;
                    inicio = i;
                    if (j < capacidad) {
                        fin = j;
                    } else {
                        fin = capacidad - 1;
                    }
                    bloque[0] = inicio;
                    bloque[1] = fin;
                    bloques.add(bloque);
                }
                i += cont;
            }
            /*Ya se tiene todos los bloques libres del espectro*/
            if (demandaColocada == 1) {
                /*
                *Realizar los calculos de la utilziacion de cada bloque
                 */
                //calculamos la utilizacion para la variable pivot(mayor)
                // para despues, apartir de este, obtener el menor.
                int mayor = 0;
                bloqueAux = (int[]) bloques.get(0);
                for (int j = bloqueAux[0]; j <= bloqueAux[1]; j++) {
                    mayor += util[j];
                }
                int acumular = 0;

                for (int i = 1; i < bloques.size(); i++) {
                    bloqueAux = (int[]) bloques.get(i);
                    acumular = 0;
                    for (int j = bloqueAux[0]; j <= bloqueAux[1]; j++) {
                        acumular += util[j];
                    }
                    if (mayor < acumular) {
                        mayor = acumular;
                        pos_mayor = i;
                    }
                    //System.out.println("Pos menor = "+pos_menor);
                }
                /*Ya se sabe sabe cual es el bloque a asignar, esta marcado por el pos_menor
                 *Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
                 * y retornamos el resultado
                 */
                //System.out.println("Pos menor = "+pos_menor);
                bloque = (int[]) bloques.get(pos_mayor);
                Resultado r = new Resultado();
                r.setCamino(k);
                r.setFin(bloque[1]);
                r.setInicio(bloque[0]);
                return r;
            }
            k++;
        }
        return null;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
 * Algoritmo de Ruteo FAR.
 * En la version 0.2, se da la posibilidad de Modulacion. Por lo tanto, se dividio los algoritmos de Ruteo y Asignacion de espectro en metodos
    diferntes.
    Parametros:
    GrafoMatriz G: Mstriz de adyacencia que almacena la red
    ListaEnlazada [] ksp: lista de los k-caminos mas cortos indicado por al demanda
    Demada demanda: objeto que contiene el origen, fin, tiempo de pemanencia, y ancho de banda requerido por el usuario
    int capacidad: cantidad de FS en un enlace
    boolean modulacion: true si se considera modulacion, false en caso contrario.
    Retorna:
    ResultadoRuteo : un objeto con el camino elegido entre los ksp.
    null: si no se encontro un camino con FS disponibles
     */
    public static ResultadoRuteo Ruteo_FAR(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad, boolean modulacion) {
        boolean demandaAsignada = false; // false si no se asigna la demanda, true en caso contrario
        int N = 0; // numero de FS que seran asignados dependiendo del tipo de modulacion (o si no se requiere se hace con considerando BPSK)
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        int k = 0; // se consideran 3 caminos alternativos
        Modulacion M = new Modulacion(); // esquema de modulacion
        int distancia; //distancia de un camino
        boolean existenFSdisponibles;// true si se encontraron FS disponibles en una ruta, false en caso contrario
        ResultadoRuteo resultado = null; // almacena el resutaldo obtenido en el algoritmo de ruteo (ruta y nro de ruta)
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres

        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        while (demandaAsignada == false && k < 3) {
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*
            *Se considera modulacion?
             */
            if (modulacion == true) { // si es asi vemos que tipo de modulacion se se utilizara y cuanto FS
                distancia = ksp[k].getFin().getDato(); // al final de la lista enlazada se guarda la distancia total del camino
                N = M.getNroFS(demanda.getAnchoBanda(), distancia); // obtenemos la cantidad de FS necesarios para la demanda segun la modulacion
                demanda.setNroFS(N);
            } else { // si no, se considera como predeterminado BPSK
                N = M.getNroFSbpsk(demanda.getAnchoBanda());
                demanda.setNroFS(N);
            }
            existenFSdisponibles = Utilitarios.buscarFSdisponibles(OE, capacidad, N); // buscamos si en esa ruta se encontro espectro disponible
            if (existenFSdisponibles) { // si fue asi, dejamos de buscar un camino valido
                demandaAsignada = true;
                break;
            }
            k++;// si no, avanzamos en el siguiente camino
        }
        if (demandaAsignada) { // si se encontro, creamos el resultado del ruteo
            resultado = new ResultadoRuteo(ksp[k], k, demanda);
        }
        return resultado;
    }

    /*
    * Asignacion de Spectro FF:
    Parametros:
    GrafoMatriz G: Mstriz de adyacencia que almacena la red
    ResultadoRuteo resultadoR: objeto que almacena el camino elegido por el algoritmo de ruteo y el nro de camino entre los ksp.
    Demada demanda: objeto que contiene el origen, fin, tiempo de pemanencia, y ancho de banda requerido por el usuario
    int capacidad: cantidad de FS en un enlace
    Retorna:
    Resultado: indices origen y fin y nro de camino ksp que se utilizara para reservar los FS en la red.
     */
    public static Resultado asignacionSpectro_FF(GrafoMatriz G, ResultadoRuteo resultadoR, int capacidad, Demanda demanda) {

        int inicio, fin, cont;
        int[] OE = new int[capacidad];
        boolean demandaColocada = false;
        ListaEnlazada ruta = resultadoR.getRuta();
        int k = resultadoR.getNroCaminoKsp();
        Resultado r = new Resultado();

        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Calcular la ocupacion del espectro para la ruta*/
        for (int i = 0; i < capacidad; i++) {
            for (Nodo n = ruta.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                    OE[i] = 0;
                    break;
                }
            }
        }
        /*la variablre inicio guardara la primera aparicion de FS disponibles*/
        inicio = fin = cont = 0;
        for (int i = 0; i < capacidad; i++) {
            if (OE[i] == 1) {
                inicio = i;
                for (int j = inicio; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        cont = 0;
                        break;
                    }
                    //si se encontro un bloque valido, salimos de todos los bloques
                    if (cont == demanda.getNroFS()) {
                        fin = j;
                        demandaColocada = true;
                        break;
                    }
                }
            }
            if (demandaColocada == true) {
                break;
            }
        }
        r.setCamino(k);
        r.setFin(fin);
        r.setInicio(inicio);
        // Utilitarios.asignarFS(ruta, r, G, null);
        return r;
    }

    /*
    * Asignacion de Spectro EF:
    Parametros:
    GrafoMatriz G: Mstriz de adyacencia que almacena la red
    ResultadoRuteo resultadoR: objeto que almacena el camino elegido por el algoritmo de ruteo y el nro de camino entre los ksp.
    Demada demanda: objeto que contiene el origen, fin, tiempo de pemanencia, y ancho de banda requerido por el usuario
    int capacidad: cantidad de FS en un enlace
    Retorna:
    Resultado: indices origen y fin y nro de camino ksp que se utilizara para reservar los FS en la red.
     */
    public static Resultado asignacionSpectro_EF(GrafoMatriz G, ResultadoRuteo resultadoR, int capacidad, Demanda demanda) {

        int inicio, fin, cont, inicio2, fin2;
        int[] OE = new int[capacidad];
        boolean demandaColocada = false;
        boolean demandaExact = false;
        ListaEnlazada ruta = resultadoR.getRuta();
        int k = resultadoR.getNroCaminoKsp();
        Resultado r = new Resultado();

        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Calcular la ocupacion del espectro para la ruta*/
        for (int i = 0; i < capacidad; i++) {
            for (Nodo n = ruta.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                    OE[i] = 0;
                    break;
                }
            }
        }
        /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
        * que satisfazca exactamente la demanda.
         */
        inicio = fin = cont = inicio2 = fin2 = 0;
        if (demandaColocada == false) {
            inicio2 = fin2 = 0;
        }
        for (int i = 0; i < capacidad; i++) {
            if (OE[i] == 1) {
                inicio = i;
                //Si aun no se encontro ningun bloque de FS que cumple,
                // guadamos tambien en el inicio auxiliar.
                if (demandaColocada == false) {
                    inicio2 = i;
                }
                for (int j = inicio; j < capacidad; j++) {
                    if (OE[j] == 1) {
                        cont++;
                    } else {
                        cont = 0;
                        break;
                    }
                    //si se encontro un bloque valido, qeu satisface exactamente la demanda, salimos de todos los bucles
                    if (cont == demanda.getNroFS() && i + cont + 1 < capacidad && OE[i + cont + 1] == 0) {
                        fin = j;
                        demandaExact = true;
                        break;
                    }
                    //Guardamos el fin auxiliar del primer bloque de FS encontrado que cumple,
                    // para el caso que no se encuentre uno que exactamente cumpla la demanda
                    if (cont == demanda.getNroFS() && demandaColocada == false) {
                        fin2 = j;
                        demandaColocada = true;
                    }
                }
            }
            if (demandaExact == true) {
                break;
            }
        }
        if (demandaExact) {
            r.setCamino(k);
            r.setFin(fin);
            r.setInicio(inicio);
        } else {
            r.setCamino(k);
            r.setFin(fin2);
            r.setInicio(inicio2);
        }

        // Utilitarios.asignarFS(ruta, r, G, null);
        return r;
    }

    /*
    * Asignacion de Spectro RF:
    Parametros:
    GrafoMatriz G: Mstriz de adyacencia que almacena la red
    ResultadoRuteo resultadoR: objeto que almacena el camino elegido por el algoritmo de ruteo y el nro de camino entre los ksp.
    Demada demanda: objeto que contiene el origen, fin, tiempo de pemanencia, y ancho de banda requerido por el usuario
    int capacidad: cantidad de FS en un enlace
    Retorna:
    Resultado: indices origen y fin y nro de camino ksp que se utilizara para reservar los FS en la red.
     */
    public static Resultado asignacionSpectro_RF(GrafoMatriz G, ResultadoRuteo resultadoR, int capacidad, Demanda demanda) {

        int inicio, fin, cont, inicio2, fin2;
        int[] OE = new int[capacidad];
        boolean demandaColocada = false;
        boolean demandaExact = false;
        int[] auxiliar = new int[capacidad];
        ListaEnlazada ruta = resultadoR.getRuta();
        int k = resultadoR.getNroCaminoKsp();
        Resultado r = new Resultado();

        for (int i = 0; i < capacidad; i++) {
            OE[i] = 1;
        }
        /*Calcular la ocupacion del espectro para la ruta*/
        for (int i = 0; i < capacidad; i++) {
            for (Nodo n = ruta.getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                    OE[i] = 0;
                    break;
                }
            }
        }
        /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
        * que satisfazca la demanda.
        * La busqueda se realiza de forma aleatoria, probando desde cada indice del espectro,
        * si tenemos un bloque de FSs que satisfaga la demanda.
         */
        auxiliar = Utilitarios.listaDeNumeros(capacidad);
        inicio = fin = cont = 0;
        int n = 0;
        int i = 0;
        while (i < capacidad && demandaColocada == false) {
            inicio = auxiliar[i];
            for (int j = inicio; j < capacidad; j++) {
                if (OE[j] == 1) {
                    cont++;
                } else {
                    cont = 0;
                    break;
                }
                //si se encontro un bloque valido, salimos de todos los bloques
                if (cont == demanda.getNroFS()) {
                    fin = j;
                    demandaColocada = true;
                    break;
                }
            }
            i++;
        }
        if (demandaColocada) {
            r.setCamino(k);
            r.setFin(fin);
            r.setInicio(inicio);
        }
        // Utilitarios.asignarFS(ruta, r, G, null);
        return r;
    }

    public static Resultado KSP_FF_Algorithm2_Greedy(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaColocada = 0; // bandera para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres

        Arrays.sort(ksp, new Comparator<ListaEnlazada>() {
            public int compare(ListaEnlazada one, ListaEnlazada other) {
                if (one.getTamanho() > other.getTamanho()) {
                    return 1;
                }
                if (one.getTamanho() < other.getTamanho()) {
                    return -1;
                }
                return 0;
            }
        });

        /*ArrayList<Integer> vk = new ArrayList<Integer>();
        for (int i = 0; i < ksp.length; i++) {
            //System.out.println("se agrego el elmento:  "+i);
            vk.add(i, i);
        }
        Collections.shuffle(vk);
        ListaEnlazada [] aux=new ListaEnlazada[ksp.length];
        for (int i = 0; i < vk.size(); i++) {
            aux[vk.get(i)]=ksp[i];
        }
        ksp=aux;*/
 /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;

        while (k < ksp.length && ksp[k] != null && demandaColocada == 0) {
            for (int i = 0; i < capacidad; i++) {
                OE[i] = 1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                //System.out.println("falla en el indice::"+ccc);
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    //System.out.println("v1 "+n.getDato()+" v2 "+n.getSiguiente().getDato()+" cant vertices "+G.getCantidadDeVertices()+" i "+i+" FSs "+G.acceder(n.getDato(),n.getSiguiente().getDato()).getFS().length);
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca la demanda.
             */
            inicio = fin = cont = 0;
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, salimos de todos los bloques
                        if (cont == demanda.getNroFS()) {
                            fin = j;
                            demandaColocada = 1;
                            break;
                        }
                    }
                }
                if (demandaColocada == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }
        /*Bloque contiguoo encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        r.setCamino(k - 1);
        r.setFin(fin);
        r.setInicio(inicio);
        return r;
    }

    public static Resultado FAR_EF_Algorithm3(GrafoMatriz G, Demanda demanda, ListaEnlazada[] ksp, int capacidad) {//greedy

        /*Definicion de variables las variables*/
        int inicio = 0, fin = 0, cont, inicio2 = 0, fin2 = 0; // posicion inicial y final dentro del espectro asi como el contador de FSs contiguos disponibles
        int demandaExact = 0, demandaColocada = 0; // banderas para controlar si ya se encontro espectro disponible para la demanda.
        int[] OE = new int[capacidad]; //Ocupacion de Espectro.
        //Inicializadomos el espectro, inicialmente todos los FSs estan libres

        //System.out.println("tamano lista ksp:  "+ksp.length);
        ArrayList<Integer> vk = new ArrayList<Integer>();
        for (int i = 0; i < ksp.length; i++) {
            //System.out.println("se agrego el elmento:  "+i);
            vk.add(i, i);
        }
        Collections.shuffle(vk);
        ListaEnlazada[] aux = new ListaEnlazada[ksp.length];
        for (int i = 0; i < vk.size(); i++) {
            aux[vk.get(i)] = ksp[i];
        }
        ksp = aux;

        /*Probamos para cada camino, si exite espectro para ubicar la damanda*/
        int k = 0;
        while (k < ksp.length && ksp[k] != null && demandaExact == 0) {
            for (int i = 0; i < capacidad; i++) {
                OE[i] = 1;
            }
            /*Calcular la ocupacion del espectro para cada camino k*/
            for (int i = 0; i < capacidad; i++) {
                for (Nodo n = ksp[k].getInicio(); n.getSiguiente().getSiguiente() != null; n = n.getSiguiente()) {
                    if (G.acceder(n.getDato(), n.getSiguiente().getDato()).getFS()[i].getEstado() == 0) {
                        OE[i] = 0;
                        break;
                    }
                }
            }
            /*Teniendo la ocupacion del espectro del camino k, buscamos un bloque continuo de FS
            * que satisfazca exactamente la demanda.
             */
            inicio = fin = cont = 0;
            if (demandaColocada == 0) {
                inicio2 = fin2 = 0;
            }
            for (int i = 0; i < capacidad; i++) {
                if (OE[i] == 1) {
                    inicio = i;
                    //Si aun no se encontro ningun bloque de FS que cumple,
                    // guadamos tambien en el inicio auxiliar.
                    if (demandaColocada == 0) {
                        inicio2 = i;
                    }
                    for (int j = inicio; j < capacidad; j++) {
                        if (OE[j] == 1) {
                            cont++;
                        } else {
                            cont = 0;
                            break;
                        }
                        //si se encontro un bloque valido, qeu satisface exactamente la demanda, salimos de todos los bucles
                        if (cont == demanda.getNroFS() && i + cont + 1 < capacidad && OE[i + cont + 1] == 0) {
                            fin = j;
                            demandaExact = 1;
                            break;
                        }
                        //Guardamos el fin auxiliar del primer bloque de FS encontrado que cumple,
                        // para el caso que no se encuentre uno que exactamente cumpla la demanda
                        if (cont == demanda.getNroFS() && demandaColocada == 0) {
                            fin2 = j;
                            demandaColocada = 1;
                        }
                    }
                }
                if (demandaExact == 1) {
                    break;
                }
            }
            k++;
        }

        if (demandaColocada == 0 && demandaExact == 0) {
            return null; // Si no se encontro, en ningun camino un bloque contiguo de FSs, retorna null.
        }

        /*Bloque contiguoo exacto encontrado, asignamos los indices del espectro a utilizar 
        * y retornamos el resultado
         */
        Resultado r = new Resultado();
        if (demandaExact == 1) {
            r.setCamino(k - 1);
            r.setFin(fin);
            r.setInicio(inicio);
            return r;
        }
        /*
        *No se encontro bloque contiguo exacto, por lo tanto, utilizamos First Fit
         */
        r.setCamino(k - 1);
        r.setFin(fin2);
        r.setInicio(inicio2);
        return r;

    }

}
