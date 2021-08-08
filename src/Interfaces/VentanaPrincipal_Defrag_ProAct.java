package Interfaces;

import EON.Utilitarios.*;
import EON.*;
import EON.Algoritmos.*;
import EON.Metricas.Metricas;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import org.jfree.data.xy.*;
import org.jfree.chart.annotations.XYTextAnnotation;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

/**
 *
 * @author sGaleano - dBaez Frame que se encargad de la interfaz con el usurio y
 * realizar la simulacion de una Red Optica Elastica. Permite realizar una
 * simulacion teniendo: - Una topologia - Un conjunto de algoritmos. - Un tipo
 * de demanda que sera generada y guardada en un archivo.
 */
public class VentanaPrincipal_Defrag_ProAct extends javax.swing.JFrame {

    private Topologias Redes; // topologias disponibles

    private int tiempoTotal; // Iiempo que dura una simualcion
    private String redSeleccionada;
    private double[][][] topologia;
    private double anchoFS; // ancho de un FS en los enlaces
    private int capacidadPorEnlace; // cantidad de FSs por enlace en la topologia elegida

    private String metodo;
    
    private int Erlang, rutas;
    private boolean esBloqueo;
    private boolean haybloqueos, encontroSolucion = false;
    private boolean encontroSolucionAG = false;
    private int Lambda, contBloqueos;
    private int HoldingTime; // Erlang / Lambda
    private int FsMinimo; // Cantidad mínima de FS por enlace
    private int FsMaximo; // Cantidad máxima de FS por enlace
    private double entropia, msi, bfr, pathConsec, entropiaUso, porcUso, shf,probBloqueo, porcentajeAnterior = 1.0;
    private ArrayList<Integer> rutasEstablecidas; //guarda el tiempo de vida de las rutas ya establecidas por el algoritmo RSA
    private ArrayList<ListaEnlazada> arrayRutas;//Guarda la lista enlazada que representa a la ruta establecida por el algoritmo RSA
    private ArrayList<Resultado> resultadoRuteo;//Guarda los resutados del algoritmo para saber en que FS fue ubicada la demanda
    private ArrayList<ListaEnlazada[]> listaKSP;
    int hora, minutos, segundos, dia, mes, anho;
    
    private ArrayList<Demanda> demandasBloqueadas;
    private List algoritmosCompletosParaGraficar;
    private int cantidadDeAlgoritmosRuteoSeleccionados;
    private int cantidadDeAlgoritmosTotalSeleccionados;

    public VentanaPrincipal_Defrag_ProAct() {
        initComponents();
        this.Redes = new Topologias(); // asignamos todas las topologias disponibles}

        /*No mostramos inicialmente los paneles que muestran los Resultados
         */
        //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        //this.algoritmosCompletosParaEjecutar = new LinkedList();
        this.algoritmosCompletosParaGraficar = new LinkedList();
        this.setTitle("EON Simulator - Defragmentación ProActiva");

        setearRed(); // setea la red que aparece por defecto

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        this.etiquetaTextoBloqueosTotales.setVisible(false);
        this.etiquetaDemandasTotales.setVisible(false);
        this.etiquetaTextoDemandasTotales.setVisible(false);
        this.etiquetaBloqueosTotales.setVisible(false);
        this.etiquetaCantDesfrag.setVisible(false);
        this.etiquetaCantDesfrag.setVisible(false);
        this.etiquetaCantRutasReruteadas.setVisible(false);
        this.etiquetaCantDesfrag.setVisible(false);
        this.etiquetaTextoCantRutasReruteadas.setVisible(false);
        this.etiquetaTextoCantDesfrag.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        listaAlgoritmosRuteo = new javax.swing.JList<String>();
        botonEjecutarSimulacion = new javax.swing.JButton();
        etiquetaTopologia = new javax.swing.JLabel();
        etiquetaCapacidadActual = new javax.swing.JLabel();
        etiquetaTiempoActual = new javax.swing.JLabel();
        spinnerTiempoSimulacion = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        etiquetaImagenTopologia = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        etiquetaDemandasTotales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        spinnerErlang = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        textFieldCapacidadEnlace = new javax.swing.JTextField();
        listaRedes = new javax.swing.JComboBox<String>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        etiquetaAnchoFSActual1 = new javax.swing.JLabel();
        textFieldLambda = new javax.swing.JTextField();
        etiquetaAnchoFSActual2 = new javax.swing.JLabel();
        textFieldFSminimo = new javax.swing.JTextField();
        etiquetaAnchoFSActual3 = new javax.swing.JLabel();
        etiquetaAnchoFSActual4 = new javax.swing.JLabel();
        textFieldFSmaximo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldAnchoFS = new javax.swing.JTextField();
        etiquetaAnchoFSActual = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        etiquetaTextoBloqueosTotales = new javax.swing.JLabel();
        etiquetaBloqueosTotales = new javax.swing.JLabel();
        etiquetaTextoDemandasTotales = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelResultados = new javax.swing.JScrollPane();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableEstadoEnlaces = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableResultadosDefrag = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        etiquetaTextoMax = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultadosBloqueosMinMax = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableResultadosBloqueos = new javax.swing.JTable();
        etiquetaTextoMin = new javax.swing.JLabel();
        etiquetaRSA1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableResultados = new javax.swing.JTable();
        etiquetaRSA3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableResultadosMinMax = new javax.swing.JTable();
        etiquetaTextoMin1 = new javax.swing.JLabel();
        etiquetaTextoMax1 = new javax.swing.JLabel();
        etiquetaRSA2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        etiquetaAnchoFSActual19 = new javax.swing.JLabel();
        textFieldPeriodoDesfrag = new javax.swing.JTextField();
        etiquetaAnchoFSActual22 = new javax.swing.JLabel();
        etiquetaTextoCantRutasReruteadas = new javax.swing.JLabel();
        etiquetaCantRutasReruteadas = new javax.swing.JLabel();
        etiquetaCantDesfrag = new javax.swing.JLabel();
        etiquetaTextoCantDesfrag = new javax.swing.JLabel();
        ComboMetodoDesfrag = new javax.swing.JComboBox<String>();
        ComboMetodo = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        etiquetaAnchoFSActual26 = new javax.swing.JLabel();
        textFieldRutasARerutear = new javax.swing.JTextField();
        etiquetaAnchoFSActual12 = new javax.swing.JLabel();
        etiquetaTopologia2 = new javax.swing.JLabel();
        ComboObjetivoReruteo = new javax.swing.JComboBox<String>();
        jPanel4 = new javax.swing.JPanel();
        etiquetaTopologia1 = new javax.swing.JLabel();
        etiquetaError = new javax.swing.JLabel();
        ComboObjetivoACO = new javax.swing.JComboBox<String>();
        etiquetaAnchoFSActual8 = new javax.swing.JLabel();
        textFieldCantHormigas = new javax.swing.JTextField();
        etiquetaAnchoFSActual20 = new javax.swing.JLabel();
        textFieldMejoraACO = new javax.swing.JTextField();
        etiquetaAnchoFSActual6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ComboObjAlgoritmoGenetico = new javax.swing.JComboBox<String>();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCantIndividuosAG = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jTextFieldCantGeneraciones = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldMejoraAG = new javax.swing.JTextField();
        etiquetaAnchoFSActual7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listaAlgoritmosRuteo.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "FA", "FA-CA", "MTLSC" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listaAlgoritmosRuteo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaAlgoritmosRuteo.setToolTipText("");
        listaAlgoritmosRuteo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listaAlgoritmosRuteo.setSelectedIndex(0);
        listaAlgoritmosRuteo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaAlgoritmosRuteoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaAlgoritmosRuteo);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 90, 60));

        botonEjecutarSimulacion.setText("Play");
        botonEjecutarSimulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEjecutarSimulacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonEjecutarSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 690, 60, 20));

        etiquetaTopologia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTopologia.setText("Topologia");
        getContentPane().add(etiquetaTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 60, 20));

        etiquetaCapacidadActual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaCapacidadActual.setText("Capacidad");
        getContentPane().add(etiquetaCapacidadActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 60, 20));

        etiquetaTiempoActual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTiempoActual.setText("Tiempo de Simulacion");
        getContentPane().add(etiquetaTiempoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 130, 20));

        spinnerTiempoSimulacion.setModel(new javax.swing.SpinnerNumberModel(50, 50, 100000, 25));
        spinnerTiempoSimulacion.setToolTipText("");
        spinnerTiempoSimulacion.setRequestFocusEnabled(false);
        spinnerTiempoSimulacion.setValue(1000);
        getContentPane().add(spinnerTiempoSimulacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 60, 20));

        jLabel2.setText("FSs por Enlace");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, 20));

        etiquetaImagenTopologia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaImagenTopologia.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        etiquetaImagenTopologia.setFocusable(false);
        etiquetaImagenTopologia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        etiquetaImagenTopologia.setOpaque(true);
        etiquetaImagenTopologia.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(etiquetaImagenTopologia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 380, 150));

        jLabel5.setText("unid.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, 20));

        etiquetaDemandasTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 50, 20));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Tráfico Máximo");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 90, 20));

        spinnerErlang.setModel(new javax.swing.SpinnerNumberModel(400, 1, 1500, 50));
        getContentPane().add(spinnerErlang, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 50, -1));

        jLabel6.setText("Erlang");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 50, 20));

        textFieldCapacidadEnlace.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCapacidadEnlace.setText("1200");
        getContentPane().add(textFieldCapacidadEnlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 50, -1));

        listaRedes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NSFNet", "USNet", "ARPA-2" }));
        listaRedes.setSelectedIndex(1);
        listaRedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaRedesActionPerformed(evt);
            }
        });
        getContentPane().add(listaRedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 80, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Desfragmentación ProActiva");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Resultados");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        etiquetaAnchoFSActual1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual1.setText("Lambda");
        getContentPane().add(etiquetaAnchoFSActual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 50, 20));

        textFieldLambda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldLambda.setText("5");
        textFieldLambda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldLambdaActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldLambda, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 30, 20));

        etiquetaAnchoFSActual2.setText("mín");
        getContentPane().add(etiquetaAnchoFSActual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 30, 20));

        textFieldFSminimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSminimo.setText("1");
        textFieldFSminimo.setToolTipText("");
        textFieldFSminimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldFSminimoActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldFSminimo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 30, 20));

        etiquetaAnchoFSActual3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual3.setText("FS Rango");
        getContentPane().add(etiquetaAnchoFSActual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 70, 20));

        etiquetaAnchoFSActual4.setText("máx");
        getContentPane().add(etiquetaAnchoFSActual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 30, 20));

        textFieldFSmaximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldFSmaximo.setText("8");
        textFieldFSmaximo.setToolTipText("");
        textFieldFSmaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldFSmaximoActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldFSmaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 30, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Red");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel3.setText("GHz");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 30, 20));

        textFieldAnchoFS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldAnchoFS.setText("2");
        textFieldAnchoFS.setEnabled(false);
        textFieldAnchoFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldAnchoFSActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldAnchoFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 30, 20));

        etiquetaAnchoFSActual.setText("Ancho FS");
        getContentPane().add(etiquetaAnchoFSActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 60, 20));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 0, 690));

        etiquetaTextoBloqueosTotales.setText("Total Bloqueos:");
        getContentPane().add(etiquetaTextoBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 90, 20));

        etiquetaBloqueosTotales.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaBloqueosTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, 50, 20));

        etiquetaTextoDemandasTotales.setText("Total Demandas:");
        getContentPane().add(etiquetaTextoDemandasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 100, 20));

        panelResultados.setViewportView(filler1);

        jTabbedPane1.addTab("Gráficos", panelResultados);

        jTableEstadoEnlaces.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableEstadoEnlaces.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(jTableEstadoEnlaces);

        jTabbedPane1.addTab("Estado Final de los Enlaces", jScrollPane6);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableResultadosDefrag.setAutoCreateRowSorter(true);
        jTableResultadosDefrag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Rutas Activas", "Mejora %", "Mejor Hormiga/Generacion AG", "Rutas Modificadas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTableResultadosDefrag);

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 360, 610));

        jTabbedPane1.addTab("Desfragmentaciones", jPanel2);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiquetaTextoMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setText("max");
        etiquetaTextoMax.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        etiquetaTextoMax.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 600, 30, 20));

        jTableResultadosBloqueosMinMax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso", "% Uso", "Prob Bloq"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultadosBloqueosMinMax.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableResultadosBloqueosMinMax);
        jTableResultadosBloqueosMinMax.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 560, 340, 63));

        jTableResultadosBloqueos.setAutoCreateRowSorter(true);
        jTableResultadosBloqueos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Demandas", "Bloqueos", "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso", "% Uso", "SHF", "Prob Bloq"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultadosBloqueos.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(jTableResultadosBloqueos);
        jTableResultadosBloqueos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 480, 520));

        etiquetaTextoMin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMin.setText("min");
        etiquetaTextoMin.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTextoMin.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 590, 30, 20));

        etiquetaRSA1.setBackground(new java.awt.Color(255, 102, 102));
        etiquetaRSA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetaRSA1.setText("Bloqueos");
        jPanel1.add(etiquetaRSA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 80, -1));

        jTableResultados.setAutoCreateRowSorter(true);
        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tiempo", "Demandas", "Bloqueos", "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso", "% Uso", "SHF", "SumSlots", "SumBlockedSlots", "Prob Bloq"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultados.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(jTableResultados);
        jTableResultados.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 520));

        etiquetaRSA3.setBackground(new java.awt.Color(255, 102, 102));
        etiquetaRSA3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetaRSA3.setText("Resultados");
        jPanel1.add(etiquetaRSA3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        jTableResultadosMinMax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entropía", "MSI", "BFR", "LightPaths", "PathConse", "Entr/uso", "% Uso", "Prob Bloq"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultadosMinMax.setColumnSelectionAllowed(true);
        jScrollPane5.setViewportView(jTableResultadosMinMax);
        jTableResultadosMinMax.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 560, 350, 63));

        etiquetaTextoMin1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMin1.setText("min");
        etiquetaTextoMin1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        etiquetaTextoMin1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 590, 30, 20));

        etiquetaTextoMax1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax1.setText("max");
        etiquetaTextoMax1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        etiquetaTextoMax1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        etiquetaTextoMax1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(etiquetaTextoMax1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 600, 30, 20));

        jTabbedPane1.addTab("Datos", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 990, 660));

        etiquetaRSA2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        etiquetaRSA2.setText("Alg. de Ruteo");
        getContentPane().add(etiquetaRSA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Otros");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        etiquetaAnchoFSActual19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual19.setText("0 = No considera");
        getContentPane().add(etiquetaAnchoFSActual19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, 100, 20));

        textFieldPeriodoDesfrag.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldPeriodoDesfrag.setText("100");
        textFieldPeriodoDesfrag.setToolTipText("");
        textFieldPeriodoDesfrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPeriodoDesfragActionPerformed(evt);
            }
        });
        getContentPane().add(textFieldPeriodoDesfrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 40, 20));

        etiquetaAnchoFSActual22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        etiquetaAnchoFSActual22.setText("Período Desfrag. para DT fijo");
        getContentPane().add(etiquetaAnchoFSActual22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 140, 20));

        etiquetaTextoCantRutasReruteadas.setText("Total Rutas Reruteadas:");
        getContentPane().add(etiquetaTextoCantRutasReruteadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 0, 140, 20));

        etiquetaCantRutasReruteadas.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaCantRutasReruteadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 20, 50, 20));

        etiquetaCantDesfrag.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(etiquetaCantDesfrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, 50, 20));

        etiquetaTextoCantDesfrag.setText("Cant. Desfragmentaciones:");
        getContentPane().add(etiquetaTextoCantDesfrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, -1, 20));

        ComboMetodoDesfrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AG" }));
        ComboMetodoDesfrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboMetodoDesfragActionPerformed(evt);
            }
        });
        getContentPane().add(ComboMetodoDesfrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 440, 140, -1));

        ComboMetodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin Desfragmentar", "Reactivo", "DT Fijo", "IA" }));
        ComboMetodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboMetodoActionPerformed(evt);
            }
        });
        getContentPane().add(ComboMetodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 432, 170, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Método");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros de Pores Rutas"));

        etiquetaAnchoFSActual26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual26.setText("Rutas a Rerutear:");
        jPanel3.add(etiquetaAnchoFSActual26);

        textFieldRutasARerutear.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldRutasARerutear.setText("30");
        textFieldRutasARerutear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldRutasARerutearActionPerformed(evt);
            }
        });
        jPanel3.add(textFieldRutasARerutear);

        etiquetaAnchoFSActual12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual12.setText("%");
        jPanel3.add(etiquetaAnchoFSActual12);

        etiquetaTopologia2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTopologia2.setText("Objetivo Peores Rutas");
        jPanel3.add(etiquetaTopologia2);

        ComboObjetivoReruteo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entropía", "Path Consecutiveness", "BFR", "MSI" }));
        ComboObjetivoReruteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboObjetivoReruteoActionPerformed(evt);
            }
        });
        jPanel3.add(ComboObjetivoReruteo);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 410, 60));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Parametros ACO"));

        etiquetaTopologia1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        etiquetaTopologia1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaTopologia1.setText("Objetivo ACO");
        jPanel4.add(etiquetaTopologia1);

        etiquetaError.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel4.add(etiquetaError);

        ComboObjetivoACO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entropía", "Path Consecutiveness", "BFR", "MSI" }));
        ComboObjetivoACO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboObjetivoACOActionPerformed(evt);
            }
        });
        jPanel4.add(ComboObjetivoACO);

        etiquetaAnchoFSActual8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual8.setText("Cant. hormigas:");
        jPanel4.add(etiquetaAnchoFSActual8);

        textFieldCantHormigas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldCantHormigas.setText("30");
        textFieldCantHormigas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldCantHormigasActionPerformed(evt);
            }
        });
        jPanel4.add(textFieldCantHormigas);

        etiquetaAnchoFSActual20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual20.setText("Mejora buscada en ACO:");
        jPanel4.add(etiquetaAnchoFSActual20);

        textFieldMejoraACO.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        textFieldMejoraACO.setText("20");
        textFieldMejoraACO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMejoraACOActionPerformed(evt);
            }
        });
        jPanel4.add(textFieldMejoraACO);

        etiquetaAnchoFSActual6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual6.setText("%");
        jPanel4.add(etiquetaAnchoFSActual6);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 350, 70));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Parámetros Agoritmo Genético"));

        jLabel1.setText("Objetivo");
        jPanel6.add(jLabel1);

        ComboObjAlgoritmoGenetico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BFR" }));
        ComboObjAlgoritmoGenetico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboObjAlgoritmoGeneticoActionPerformed(evt);
            }
        });
        jPanel6.add(ComboObjAlgoritmoGenetico);

        jLabel7.setText("Cant. de individuos");
        jPanel6.add(jLabel7);

        jTextFieldCantIndividuosAG.setText("50");
        jTextFieldCantIndividuosAG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantIndividuosAGActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldCantIndividuosAG);

        label1.setText("Cant.Generac.");
        jPanel6.add(label1);

        jTextFieldCantGeneraciones.setText("50");
        jTextFieldCantGeneraciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCantGeneracionesActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldCantGeneraciones);

        jLabel8.setText("Tamaño del Cromosoma :");
        jPanel6.add(jLabel8);

        jTextFieldMejoraAG.setText("30");
        jTextFieldMejoraAG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMejoraAGActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldMejoraAG);

        etiquetaAnchoFSActual7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaAnchoFSActual7.setText("% de las rutas activas");
        jPanel6.add(etiquetaAnchoFSActual7);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 450, 70));

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEjecutarSimulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEjecutarSimulacionActionPerformed

        // Al inicio de cada Simulacion e+condemos los paneles de Resultado
        //this.panelResultadosBloqueos.setVisible(false);
//        this.etiquetaTextoMax.setVisible(false);
//        this.etiquetaDemandasTotales.setVisible(false);
//        this.etiquetaTextoBloqueosTotales.setVisible(false);
//        this.etiquetaBloqueosTotales.setVisible(false);

        //evita desfragmentar cuando hay muchos bloqueos
        int noLogroEvitar = -1, yaDesfragmento = -1;
                
        this.etiquetaError.setText("Simulando...");
        this.etiquetaError.setVisible(true); 
        
        //inicializamos algunas variables
        this.cantidadDeAlgoritmosTotalSeleccionados = 0;
        this.algoritmosCompletosParaGraficar.clear();
        
        //borramos los resultados que están en las tablas
        Utilitarios.reiniciarJTableRows(this.jTableResultados);
        Utilitarios.reiniciarJTableRows(this.jTableResultadosMinMax);
        Utilitarios.reiniciarJTableRows(this.jTableResultadosBloqueos);
        Utilitarios.reiniciarJTableRows(this.jTableResultadosBloqueosMinMax);
        Utilitarios.reiniciarJTableRows(this.jTableEstadoEnlaces);
        Utilitarios.reiniciarJTableColumns(this.jTableEstadoEnlaces);
        Utilitarios.reiniciarJTableRows(this.jTableResultadosDefrag);
        
        //pone en cero los resultados
        etiquetaCantRutasReruteadas.setText("0");
        etiquetaCantDesfrag.setText("0");
        etiquetaDemandasTotales.setText("0");
        etiquetaBloqueosTotales.setText("0");
        
        Integer resultadoReRuteo; //resultado Reruteo peores rutas
        
        //método
        this.metodo = (String) this.ComboMetodo.getSelectedItem(); 
        
        //desfrag metodo
        String metodoDesfrag = (String) this.ComboMetodoDesfrag.getSelectedItem();
        
        //Peores rutas
        String ObjetivoReruteo = (String) this.ComboObjetivoReruteo.getSelectedItem();
        double porcRutasARerutear = Double.parseDouble(this.textFieldRutasARerutear.getText());
        
        //parámetros ACO
        double mejoraACO = Double.parseDouble(this.textFieldMejoraACO.getText());
        int cantHormACO = Integer.parseInt(this.textFieldCantHormigas.getText());
        String objetivoACO = (String) this.ComboObjetivoACO.getSelectedItem(); 
        
        //Parámetros AG
        int porcentajeLongCRAG = Integer.parseInt(this.jTextFieldMejoraAG.getText());
        int cantIndividuosAG = Integer.parseInt(this.jTextFieldCantIndividuosAG.getText());
        String objetivoAG = (String) this.ComboObjAlgoritmoGenetico.getSelectedItem(); 
        int cantGeneracionesAG = Integer.parseInt(this.jTextFieldCantGeneraciones.getText());
        //int porcentajeMejora = Integer.parseInt(this.jTextField1.getText()); 
        //leemos los valores seteados
        this.tiempoTotal = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); //Tiempo de simulacion indicado por el usuario
        this.redSeleccionada = (String) this.listaRedes.getSelectedItem(); // obtenemos la topologia seleccionada en letras
        
        this.anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText()); // ancho de los FSs de la toplogia elegida, tambien indicado por el usuario
        this.capacidadPorEnlace = Integer.parseInt(this.textFieldCapacidadEnlace.getText()); //obtenemos la cantidad de FS de los enlaces indicados por el usuario
        this.Erlang = Integer.parseInt(this.spinnerErlang.getValue().toString()); //obtenemos Erlang indicados por el usuario
        this.Lambda = Integer.parseInt(this.textFieldLambda.getText()); //obtenemos Erlang indicados por el usuario
        this.HoldingTime = (Erlang / Lambda); // Erlang / Lambda
        this.FsMinimo = Integer.parseInt(this.textFieldFSminimo.getText()); //obtenemos FSminimo indicados por el usuario
        this.FsMaximo = Integer.parseInt(this.textFieldFSmaximo.getText()); //obtenemos FSmaximo indicados por el usuario
        
        /*Declaracion de estructura de datos para estadisticas de bloqueo */
        /*vector para guardar los datos y establecer una estadistica de bloqueos 
        por cantidad de ranuras requeridas*/
        int tam=FsMaximo - FsMinimo +2;
        int ranuras []= new int [tam];/*se toma el indice del vector como la cantidad de ranura
         en ella se guardan la suma de los bloqueos para cada ranura */
        int cantidadTotalBloqueos =0;
        /*fin de la declaracion para datos estadisticos de bloqueo*/
        
        //FS mínimos para considerar en el PatchConsecutiveness
        int FSMinPC = (int) (FsMaximo - ((FsMaximo - FsMinimo) * 0.3));

        //Guardar el seleccionado en la lista de algoritmos seleccionados, más adelante ver como agregar más algoritmos a la lista
        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
        this.algoritmosCompletosParaGraficar.add(cantidadDeAlgoritmosTotalSeleccionados, algoritmoSeleccionado);
        this.cantidadDeAlgoritmosTotalSeleccionados++;
        
        //parámetros desfragmentación
        int periodoDesfrag = Integer.parseInt(this.textFieldPeriodoDesfrag.getText()); //Tiempo de simulacion indicado por el usuario
        int ultimoDesfrag = periodoDesfrag;
        

        GrafoMatriz G[] = new GrafoMatriz[this.algoritmosCompletosParaGraficar.size()]; // Se tiene una matriz de adyacencia por algoritmo RSA elegidos para por el usuario
        ListaEnlazada[] caminosDeDosEnlaces = null;
        Demanda d = new Demanda();  // Demanda a recibir
        File archivoDemandas = null;
        Resultado r = new Resultado(); // resultado obtenido en una demanda. Si r==null se cuenta como bloqueo
        String mensajeError = "Seleccione: "; // mensaje de error a mostrar eal usuario si no ha completado los parametros de
        // simulacion

        List<String> RSA = new LinkedList<String>(); // lista de Algoritmos RSA seleccionados
        ResultadoRuteo r1 = new ResultadoRuteo(); // resultado optenido luego de ejecutarse un algoritmo de ruteo

        int E = (int) this.spinnerErlang.getValue(); // se obtiene el limite de carga (Erlang) de trafico seleccionado por el usuario
        ArrayList<Demanda> demandasPorUnidadTiempo = new ArrayList<>(); //ArrayList que contiene las demandas para una unidad de tiempo T
        rutasEstablecidas = new ArrayList();
        arrayRutas = new ArrayList<>();
        demandasBloqueadas = new ArrayList<>();
        resultadoRuteo = new ArrayList<>();
        listaKSP = new ArrayList<>();
        
        int sumSlots = 0;
        int sumBlockedSlots =  0;
        int k = -1; // contador auxiliar
        //int paso = (int) this.spinnerPaso.getValue(); // siguiente carga de trafico a simular (Erlang)
        int contD = 0; // contador de demandas totales
        int tiempoT = Integer.parseInt(this.spinnerTiempoSimulacion.getValue().toString()); // Tiempo de simulacion especificada por el usaurio
        double anchoFS = Double.parseDouble(this.textFieldAnchoFS.getText()); // ancho de FS
        //factor del tiempo de simulacion especificado por el usuario

        System.out.println("El ancho del FS es:" + anchoFS);
        System.out.println("Cantidad de FS por enlace:" + capacidadPorEnlace);
        System.out.println("Cantidad Algoritmos:" + this.cantidadDeAlgoritmosTotalSeleccionados);

        //if(this.listaDemandas.getSelectedIndex()>=0 && this.listaAlgoritmosRuteo.getSelectedIndex()>=0 && 
        //        this.listaRedes.getSelectedIndex()>=0 && this.listaAlgoritmosAS.getSelectedIndex()>=0 && this.cantidadDeAlgoritmosTotalSeleccionados >0){ // si todos los parametros fueron seleccionados
        if (this.listaAlgoritmosRuteo.getSelectedIndex() >= 0 && this.listaRedes.getSelectedIndex() >= 0 && this.cantidadDeAlgoritmosTotalSeleccionados > 0) {

            RSA = this.algoritmosCompletosParaGraficar; // obtenemos los algoritmos RSA seleccionados

            //String demandaSeleccionada = this.listaDemandas.getSelectedValue(); // obtenemos el tipo de trafico seleccionado
            int[] conexid = new int[RSA.size()];

            for (int i = 0; i < conexid.length; i++) {
                conexid[i] = 0;
            }

            int[] contB = new int[RSA.size()]; // array encargado de almacenar en cada posicion la cantidad de bloqueo para cada
            // algoritmo seleccionado
            List prob[] = new LinkedList[RSA.size()]; // probabilidad de bloqueo para cada algoritmo RSA selecciondo 

            for (int i = 0; i < prob.length; i++) {
                prob[i] = new LinkedList(); // para cada algoritmo, se tiene una lista enlazada que almacenara la Pb 
                // obtenidad en cada simulacion
            }

            switch (redSeleccionada) { // cargamos los datos en las matrices de adyacencia segun la topologia seleccionada
                case "Red 0":
                    topologia = this.Redes.getTopologia(0);
                    //de ´rueba no utilizado
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(0).getCantidadDeVertices());
                        G[i].insertarDatos(topologia);
                    }
                    break;
                case "NSFNet":
                    topologia = this.Redes.getTopologia(1);
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(1).getCantidadDeVertices());
                        G[i].insertarDatos(topologia);
                    }
                    caminosDeDosEnlaces = Utilitarios.hallarCaminosTomadosDeADos(topologia, 14, 21);
                    break;
                case "ARPA-2":
                    topologia = this.Redes.getTopologia(2);
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(2).getCantidadDeVertices());
                        G[i].insertarDatos(topologia);
                    }
                    caminosDeDosEnlaces = Utilitarios.hallarCaminosTomadosDeADos(topologia, 21, 26);
                case "USNet":
                    topologia = this.Redes.getTopologia(3);
                    for (int i = 0; i < RSA.size(); i++) {
                        G[i] = new GrafoMatriz(this.Redes.getRed(3).getCantidadDeVertices());
                        G[i].insertarDatos(topologia);
                    }
                    caminosDeDosEnlaces = Utilitarios.hallarCaminosTomadosDeADos(topologia, 24, 43);
            }
            
            //generar archivo de demandas
            try {
                //while (earlang <= E) { // mientras no se llega a la cargad de trafico maxima
                archivoDemandas = Utilitarios.generarArchivoDemandas(Lambda, tiempoTotal, FsMinimo, FsMaximo, G[0].getCantidadDeVertices(), HoldingTime, Erlang, "sb");
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Construimos el nombre del archivo con la fecha y hora
            Calendar calendario = new GregorianCalendar();
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            anho = calendario.get(Calendar.YEAR);
            File carpeta = new File(System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\");
            String detallesNombre = ""+Lambda + "k_" + tiempoTotal + "t-" + RSA.get(0) + "-" + dia + "-" + mes + "-" + anho + "-" + hora + "_" + minutos + "_" + segundos + ".txt";
            String ruta = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Resultado"+detallesNombre;
            String rutaDefrag = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Defrag"+detallesNombre;
            String rutaEstados = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Estados"+detallesNombre;
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivoResultados = new File(ruta);
            File archivoDefrag = new File(rutaDefrag);
            File archivoEstados = new File(rutaEstados);
            
            int sumaTiempoDeVida = 0;
            
            String algoritmoAejecutar = RSA.get(0);
             ArrayList<Integer> slotsC = new ArrayList<>();
            ArrayList<Integer> blockedSlots = new ArrayList<>();
            
            
            for (int i = 1; i <= tiempoT; i++) {
                haybloqueos = false;
            
//                //imprimir estado de los enlaces
//                System.out.println("Grafo al empezar el tiempo: " + i);
//                Utilitarios.actualizarTablaEstadoEnlaces(G[0],this.jTableEstadoEnlaces,capacidadPorEnlace);

                
                
                try {
                    demandasPorUnidadTiempo = Utilitarios.leerDemandasPorTiempo(archivoDemandas, i); //lee las demandas para el tiempo i
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Demanda demanda : demandasPorUnidadTiempo) { // para cada demanda
                    sumSlots += demanda.getNroFS();
                    sumaTiempoDeVida = sumaTiempoDeVida + demanda.getTiempo();
                    esBloqueo = false;
                    ListaEnlazada[] ksp = Utilitarios.KSP(G[0], demanda.getOrigen(), demanda.getDestino(), 5); // calculamos los k caminos mas cortos entre el origen y el fin. Con k=5 (pude ser mas, cambiar dependiendo de la necesidad)
                    for (int a = 0; a < RSA.size(); a++) {
                        
                        algoritmoAejecutar = RSA.get(a);

                        switch (algoritmoAejecutar) {
                            case "FA":
                                r = Algoritmos_Defrag_ProAct.Def_FA(G[a], demanda, ksp, capacidadPorEnlace);
                                if(slotsC.size() == 10){
                                        slotsC.remove(0);
                                        blockedSlots.remove(0);
                                }
                                slotsC.add(demanda.getNroFS());
                                if(r == null)
                                    blockedSlots.add(demanda.getNroFS());
                                else
                                    blockedSlots.add(0);

                                sumSlots = 0;
                                for(int k2 = 0; k2 < slotsC.size(); k2++)
                                    sumSlots += slotsC.get(k2);

                                sumBlockedSlots = 0;
                                for(int k2 = 0; k2 < slotsC.size(); k2++)
                                    sumBlockedSlots += blockedSlots.get(k2);
                                

                                if (r != null) {//si se pudo establecer la demanda
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                    listaKSP.add(ksp);
                                } else {
                                    /*Inicio de creacion de estadistica de los bloqueos segun la cantidad de ranuras requeridas*/
                                    
                                    int cantidadRanurasRequeridas = demanda.getNroFS();
                                    ranuras[cantidadRanurasRequeridas]+=1;
                                    cantidadTotalBloqueos++;
                                    
                                    /*Fin del bloque para obtener los datos estadisticos de bloqueo*/
                                    if(metodo == "Reactivo" && noLogroEvitar<i && yaDesfragmento!=i){
                                        System.out.println("Inicia desfragmentacion en el tiempo "+i+" con "+arrayRutas.size()+" rutas activas");
                                        yaDesfragmento=i;                                        try {
                                            if (metodoDesfrag == "ACO"){
                                                encontroSolucion = Utilitarios.desfragmentacionACO(topologia,RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, caminosDeDosEnlaces, this.jTableEstadoEnlaces, FSMinPC, objetivoACO);
                                            }else if(metodoDesfrag.equals("AG")){
                                                encontroSolucionAG = Utilitarios.desfragmentacionAG(topologia,RSA.get(0), resultadoRuteo, arrayRutas, porcentajeLongCRAG, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantIndividuosAG, objetivoAG,cantGeneracionesAG);
                                            }else{
                                                resultadoReRuteo = Utilitarios.desfragmentacionPeoresRutas(topologia, G[0], capacidadPorEnlace, arrayRutas, resultadoRuteo, listaKSP, ObjetivoReruteo, porcRutasARerutear , FSMinPC, algoritmoAejecutar, rutasEstablecidas);
                                                //suma el resultado
                                                etiquetaCantRutasReruteadas.setText("" + (int) (Integer.parseInt("" + etiquetaCantRutasReruteadas.getText()) + resultadoReRuteo));
                                                if (resultadoReRuteo > 0){ //si resultadoReRuteo es mayor a cero si se hizo la desfragmentación
                                                    etiquetaCantDesfrag.setText("" + (int) (Integer.parseInt("" + etiquetaCantDesfrag.getText()) + 1)); //suma 1
                                                }
                                            } 
                                        } catch (IOException ex) {
                                            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        r = Algoritmos_Defrag_ProAct.Def_FA(G[a], demanda, ksp, capacidadPorEnlace);
                                        if (r != null) {
                                            Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                            rutasEstablecidas.add(demanda.getTiempo());
                                            arrayRutas.add(ksp[r.getCamino()]);
                                            resultadoRuteo.add(r);
                                            listaKSP.add(ksp);
                                        } else{
                                            System.out.println("Desfragmento en el tiempo: "+i+"pero no logro evitar el bloqueo");
                                            noLogroEvitar = i;
                                            contB[a]++;
                                            contBloqueos++;
                                            esBloqueo = true;
                                            haybloqueos = true;
                                        }
                                    }else{
                                        contB[a]++;
                                        contBloqueos++;
                                        esBloqueo = true;
                                        haybloqueos = true;
                                    }  
                                }
                                break;
                            case "FA-CA":
                                r = Algoritmos_Defrag_ProAct.Def_FACA(G[a], demanda, ksp, capacidadPorEnlace);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                    listaKSP.add(ksp);
                                } else {
                                    if(metodo == "Reactivo" && noLogroEvitar<i){
                                        try {
                                            if (metodoDesfrag == "ACO"){
                                                encontroSolucion = Utilitarios.desfragmentacionACO(topologia,RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, caminosDeDosEnlaces, this.jTableEstadoEnlaces, FSMinPC, objetivoACO);
                                            }else if(metodoDesfrag.equals("AG")){
                                                encontroSolucionAG = Utilitarios.desfragmentacionAG(topologia,RSA.get(0), resultadoRuteo, arrayRutas, porcentajeLongCRAG, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantIndividuosAG, objetivoAG,cantGeneracionesAG);
                                            }else{
                                                resultadoReRuteo = Utilitarios.desfragmentacionPeoresRutas(topologia, G[0], capacidadPorEnlace, arrayRutas, resultadoRuteo, listaKSP, ObjetivoReruteo, porcRutasARerutear , FSMinPC, algoritmoAejecutar, rutasEstablecidas);
                                                //suma el resultado
                                                etiquetaCantRutasReruteadas.setText("" + (int) (Integer.parseInt("" + etiquetaCantRutasReruteadas.getText()) + resultadoReRuteo));
                                                if (resultadoReRuteo > 0){ //si resultadoReRuteo es mayor a cero si se hizo la desfragmentación
                                                    etiquetaCantDesfrag.setText("" + (int) (Integer.parseInt("" + etiquetaCantDesfrag.getText()) + 1)); //suma 1
                                                }
                                            } 
                                        } catch (IOException ex) {
                                            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        r = Algoritmos_Defrag_ProAct.Def_FACA(G[a], demanda, ksp, capacidadPorEnlace);
                                        if (r != null) {
                                            Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                            rutasEstablecidas.add(demanda.getTiempo());
                                            arrayRutas.add(ksp[r.getCamino()]);
                                            resultadoRuteo.add(r);
                                            listaKSP.add(ksp);
                                        } else{
                                            System.out.println("Desfragmento en el tiempo: "+i+"pero no logro evitar e bloqueo");
                                            noLogroEvitar = i;
                                            contB[a]++;
                                            contBloqueos++;
                                            esBloqueo = true;
                                            haybloqueos = true;
                                        }
                                    }else{
                                        contB[a]++;
                                        contBloqueos++;
                                        esBloqueo = true;
                                        haybloqueos = true;
                                    }  
                                }
                                break;
                           case "MTLSC":
                                r = Algoritmos.MTLSC_Algorithm(G[a], demanda, ksp, capacidadPorEnlace);
                                if (r != null) {
                                    Utilitarios.asignarFS_Defrag(ksp, r, G[a], demanda, ++conexid[a]);
                                    rutasEstablecidas.add(demanda.getTiempo());
                                    arrayRutas.add(ksp[r.getCamino()]);
                                    resultadoRuteo.add(r);
                                } else {
                                    
                                    contB[a]++;
                                    contBloqueos++;
                                    esBloqueo = true;
                                    haybloqueos = true;
                                }
                                break;
                        }

                    }
                    contD++;
                    //Para cada demanda guardar el estado de la red, para el analisis de metricas
                    for (int a = 0; a < RSA.size(); a++) {
                        //Escribimos el archivo de resultados
                        entropia = msi = bfr = pathConsec = entropiaUso = shf = 0.0;
                        entropia = G[a].entropia();
                        msi = Metricas.MSI(G[a], capacidadPorEnlace);
                        bfr = Metricas.BFR(G[a], capacidadPorEnlace);
                        pathConsec = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadPorEnlace, G[a], FSMinPC);
                        entropiaUso = Metricas.EntropiaPorUso(caminosDeDosEnlaces, capacidadPorEnlace, G[a]);
                        porcUso = Metricas.PorcUsoGrafo(G[a]);
                        shf = Metricas.shf(G[a], capacidadPorEnlace);
                        Utilitarios.escribirArchivoEstados(archivoEstados, entropia, msi, bfr, pathConsec, entropiaUso, esBloqueo, rutasEstablecidas.size(),porcUso, shf);
                    }
                   
                }
                //Disminuir el tiempo de vida de todas las rutas en la red
                for (int j = 0; j < RSA.size(); j++) {
                    Utilitarios.Disminuir(G[j]);
                }
                //verificar si la ruta sigue activa o no dentro de la red.
                for (int index = 0; index < rutasEstablecidas.size(); index++) {
                    rutasEstablecidas.set(index, rutasEstablecidas.get(index) - 1);
                }
                //Segundo for para evitar problemas con los indices al borrar
                for (int index = 0; index < rutasEstablecidas.size(); index++) {
                    if (rutasEstablecidas.get(index) == 0) { //si el tiempo de vida es cero
                        rutasEstablecidas.remove(index); //remover del contador de rutas establecidas
                        arrayRutas.remove(index); //remover la ruta de la lista de rutas vigentes
                        resultadoRuteo.remove(index);//remueve de la lista de resultados de ruteo
                        listaKSP.remove(index);
                        index--;
                    }
                }
                for (int a = 0; a < RSA.size(); a++) {
                    //Escribimos el archivo de resultados
                    entropia = msi = bfr = pathConsec = entropiaUso = probBloqueo = shf= 0.0;
                    entropia = G[a].entropia();
                    msi = Metricas.MSI(G[a], capacidadPorEnlace);
                    bfr = Metricas.BFR(G[a], capacidadPorEnlace);
                    pathConsec = Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadPorEnlace, G[a], FSMinPC);
                    entropiaUso = Metricas.EntropiaPorUso(caminosDeDosEnlaces, capacidadPorEnlace, G[a]);
                    porcUso = Metricas.PorcUsoGrafo(G[a]);
                    probBloqueo = Utilitarios.calcularProbabilidadDeBloqueo(entropia, msi, bfr, pathConsec, entropiaUso, porcUso, arrayRutas.size());
                    shf= Metricas.shf(G[a],capacidadPorEnlace);
                    Utilitarios.escribirArchivoResultados(archivoResultados, i, contBloqueos, demandasPorUnidadTiempo.size(), entropia, msi, bfr, rutasEstablecidas.size(), pathConsec, entropiaUso,porcUso,shf,sumSlots, sumBlockedSlots,probBloqueo);
                
                }
                
                if(metodo == "IA") {
                    
                    try {
                        double ratio = Utilitarios.getRatioIA(entropia,pathConsec, shf, msi, porcUso, sumBlockedSlots, bfr);
                        System.out.println("Tiempo: " + i + ", Rutas activas: " + rutasEstablecidas.size() + ", Ratio: " + ratio );
                        if(ratio >= 0.22) {
                            encontroSolucionAG = Utilitarios.desfragmentacionAG(topologia,RSA.get(0), resultadoRuteo, arrayRutas, porcentajeLongCRAG, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i,cantIndividuosAG,objetivoAG,cantGeneracionesAG);
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //no modificar 
                if(metodo == "DT Fijo"){
                   
                    if(i == ultimoDesfrag && i != tiempoTotal){// cada periodo y que no haga si es el ultimo tiempo
                        ultimoDesfrag = ultimoDesfrag + periodoDesfrag;
                        try {
                            System.out.println("Inicia desfragmentacion en tiempo "+i+" con "+arrayRutas.size()+" rutas activas");
                            if (metodoDesfrag == "ACO"){
                                encontroSolucion = Utilitarios.desfragmentacionACO(topologia,RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, caminosDeDosEnlaces, this.jTableEstadoEnlaces, FSMinPC, objetivoACO);
                            }else if(metodoDesfrag.equals("AG")){
                                encontroSolucionAG = Utilitarios.desfragmentacionAG(topologia,RSA.get(0), resultadoRuteo, arrayRutas, porcentajeLongCRAG, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i,cantIndividuosAG,objetivoAG,cantGeneracionesAG);
                            }else{
                                resultadoReRuteo = Utilitarios.desfragmentacionPeoresRutas(topologia, G[0], capacidadPorEnlace, arrayRutas, resultadoRuteo, listaKSP, ObjetivoReruteo, porcRutasARerutear , FSMinPC, algoritmoAejecutar, rutasEstablecidas);
                                //suma el resultado
                                etiquetaCantRutasReruteadas.setText("" + (int) (Integer.parseInt("" + etiquetaCantRutasReruteadas.getText()) + resultadoReRuteo));
                                if (resultadoReRuteo > 0){ //si resultadoReRuteo es mayor a cero si se hizo la desfragmentación
                                    etiquetaCantDesfrag.setText("" + (int) (Integer.parseInt("" + etiquetaCantDesfrag.getText()) + 1)); //suma 1
                                }
                            } 
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
              
                //no modificar
                /*if(i==tiempoDesfrag){// || i==500 || i==700){
                    try {
                        Utilitarios.desfragmentacionACO(topologia,RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, caminosDeDosEnlaces, this.jTableEstadoEnlaces, FSMinPC,objetivoACO);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
                
                //ESTE SI PODES MODIFICAR
//                if(haybloqueos){
//                    System.out.println("Path Consecutiveness Antes: "+ Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadPorEnlace, G[0]));
//                    try {
//                        Utilitarios.seleccionDeRutasPathConsec(this.Redes.getTopologia(1),RSA.get(0), resultadoRuteo, arrayRutas, mejoraACO, capacidadPorEnlace, G[0], listaKSP, archivoDefrag, i, cantHormACO, this.jTableEstadoEnlaces, caminosDeDosEnlaces);
//                    } catch (IOException ex) {
//                        Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                                    System.out.println("Path Consecutiveness Despues: "+ Metricas.PathConsecutiveness(caminosDeDosEnlaces, capacidadPorEnlace, G[0]));
//                }
                
                contBloqueos = 0;
                
            }
            ++k;
            /*Inicio de impresion de  los datos de los estadisticos de boqueos*/
            
            
            System.out.println("\nRanuras   Cantidad de Bloqueos\n");
            for(int aux=1;aux < ranuras.length; aux++){
                System.out.println("  "+aux+"\t\t"+ranuras[aux] );       
            }
            System.out.println("\nLa  cantidad de bloqueos total : "+cantidadTotalBloqueos );
            
            
            /*Fin de la impresion de la estadistica de bloqueos*/
            // almacenamos la probablidad de bloqueo final para cada algoritmo
            for (int a = 0; a < RSA.size(); a++) {
                prob[a].add(((double) contB[a] / contD));
                System.out.println("Probabilidad: " + (double) prob[a].get(k) + " Algoritmo: " + RSA.get(a));
            }
            this.etiquetaError.setText("Simulacion Terminada...");
            
            //Si no existe escibir 0,0,0
            if(!archivoDefrag.exists()){
                        try {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoDefrag));
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write(",");
                            bw.write("" + 0);
                            bw.write("\r\n");
                            bw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            //RESULTADOS
            
            //suma de tiempos de vida
            System.out.println("Suma de tiempos de vida: " + sumaTiempoDeVida);

            // una vez finalizado, graficamos el resultado.
            //leemos el archivo de resultados
            List<XYTextAnnotation> annotation = new LinkedList<>();
            String linea;
            int contLinea = 0;
            XYSeries series[] = new XYSeries[12];
            
            //tablas
            DefaultTableModel modelBloqueos = (DefaultTableModel) this.jTableResultadosBloqueos.getModel(); //todos
            DefaultTableModel modelResultados = (DefaultTableModel) this.jTableResultados.getModel(); //bloqueos

            FileReader fr;
            try {
                fr = new FileReader(archivoResultados);
                BufferedReader br = new BufferedReader(fr);
                series[0] = new XYSeries("Bloqueos");
                series[1] = new XYSeries("Entropía");
                series[2] = new XYSeries("MSI");
                series[3] = new XYSeries("BFR");
                series[4] = new XYSeries("Cantidad de Light Paths");
                series[5] = new XYSeries("Path Consecutiveness");
                series[6] = new XYSeries("Entropía por su uso");
                series[7] = new XYSeries("% Uso");
                series[8] = new XYSeries("SHF");
                series[9] = new XYSeries("SumSlots");
                series[10] = new XYSeries("SumBlockedSlots");
                series[11] = new XYSeries("Prob Bloqueo");

                while (((linea = br.readLine()) != null)) {
                    contLinea++;
                    String[] line = linea.split(",", 14);
                    
                    //agrega a la tabla los Resultados
                        modelResultados.addRow(new Object[]{line[0], line[1], line[2], (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4]), (double) Double.parseDouble(line[5]), (double) Double.parseDouble(line[6]), (double) Double.parseDouble(line[7]), (double) Double.parseDouble(line[8]), (double) Double.parseDouble(line[9]), (double) Double.parseDouble(line[10]),(int)Integer.parseInt(line[11]),(int)Integer.parseInt(line[12]), (double) Double.parseDouble(line[13])});
                    
                    //agrega en annotation todos los bloqueos para después agregarlos a los gráficos
                    if ((double) Double.parseDouble(line[2]) > 0) {
                        annotation.add(new XYTextAnnotation(line[2], (double) Double.parseDouble(line[0]), 0.02));
                        //agrega a la tabla los bloqueos
                        modelBloqueos.addRow(new Object[]{line[0], line[1], line[2], (double) Double.parseDouble(line[3]), (double) Double.parseDouble(line[4]), (double) Double.parseDouble(line[5]), (double) Double.parseDouble(line[6]), (double) Double.parseDouble(line[7]), (double) Double.parseDouble(line[8]), (double) Double.parseDouble(line[9]), (double) Double.parseDouble(line[10]),(int)Integer.parseInt(line[11]),(int)Integer.parseInt(line[12]), (double) Double.parseDouble(line[13])});
                    }

                    series[0].add(contLinea, (double) Double.parseDouble(line[2]));
                    series[1].add(contLinea, (double) Double.parseDouble(line[3]));
                    series[2].add(contLinea, (double) Double.parseDouble(line[4]));
                    series[3].add(contLinea, (double) Double.parseDouble(line[5]));
                    series[4].add(contLinea, (double) Double.parseDouble(line[6]));
                    series[5].add(contLinea, (double) Double.parseDouble(line[7]));
                    series[6].add(contLinea, (double) Double.parseDouble(line[8]));
                    series[7].add(contLinea, (double) Double.parseDouble(line[9]));
                    series[8].add(contLinea, (double) Double.parseDouble(line[10]));
                    series[9].add(contLinea, (int)Integer.parseInt(line[11]));
                    series[10].add(contLinea, (int)Integer.parseInt(line[12]));
                    series[11].add(contLinea, (double) Double.parseDouble(line[13]));
                }
                
                //hallar el max y min de los resultados
                guardarMaxMin(this.jTableResultados, this.jTableResultadosMinMax);
                
                //hallar el max y min de los bloqueos
                if (contB[0]!=0){
                    guardarMaxMin(this.jTableResultadosBloqueos, this.jTableResultadosBloqueosMinMax);
                }
                
                //graficar
                Utilitarios.GraficarResultado(series, annotation, this.panelResultados);
                
                //estado final de los enlaces
//                Utilitarios.actualizarTablaEstadoEnlaces(G[0], jTableResultados, capacidadPorEnlace);


            } catch (IOException ioe) {
                Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ioe);  
            }
            
            
            
            //tabla desfragmentaciones
            //solo si fue ACO porque peores rutas no guarda en el archivo, escribe directamente al terminar
            if (metodoDesfrag == "ACO"){
                Integer[] resultDefrags = new Integer[2];
                String rutaResultadosDefrag = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Defrag"+detallesNombre;
                File archivoResultadosDefrag = new File(rutaResultadosDefrag);
                try {
                    resultDefrags = Utilitarios.cargarTablaResultadosDefrag(archivoResultadosDefrag, this.jTableResultadosDefrag);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                }

                //imprime los resultados en la pantalla
                this.etiquetaCantDesfrag.setText("" + resultDefrags[0]);
                this.etiquetaCantRutasReruteadas.setText("" + resultDefrags[1]);                
            }
            if(metodoDesfrag == "AG"){
                Integer[] resultDefrags = new Integer[2];
                String rutaResultadosDefrag = System.getProperty("user.dir") + "\\src\\Defrag\\ProAct\\Archivos\\Resultados\\Defrag"+detallesNombre;
                File archivoResultadosDefrag = new File(rutaResultadosDefrag);
                try {
                    resultDefrags = Utilitarios.cargarTablaResultadosDefrag(archivoResultadosDefrag, this.jTableResultadosDefrag);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(Level.SEVERE, null, ex);
                }

                //imprime los resultados en la pantalla
                this.etiquetaCantDesfrag.setText("" + resultDefrags[0]);
                this.etiquetaCantRutasReruteadas.setText("" + resultDefrags[1]);
                System.out.println("Cantidad de reconfiguraciones: " + resultDefrags[1]);
            }


            //Utilitarios.GraficarResultado(prob, this.panelResultado, "Resultado de la Simulación", RSA, paso);
            String demandasTotales = "" + contD; // mostramos la cantidad de demandas totales recibidas
            this.etiquetaDemandasTotales.setText(demandasTotales);
            this.etiquetaBloqueosTotales.setText("" + contB[0]);
            this.etiquetaTextoBloqueosTotales.setVisible(true);
            this.etiquetaDemandasTotales.setVisible(true);
            this.etiquetaTextoDemandasTotales.setVisible(true);
            this.etiquetaBloqueosTotales.setVisible(true);
            this.etiquetaTextoCantDesfrag.setVisible(true);
            this.etiquetaCantDesfrag.setVisible(true);
            this.etiquetaTextoCantRutasReruteadas.setVisible(true);
            this.etiquetaCantRutasReruteadas.setVisible(true);

            ////////Vaciar listas para las siguientes simulaciones///////////////
            /////////////////////////////////////////////////////////////////////
            //this.algoritmosCompletosParaEjecutar.clear();
            //this.algoritmosCompletosParaGraficar.clear();
            //this.cantidadDeAlgoritmosRuteoSeleccionados = 0;
            this.cantidadDeAlgoritmosTotalSeleccionados = 0;

        } else { // control de errores posibles realizados al no completar los parametros de simulacion
            if (this.listaAlgoritmosRuteo.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Algoritmo RSA";
                } else {
                    mensajeError = mensajeError + ", Algoritmo RSA";
                }
            }
            if (this.listaRedes.getSelectedIndex() < 0) {
                if (mensajeError == "Seleccione ") {
                    mensajeError = mensajeError + "Topologia";
                } else {
                    mensajeError = mensajeError + ", Topologia";
                }
            }
            if (mensajeError != "Seleccione ") {
                this.etiquetaError.setText(mensajeError);
            }
        }
    }//GEN-LAST:event_botonEjecutarSimulacionActionPerformed

    // get the maximum and the minimum
    public void guardarMaxMin(JTable Tabla, JTable TablaMaxMin){
        DefaultTableModel model = (DefaultTableModel) TablaMaxMin.getModel();
        ArrayList<Double> list0 = new ArrayList<>();
        ArrayList<Double> list1 = new ArrayList<>();
        ArrayList<Double> list2 = new ArrayList<>();
        ArrayList<Double> list3 = new ArrayList<>();
        ArrayList<Double> list4 = new ArrayList<>();
        ArrayList<Double> list5 = new ArrayList<>();
        ArrayList<Double> list6 = new ArrayList<>();
        ArrayList<Double> list7 = new ArrayList<>();
        for(int i = 0; i < Tabla.getRowCount(); i++){
            list0.add(Double.parseDouble(Tabla.getValueAt(i,3).toString()));
            list1.add(Double.parseDouble(Tabla.getValueAt(i,4).toString()));
            list2.add(Double.parseDouble(Tabla.getValueAt(i,5).toString()));
            list3.add(Double.parseDouble(Tabla.getValueAt(i,6).toString()));
            list4.add(Double.parseDouble(Tabla.getValueAt(i,7).toString()));
            list5.add(Double.parseDouble(Tabla.getValueAt(i,8).toString()));
            list6.add(Double.parseDouble(Tabla.getValueAt(i,9).toString()));
            list7.add(Double.parseDouble(Tabla.getValueAt(i,10).toString()));
        }
        
        Double maxEntro;
        Double minEntro;
        Double maxMSI;
        Double minMSI;
        Double maxBRF;
        Double minBRF;
        Double maxLP;
        Double minLP;
        Double maxPC;
        Double minPC;
        Double maxEntroUso;
        Double minEntroUso;
        Double maxPorcUso;
        Double minPorcUso;
        Double maxPorcBloqueo;
        Double minPorcBloqueo;
        
        maxEntro = Collections.max(list0);
        minEntro = Collections.min(list0);
        maxMSI = Collections.max(list1);
        minMSI = Collections.min(list1);
        maxBRF = Collections.max(list2);
        minBRF = Collections.min(list2);
        maxLP = Collections.max(list3);
        minLP = Collections.min(list3);
        maxPC = Collections.max(list4);
        minPC = Collections.min(list4);
        maxEntroUso = Collections.max(list5);
        minEntroUso = Collections.min(list5);
        maxPorcUso = Collections.max(list6);
        minPorcUso = Collections.min(list6);
        maxPorcBloqueo = Collections.max(list7);
        minPorcBloqueo = Collections.min(list7);
        
        
        //agrega a la tabla los bloqueos
        model.addRow(new Object[]{minEntro, minMSI, minBRF, minLP, minPC, minEntroUso,minPorcUso,minPorcBloqueo});
        model.addRow(new Object[]{maxEntro, maxMSI, maxBRF, maxLP, maxPC, maxEntroUso,maxPorcUso,maxPorcBloqueo});
//        Tmax.setText(Integer.toString(max));
//        Tmin.setText(Integer.toString(min));
    }
    

            
    private void listaAlgoritmosRuteoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaAlgoritmosRuteoMouseClicked
        // TODO add your handling code here:
//        List algoritmosRuteoSeleccionados = this.listaAlgoritmosRuteo.getSelectedValuesList();
//        String algoritmoSeleccionado = (String) algoritmosRuteoSeleccionados.get(0);
//        //System.out.println("El algoritmosRuteoSeleccionados22:"+algoritmoSeleccionado);
//        if (algoritmoSeleccionado.equals("FAR")) {
//            this.panelAsignacionSpectro.setVisible(true);
//        } else {
//            this.panelAsignacionSpectro.setVisible(false);
//        }


    }//GEN-LAST:event_listaAlgoritmosRuteoMouseClicked

    private void listaRedesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaRedesActionPerformed
        setearRed();
    }//GEN-LAST:event_listaRedesActionPerformed

    private void setearRed() {
        if (this.listaRedes.getSelectedIndex() >= 0) {
            ImageIcon Img = new ImageIcon();
            
            String redseleccionada = (String) this.listaRedes.getSelectedItem();
            switch (redseleccionada) {
                case "NSFNet":
                    Img = new ImageIcon(getClass().getResource(("Imagenes/" + ("Red 1.png"))));
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(1).getCapacidadTotal() / this.Redes.getRed(1).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(1).getAnchoFS()));
                    break;
                case "ARPA-2":
                    Img = new ImageIcon(getClass().getResource(("Imagenes/" + ("Red 2.png"))));
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(2).getCapacidadTotal() / this.Redes.getRed(2).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(2).getAnchoFS()));
                    break;
                case "USNet":
                    Img = new ImageIcon(getClass().getResource(("Imagenes/" + ("Red 6.png"))));
                    this.textFieldCapacidadEnlace.setText(Integer.toString((int) (this.Redes.getRed(3).getCapacidadTotal() / this.Redes.getRed(3).getAnchoFS())));
                    this.textFieldAnchoFS.setText(Double.toString(this.Redes.getRed(3).getAnchoFS()));
                    break;
            }
            
            etiquetaImagenTopologia.setBounds(150, 110, 150, 110);
            etiquetaImagenTopologia.setIcon(Img);
            etiquetaImagenTopologia.setVisible(true);
            etiquetaImagenTopologia.setOpaque(false);
        }
    }
   
    
    private void textFieldLambdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldLambdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldLambdaActionPerformed

    private void textFieldAnchoFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldAnchoFSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldAnchoFSActionPerformed

    private void textFieldFSmaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldFSmaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldFSmaximoActionPerformed

    private void ComboMetodoDesfragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboMetodoDesfragActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboMetodoDesfragActionPerformed

    private void ComboMetodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboMetodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboMetodoActionPerformed

    private void textFieldFSminimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldFSminimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldFSminimoActionPerformed

    private void textFieldPeriodoDesfragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPeriodoDesfragActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldPeriodoDesfragActionPerformed

    private void textFieldMejoraACOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMejoraACOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldMejoraACOActionPerformed

    private void textFieldCantHormigasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldCantHormigasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldCantHormigasActionPerformed

    private void ComboObjetivoACOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboObjetivoACOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboObjetivoACOActionPerformed

    private void ComboObjetivoReruteoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboObjetivoReruteoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboObjetivoReruteoActionPerformed

    private void textFieldRutasARerutearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldRutasARerutearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldRutasARerutearActionPerformed

    private void jTextFieldCantIndividuosAGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCantIndividuosAGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCantIndividuosAGActionPerformed

    private void jTextFieldMejoraAGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMejoraAGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMejoraAGActionPerformed

    private void jTextFieldCantGeneracionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCantGeneracionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCantGeneracionesActionPerformed

    private void ComboObjAlgoritmoGeneticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboObjAlgoritmoGeneticoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboObjAlgoritmoGeneticoActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal_Defrag_ProAct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal_Defrag_ProAct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboMetodo;
    private javax.swing.JComboBox<String> ComboMetodoDesfrag;
    private javax.swing.JComboBox<String> ComboObjAlgoritmoGenetico;
    private javax.swing.JComboBox<String> ComboObjetivoACO;
    private javax.swing.JComboBox<String> ComboObjetivoReruteo;
    private javax.swing.JButton botonEjecutarSimulacion;
    private javax.swing.JLabel etiquetaAnchoFSActual;
    private javax.swing.JLabel etiquetaAnchoFSActual1;
    private javax.swing.JLabel etiquetaAnchoFSActual12;
    private javax.swing.JLabel etiquetaAnchoFSActual19;
    private javax.swing.JLabel etiquetaAnchoFSActual2;
    private javax.swing.JLabel etiquetaAnchoFSActual20;
    private javax.swing.JLabel etiquetaAnchoFSActual22;
    private javax.swing.JLabel etiquetaAnchoFSActual26;
    private javax.swing.JLabel etiquetaAnchoFSActual3;
    private javax.swing.JLabel etiquetaAnchoFSActual4;
    private javax.swing.JLabel etiquetaAnchoFSActual6;
    private javax.swing.JLabel etiquetaAnchoFSActual7;
    private javax.swing.JLabel etiquetaAnchoFSActual8;
    private javax.swing.JLabel etiquetaBloqueosTotales;
    private javax.swing.JLabel etiquetaCantDesfrag;
    private javax.swing.JLabel etiquetaCantRutasReruteadas;
    private javax.swing.JLabel etiquetaCapacidadActual;
    private javax.swing.JLabel etiquetaDemandasTotales;
    private javax.swing.JLabel etiquetaError;
    private javax.swing.JLabel etiquetaImagenTopologia;
    private javax.swing.JLabel etiquetaRSA1;
    private javax.swing.JLabel etiquetaRSA2;
    private javax.swing.JLabel etiquetaRSA3;
    private javax.swing.JLabel etiquetaTextoBloqueosTotales;
    private javax.swing.JLabel etiquetaTextoCantDesfrag;
    private javax.swing.JLabel etiquetaTextoCantRutasReruteadas;
    private javax.swing.JLabel etiquetaTextoDemandasTotales;
    private javax.swing.JLabel etiquetaTextoMax;
    private javax.swing.JLabel etiquetaTextoMax1;
    private javax.swing.JLabel etiquetaTextoMin;
    private javax.swing.JLabel etiquetaTextoMin1;
    private javax.swing.JLabel etiquetaTiempoActual;
    private javax.swing.JLabel etiquetaTopologia;
    private javax.swing.JLabel etiquetaTopologia1;
    private javax.swing.JLabel etiquetaTopologia2;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableEstadoEnlaces;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JTable jTableResultadosBloqueos;
    private javax.swing.JTable jTableResultadosBloqueosMinMax;
    private javax.swing.JTable jTableResultadosDefrag;
    private javax.swing.JTable jTableResultadosMinMax;
    private javax.swing.JTextField jTextFieldCantGeneraciones;
    private javax.swing.JTextField jTextFieldCantIndividuosAG;
    private javax.swing.JTextField jTextFieldMejoraAG;
    private java.awt.Label label1;
    private javax.swing.JList<String> listaAlgoritmosRuteo;
    private javax.swing.JComboBox<String> listaRedes;
    private javax.swing.JScrollPane panelResultados;
    private javax.swing.JSpinner spinnerErlang;
    private javax.swing.JSpinner spinnerTiempoSimulacion;
    private javax.swing.JTextField textFieldAnchoFS;
    private javax.swing.JTextField textFieldCantHormigas;
    private javax.swing.JTextField textFieldCapacidadEnlace;
    private javax.swing.JTextField textFieldFSmaximo;
    private javax.swing.JTextField textFieldFSminimo;
    private javax.swing.JTextField textFieldLambda;
    private javax.swing.JTextField textFieldMejoraACO;
    private javax.swing.JTextField textFieldPeriodoDesfrag;
    private javax.swing.JTextField textFieldRutasARerutear;
    // End of variables declaration//GEN-END:variables

}
