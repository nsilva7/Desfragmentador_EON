
package EON;

/**
 *
 * @author Team Delvalle
 * La clase que almacena las topologias utilizadas para las simulaciones.
 * 
 */
public class Topologias {
    private GrafoMatriz [] redes;
    private int cantRedes;
    private int canVertices1;
    private int canVertices2;
    private int canVertices3;
    private int canVertices4;
 
   
    
    public Topologias(){
        this.cantRedes=5;
        this.redes=new GrafoMatriz[this.cantRedes];
        this.canVertices1=7; //Colocar camtidad de vetices para cada red.
        this.canVertices2=14;
        this.canVertices3=21;
        this.canVertices4=24;
        
        //Cargar red por red con sus datos
        
        this.redes[0]=new GrafoMatriz(this.canVertices1);
        this.redes[0].insertarDatos(this.red1);
        this.redes[1]=new GrafoMatriz(this.canVertices2);
        this.redes[1].insertarDatos(red4);
        this.redes[2]=new GrafoMatriz(this.canVertices3);
        this.redes[2].insertarDatos(red5);
        this.redes[3]=new GrafoMatriz(this.canVertices4);
        this.redes[3].insertarDatos(red6);
    }
    
    public GrafoMatriz getRed(int i){
        return this.redes[i];
    }
    public int getCantidadDeRedes(){
        return this.cantRedes;
    }
    public double [][][] getTopologia(int i){
        switch(i){
            case 0:
                return this.red1;
            case 1:
                return this.red4;
            case 2:
                return this.red5;
        }
        return this.red6;
    }
    private double [][][] red1={ {{0,0,0,0},{1,10,100,2},{2,2,100,2},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                   {{0,10,100,2},{0,0,0,0},{0,0,0,0},{3,7,100,2},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                   {{0,2,100,2},{0,0,0,0},{0,0,0,0},{3,3,100,2},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
                   {{0,0,0,0},{1,7,100,2},{2,3,100,2},{0,0,0,0},{4,25,100,2},{5,3,100,2},{0,0,0,0}},
                   {{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,25,100,2},{0,0,0,0},{0,0,0,0},{6,11,100,2}},
                   {{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,3,100,2},{0,0,0,0},{0,0,0,0},{6,2,100,2}},
                   {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{4,11,100,2},{5,2,100,2},{0,0,0,0}} };

    private double [][][] red4={ 
 /* 0 */                  {{0,0,0,0},{1,1100,4400,12.5},{2,600,4400,12.5},{3,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 1 */                  {{0,1100,4400,12.5},{0,0,0,0},{2,1600,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,2800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 2 */                  {{0,600,4400,12.5},{1,1600,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,1600,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 3 */                  {{0,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{4,600,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,2400,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 4 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,600,4400,12.5},{0,0,0,0},{5,1100,4400,12.5},{6,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 5 */                  {{0,0,0,0},{0,0,0,0},{2,2000,4400,12.5},{0,0,0,0},{4,1100,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{10,1200,4400,12.5},{0,0,0,0},{12,2000,4400,12.5},{0,0,0,0}},
 /* 6 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{4,800,4400,12.5},{0,0,0,0},{0,0,0,0},{7,700,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 7 */                  {{0,0,0,0},{1,2800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{6,700,4400,12.5},{0,0,0,0},{0,0,0,0},{9,700,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 8 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,2400,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{11,800,4400,12.5},{0,0,0,0},{13,800,4400,12.5}},
 /* 9 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,700,4400,12.5},{0,0,0,0},{0,0,0,0},{10,900,4400,12.5},{11,500,4400,12.5},{0,0,0,0},{13,500,4400,12.5}},
/* 10 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,1200,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{9,900,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 11 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,800,4400,12.5},{9,500,4400,12.5},{0,0,0,0},{0,0,0,0},{12,300,4400,12.5},{0,0,0,0}},
/* 12 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,2000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{11,300,4400,12.5},{0,0,0,0},{13,300,4400,12.5}},
/* 13 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,800,4400,12.5},{9,500,4400,12.5},{0,0,0,0},{0,0,0,0},{12,300,4400,12.5},{0,0,0,0}}};
    private double [][][] red5={ 
 /* 0 */                  {{0,0,0,0},{1,12,5000,12.5},{2,15,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 1 */                  {{0,12,5000,12.5},{0,0,0,0},{2,25,5000,12.5},{3,6,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,13,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 2 */                  {{0,15,5000,12.5},{1,25,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,11,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 3 */                  {{0,0,0,0},{1,6,5000,12.5},{0,0,0,0},{0,0,0,0},{4,7,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 4 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{3,7,5000,12.5},{0,0,0,0},{5,12,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 5 */                  {{0,0,0,0},{0,0,0,0},{2,11,5000,12.5},{0,0,0,0},{4,13,5000,12.5},{0,0,0,0},{6,15,5000,15.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{10,14,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 6 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,15,5000,12.5},{0,0,0,0},{7,10,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 7 */                  {{0,0,0,0},{1,13,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{6,10,5000,12.5},{0,0,0,0},{8,10,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 8 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,10,5000,12.5},{0,0,0,0},{9,8,5000,12.5},{0,0,0,0},{11,4,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
 /* 9 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,8,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{14,15,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 10 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,14,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,9,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 11 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,4,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{12,6,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 12 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{11,6,5000,12.5},{0,0,0,0},{13,5,5000,12.5},{0,0,0,0},{0,0,0,0},{16,6,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 13 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{12,5,5000,12.5},{0,0,0,0},{14,9,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 14 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{9,15,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{13,7,5000,12.5},{0,0,0,0},{15,9,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 15 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{10,9,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,7,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 16 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{12,6,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{18,8,5000,12.5},{0,0,0,0},{0,0,0,0}},
/* 17 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,7,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{19,7,5000,12.5},{0,0,0,0}},
/* 18 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{16,8,5000,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{20,14,5000,12.5}},
/* 19 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0,},{17,7,5000,12.5},{0,0,0,0},{0,0,0,0},{20,17,5000,12.5}},
/* 20 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0,},{0,0,0,0},{18,15,5000,12.5},{19,17,5000,12.5},{0,0,0,0}}};
    
    private double [][][] red6={ 
/* 0 */                  {{0,0,0,0},{1,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 1 */                  {{0,800,4400,12.5},{0,0,0,0},{2,1100,4400,12.5},{0,0,0,0},{0,0,0,0},{5,950,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 2 */                  {{0,0,0,0},{1,1100,4400,12.5},{0,0,0,0},{3,250,4400,12.5},{4,1000,4400,12.5},{0,0,0,0},{6,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 3 */                  {{0,0,0,0},{0,0,0,0},{2,250,4400,12.5},{0,0,0,0},{4,800,4400,12.5},{0,0,0,0},{6,850,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 4 */                  {{0,0,0,0},{0,0,0,0},{2,1000,4400,12.5},{3,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,1200,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 5 */                  {{0,1000,4400,12.5},{1,950,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{6,1000,4400,12.5},{0,0,0,0},{8,1200,4400,12.5},{0,0,0,0},{10,1900,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 6 */                  {{0,0,0,0},{0,0,0,0},{2,1000,4400,12.5},{3,850,4400,12.5},{0,0,0,0},{5,1000,4400,12.5},{0,0,0,0},{7,1150,4400,12.5},{8,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 7 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{4,1200,4400,12.5},{0,0,0,0},{6,1150,4400,12.5},{0,0,0,0},{0,0,0,0},{9,700,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 8 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,1200,4400,12.5},{6,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{9,1000,4400,12.5},{10,1400,4400,12.5},{11,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 9 */                  {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{7,700,4400,12.5},{8,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{12,950,4400,12.5},{13,850,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 10 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{5,1900,4400,12.5},{0,0,0,0},{0,0,0,0},{8,1400,4400,12.5},{0,0,0,0},{0,0,0,0},{11,900,4400,12.5},{0,0,0,0},{0,0,0,0},{14,1300,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{18,2800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 11 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{8,1000,4400,12.5},{0,0,0,0},{10,900,4400,12.5},{0,0,0,0},{12,900,4400,12.5},{0,0,0,0},{0,0,0,0},{15,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 12 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{9,950,4400,12.5},{0,0,0,0},{11,900,4400,12.5},{0,0,0,0},{13,650,4400,12.5},{0,0,0,0},{0,0,0,0},{16,1100,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 13 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{9,850,4400,12.5},{0,0,0,0},{0,0,0,0},{12,650,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{17,1200,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 14 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{10,1300,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{19,1300,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 15 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{11,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{14,800,4400,12.5},{0,0,0,0},{16,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{20,1000,4400,12.5},{21,800,4400,12.5},{0,0,0,0},{0,0,0,0}},
/* 16 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{12,1100,4400,12.5},{0,0,0,0},{0,0,0,0},{15,1000,4400,12.5},{0,0,0,0},{17,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{21,850,4400,12.5},{22,1000,4400,12.5},{0,0,0,0}},
/* 17 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{13,1200,4400,12.5},{0,0,0,0},{0,0,0,0},{16,800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{23,900,4400,12.5}},
/* 18 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{10,2800,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{19,1200,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 19 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{14,1300,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{18,1200,4400,12.5},{0,0,0,0},{20,700,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0}},
/* 20 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{19,700,4400,12.5},{0,0,0,0},{21,300,4400,12.5},{0,0,0,0},{0,0,0,0}},
/* 21 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{15,800,4400,12.5},{16,850,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{20,300,4400,12.5},{0,0,0,0},{22,600,4400,12.5},{0,0,0,0}},
/* 22 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{16,1000,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{21,600,4400,12.5},{0,0,0,0},{23,900,4400,12.5}},
/* 23 */                 {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{17,1,4400,12.5},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{22,900,4400,12.5},{0,0,0,0}}};
   
}
