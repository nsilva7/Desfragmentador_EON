/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON;

import EON.ListaEnlazada;
import EON.Demanda;
/**
 *
 * @author sFernandez
 */
public class ListaEnlazadaAsignadas  {
    
    ListaEnlazada listaAsignada;
    Demanda demanda;
    int estado;//Si esta vivo(1) o muerto(0)
    int aux;//entero auxiliar para el paso de datos
    
    public ListaEnlazadaAsignadas(){}
    
    public ListaEnlazadaAsignadas(ListaEnlazada listaAsignada, Demanda demanda){
        this.listaAsignada = listaAsignada;
        this.demanda = new Demanda(demanda.getOrigen(), demanda.getDestino(), demanda.getNroFS(), demanda.getTiempo());
        this.estado=1;
        
    }
    
    public void setDemanda(Demanda d){
        this.demanda =d;
    }
    
    public Demanda getDemanda(){
        return this.demanda;
    }
    
    public void setListaAsignada(ListaEnlazada listaAsignada){
        this.listaAsignada = listaAsignada;
    }
    public ListaEnlazada getListaAsignada(){
        return this.listaAsignada;
    }
    
    public void setEstado(int e){
        this.estado = e;
    }
    
    public int  getEstado(){
        return this.estado;
    }
    
    public void setEnteroAux(int aux){
        this.aux = aux;
    }
    
    public int getEnteroAux(){
        return this.aux;
    }
    
}
