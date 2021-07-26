/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EON;
import java.util.List;
import java.util.LinkedList;
/**
 *
 * @author Fer
 */
public class ArbolConNodosConocidos {
    
    int [][] nodos;
    int raiz;
    
    public ArbolConNodosConocidos(){
        this.nodos=null;
        this.raiz=-1;
    }
    public ArbolConNodosConocidos(int raiz){
        this.nodos[raiz][raiz]=raiz;
        this.raiz=raiz;
    }
    /// setters - getters
    public void setRaiz(int r){
        this.nodos[r][r]=r;
        this.raiz=r;
    }
    public int getRaiz(){
        return this.raiz;
    }
    public int [][] getArbol(){
        return this.nodos;
    }
    ///inserttar un elemento al arbol, se debe conocer el padre
    public void insertar(int a,int padre){
        
        if(raiz==-1){
            setRaiz(a);
        }else{
            //inserto en el padre
            this.nodos[padre][a]=1;
            // indico en el hijo, quien es su pare ;)
            this.nodos[a][a]=padre;
        }
    }
    
    
}
