package GUI.controller;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import GUI.graph.Graph;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualBlackBoxControllerWithOutDataBase {

    @FXML private Label lblStatus;

    @FXML private ProgressBar progressBar;

    @FXML private Circle c1;
    @FXML private Circle c2;
    @FXML private Circle c3;
    @FXML private Circle c4;
    @FXML private Circle c5;
    @FXML private Circle c6;
    @FXML private Circle c7;
    @FXML private Circle c8;
    @FXML private Circle c9;
    @FXML private Circle c10;
    @FXML private Circle c12;
    @FXML private Circle c11;
    @FXML private Circle c13;
    @FXML private Circle c14;

    @FXML private RadioButton rbtnParamLat;
    @FXML private RadioButton rbtnParamLon;
    @FXML private RadioButton rbtnParamVg;
    @FXML private RadioButton rbtnParamVt;
    @FXML private RadioButton rbtnParamVi;
    @FXML private RadioButton rbtnParamR;
    @FXML private RadioButton rbtnParamP;
    @FXML private RadioButton rbtnParamH;
    @FXML private RadioButton rbtnParamA;

    @FXML private ListView<Double> listViewParamLat;
    @FXML private ListView<Double> listViewParamLon;
    @FXML private ListView<Double> listViewParamVg;
    @FXML private ListView<Double> listViewParamVt;
    @FXML private ListView<Double> listViewParamVi;
    @FXML private ListView<Double> listViewParamR;
    @FXML private ListView<Double> listViewParamP;
    @FXML private ListView<Double> listViewParamH;
    @FXML private ListView<Double> listViewParamA;

    private ObservableList<Double> observableListParamLat;
    private ObservableList<Double> observableListParamLon;
    private ObservableList<Double> observableListParamVg;
    private ObservableList<Double> observableListParamVt;
    private ObservableList<Double> observableListParamVi;
    private ObservableList<Double> observableListParamR;
    private ObservableList<Double> observableListParamP;
    private ObservableList<Double> observableListParamH;
    private ObservableList<Double> observableListParamA;

    private Circle[] circles;

    private ServerSocket server;
    private Socket flightgear;
    private BufferedReader in;

    private String line;

    private Double paramT;
    private Double paramA;
    private Double paramVg;
    private Double paramVt;
    private Double paramVi;
    private Double paramLon;
    private Double paramLat;
    private Double paramH;
    private Double paramP;
    private Double paramR;

    private ExecutorService es;

    private Graph graphParamLat;
    private Graph graphParamLon;
    private Graph graphParamVg;
    private Graph graphParamVt;
    private Graph graphParamVi;
    private Graph graphParamR;
    private Graph graphParamP;
    private Graph graphParamH;
    private Graph graphParamA;

    private int time = 0;

    private int scrollEndId = 1;

    @FXML
    private void initialize(){

        observableListParamLat = FXCollections.observableArrayList();
        observableListParamLon = FXCollections.observableArrayList();
        observableListParamVg = FXCollections.observableArrayList();
        observableListParamVt = FXCollections.observableArrayList();
        observableListParamVi = FXCollections.observableArrayList();
        observableListParamR = FXCollections.observableArrayList();
        observableListParamP = FXCollections.observableArrayList();
        observableListParamH = FXCollections.observableArrayList();
        observableListParamA = FXCollections.observableArrayList();

        listViewParamLat.setItems(observableListParamLat);
        listViewParamLon.setItems(observableListParamLon);
        listViewParamVg.setItems(observableListParamVg);
        listViewParamVt.setItems(observableListParamVt);
        listViewParamVi.setItems(observableListParamVi);
        listViewParamR.setItems(observableListParamR);
        listViewParamP.setItems(observableListParamP);
        listViewParamH.setItems(observableListParamH);
        listViewParamA.setItems(observableListParamA);

        circles = new Circle[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14};

        graphParamLat = new Graph("Місцезнаходження", "Широта");
        graphParamLon = new Graph("Місцезнаходження", "Довгота");
        graphParamVg = new Graph("Швидкість", "Шляхова швидкість");
        graphParamVt = new Graph("Швидкість", "Істинна швидкість");
        graphParamVi = new Graph("Швидкість", "Індикаторна швидкість");
        graphParamR = new Graph("Положення у просторі", "Кут крену (град)");
        graphParamP = new Graph("Положення в просторі", "Кут тангажу (град)");
        graphParamH = new Graph("Положення в просторі", "Кут курсу (град)");
        graphParamA = new Graph("Висота", "Абсолютна висота");

        es = Executors.newSingleThreadExecutor();
    }

    private Runnable flight(){
        Runnable flightSteam = (() -> {
            try {
                server = new ServerSocket(5500);
                flightgear = server.accept();
                Platform.runLater(() -> {
                    lblStatus.setText("Статус: ВСТАНОВЛЮЄТЬСЯ З'ЄДНАННЯ");
                    progressBar.setProgress(-1);
                });
                in = new BufferedReader(new InputStreamReader(flightgear.getInputStream()));
                if(in.readLine() != null){
                    Platform.runLater(() -> {
                        lblStatus.setText("Cтатус: З'ЄДНАННЯ ВСТАНОВЛЕНО");
                        lblStatus.setVisible(false);
                        progressBar.setProgress(0);
                    });
                    animationCircles();
                }
                while ((line = in.readLine()) != null) {
                    paramT = Double.parseDouble(line.substring(line.indexOf("T") + 1, line.lastIndexOf("A") - 1));
                    paramA = Double.parseDouble(line.substring(line.indexOf("A") + 1, line.lastIndexOf("Vg") - 1));
                    paramVg = Double.parseDouble(line.substring(line.indexOf("Vg") + 2, line.lastIndexOf("Vt") - 1));
                    paramVt = Double.parseDouble(line.substring(line.indexOf("Vt") + 2, line.lastIndexOf("Vi") - 1));
                    paramVi = Double.parseDouble(line.substring(line.indexOf("Vi") + 2, line.lastIndexOf("Lon") - 1));
                    paramLon = Double.parseDouble(line.substring(line.indexOf("Lon") + 3, line.lastIndexOf("Lat") - 1));
                    paramLat = Double.parseDouble(line.substring(line.indexOf("Lat") + 3, line.lastIndexOf("H") - 1));
                    paramH = Double.parseDouble(line.substring(line.indexOf("H") + 1, line.lastIndexOf("P") - 1));
                    paramP = Double.parseDouble(line.substring(line.indexOf("P") + 1, line.lastIndexOf("R") - 1));
                    paramR = Double.parseDouble(line.substring(line.indexOf("R") + 1, line.length()));
                    if (paramT > 1.0) {
                        Platform.runLater(() -> {
                            observableListParamLat.add(paramLat);
                            observableListParamLon.add(paramLon);
                            observableListParamVg.add(paramVg);
                            observableListParamVt.add(paramVt);
                            observableListParamVi.add(paramVi);
                            observableListParamR.add(paramR);
                            observableListParamP.add(paramP);
                            observableListParamH.add(paramH);
                            observableListParamA.add(paramA);
                            listViewParamLat.refresh();
                            listViewParamLon.refresh();
                            listViewParamVg.refresh();
                            listViewParamVt.refresh();
                            listViewParamVi.refresh();
                            listViewParamR.refresh();
                            listViewParamP.refresh();
                            listViewParamH.refresh();
                            listViewParamA.refresh();
                            listViewParamLat.scrollTo(scrollEndId);
                            listViewParamLon.scrollTo(scrollEndId);
                            listViewParamVg.scrollTo(scrollEndId);
                            listViewParamVt.scrollTo(scrollEndId);
                            listViewParamVi.scrollTo(scrollEndId);
                            listViewParamR.scrollTo(scrollEndId);
                            listViewParamP.scrollTo(scrollEndId);
                            listViewParamH.scrollTo(scrollEndId);
                            listViewParamA.scrollTo(scrollEndId);
                            scrollEndId++;
                        });
                        createGraph(graphParamLat, paramLat);
                        createGraph(graphParamLon, paramLon);
                        createGraph(graphParamVg, paramVg);
                        createGraph(graphParamVt, paramVt);
                        createGraph(graphParamVi, paramVi);
                        createGraph(graphParamR, paramR);
                        createGraph(graphParamP, paramP);
                        createGraph(graphParamH, paramH);
                        createGraph(graphParamA, paramA);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return flightSteam;
    }

    private void start(){
        es.execute(flight());
    }

    @FXML
    public void rbtnParamLatOnAction() {
        if(rbtnParamLat.isSelected()){
            graphParamLat.getWindow().setVisible(true);
        } else {
            graphParamLat.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamLonOnAction(){
        if(rbtnParamLon.isSelected()){
            graphParamLon.getWindow().setVisible(true);
        } else {
            graphParamLon.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamVgOnAction(){
        if(rbtnParamVg.isSelected()){
            graphParamVg.getWindow().setVisible(true);
        } else {
            graphParamVg.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamVtOnAction(){
        if(rbtnParamVt.isSelected()){
            graphParamVt.getWindow().setVisible(true);
        } else {
            graphParamVt.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamViOnAction(){
        if(rbtnParamVi.isSelected()){
            graphParamVi.getWindow().setVisible(true);
        } else {
            graphParamVi.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamROnAction(){
        if(rbtnParamR.isSelected()){
            graphParamR.getWindow().setVisible(true);
        } else {
            graphParamR.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamPOnAction(){
        if(rbtnParamP.isSelected()){
            graphParamP.getWindow().setVisible(true);
        } else {
            graphParamP.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamHOnAction(){
        if(rbtnParamH.isSelected()){
            graphParamH.getWindow().setVisible(true);
        } else {
            graphParamH.getWindow().setVisible(false);
        }
    }

    @FXML
    public void rbtnParamAOnAction(){
        if(rbtnParamA.isSelected()){
            graphParamA.getWindow().setVisible(true);
        } else {
            graphParamA.getWindow().setVisible(false);
        }
    }

    @FXML
    public void menuItemBeginOnAction(){
        start();
    }

    @FXML
    public void menuItemStopOnAction(){
        if(!es.isTerminated()){
            try {
                es.awaitTermination(1, TimeUnit.SECONDS);
                es.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void menuItemExitOnAction(){
        menuItemStopOnAction();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void menuItemAboutOnAction(){
        showFrameAbout();
    }

    @FXML
    public void menuItemInformationOnAction(){
        showAlertHelp();
    }

    private void animationCircles(){
        FillTransition[] fillTransitions = new FillTransition[14];
        int k = 0;
        for(int i = 0; i < 14 ; i++){
            fillTransitions[i] = new FillTransition(Duration.seconds(7), circles[i], Color.valueOf("#ffffff"), Color.web("#ffffff",00));
            fillTransitions[i].setCycleCount(Animation.INDEFINITE);
            fillTransitions[i].setDelay(Duration.seconds(k++));
            fillTransitions[i].play();
        }
    }

    private void createGraph(Graph graph, Double param){
        graph.getSeries().add(time++, param);
        graph.getWindow().repaint();
    }

    private void showFrameAbout(){
        JFrame frameAbout = new JFrame();
        frameAbout.setTitle("About");
        frameAbout.setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(new File(System.getProperty("user.dir") +
                File.separator + "resources" +
                File.separator + "GUI" +
                File.separator + "images" +
                File.separator + "background.jpg").toString()));
        frameAbout.add(background);
        background.setLayout(new FlowLayout());
        frameAbout.setSize(400, 520);
        frameAbout.setResizable(false);
        frameAbout.setLocationRelativeTo(null);
        frameAbout.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frameAbout.setVisible(false);
            }
        });
        JTextArea textAbout = new JTextArea(
                "Virtual Black Box – це спеціальна програма, яка \n" +
                "реалізує новий спосіб збереження важливої \n" +
                "інформації з літака, яка називається авігаційними \n" +
                "даними. Бортовий самописець також відомий, як \n" +
                "чорний ящик - це електронний реєстраційний \n" +
                "пристрій, розміщене на літаку для полегшення \n" +
                "розслідування льотних подій (аварій, інцидентів). \n" +
                "Програма імітує два процеси:\n" +
                "1-й - запис даних польоту до хмарного сховища. \n" +
                "Параметри польоту походять з Flight Gear \n" +
                "(авіаційний симулятор) та записуються в базу даних.\n" +
                "2-й - зчитування параметрів з хмарної бази даних. \n" +
                "Ці параметри відображаються в списках, також \n" +
                "будуються графіки для кожного з них. \n" +
                "Всі процеси відбуваються в режимі реального часу.\n");
        textAbout.setForeground(java.awt.Color.WHITE);
        textAbout.setFont(textAbout.getFont().deriveFont(14f));
        textAbout.setOpaque(false);
        textAbout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        background.add(new JLabel(new ImageIcon(new ImageIcon(new File(System.getProperty("user.dir") +
                File.separator + "resources" +
                File.separator + "GUI" +
                File.separator + "images" +
                File.separator + "server.png").toString()).getImage().
                getScaledInstance(100, 100, Image.SCALE_DEFAULT))));
        JButton button = new JButton("Oк");
        button.setFont(button.getFont().deriveFont(14f));
        button.setPreferredSize(new Dimension(100, 30));
        background.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameAbout.setVisible(false);
            }
        });
        background.add(textAbout);
        background.add(button);
        frameAbout.setVisible(true);
    }

    private void showAlertHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Virtual Black Box");
        alert.setContentText(
                "1. ввімкніть FlightGear\n" +
                "2. в налаштуваннях виберіть протокол my_protocol_20_05.xml\n" +
                "3. виберіть порт з’єднання 5500\n" +
                "4. ввімкніть старт програми \n" +
                "(Файл -> Старт)\n ");
        alert.show();
    }

}
