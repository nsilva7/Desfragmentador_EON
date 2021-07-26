package Interfaces;
import java.awt.Color;
import javax.swing.*;

public class hilobar implements Runnable{
    JProgressBar bar1;
    int ear;
    int max;
    double barstate;
    boolean fin=false;
    int cont=0;
    JLabel er;
    
    public hilobar(JProgressBar bar1, int ear,int max,JLabel er){
        this.bar1=bar1;
        this.ear=ear;
        this.max=max;
        this.er=er;
        
    }
    
    public void setvar(int val){
        this.bar1.setValue(val);
        bar1.update(bar1.getGraphics());
    }
    
    public void run(){
        er.setText("");
        er.setText("Simulando...");
        er.update(er.getGraphics());
        
                er.update(er.getGraphics());
        while (!fin) {
            try {
                
                if (cont==-1) {
                    bar1.setValue(0);
                    bar1.update(bar1.getGraphics());
                    Thread.sleep(100);
                    
                    ++cont;
                    
                }
                if (cont==0) {
                    bar1.setValue(25);
                    bar1.update(bar1.getGraphics());
                    Thread.sleep(100);
                    
                    ++cont;
                    
                }
                if (cont==1) {
                    bar1.setValue(50);
                    bar1.update(bar1.getGraphics());
                    Thread.sleep(100);
                    
                    setvar(++cont);
                    ++cont;
                    
                }
                if (cont==2) {
                    
                    bar1.setValue(75);
                    bar1.update(bar1.getGraphics());
                    Thread.sleep(100);
                    
                    ++cont;
                    
                }
                if (cont==3) {
                    bar1.setValue(600);
                    bar1.update(bar1.getGraphics());
                    Thread.sleep(100);
                    
                    setvar(++cont);
                    cont=-1;
                    
                }
                //setvar(++cont);
                
                
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        bar1.setValue(100);
        bar1.setBackground(Color.red);
        
                er.update(er.getGraphics());
        
        
        
        
    }
}

