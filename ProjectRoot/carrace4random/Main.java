package carrace4random;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Main extends Application {

    static Rectangle[] rect = new Rectangle[2];
    FileWriter fw;
    BufferedWriter bw;
    static ImageView[] carImages = new ImageView[2];
    static Car Car1 = new Car();
    static int c;
    int counter1 = 0;
    int counter2 = 0;
    ChoiceBox<String> cb;
    ImageView carIm;
    double px;
    double py;
    double pAngle;
    Thread t;
    static String name;//should be passed from command line argument
    static String OName;
    static int clientPort;//should be passed from command line argument
    static String serverIP;//should be passed from command line argument
    static String via;
    static Client2 client2;
    private ScaleTransition scaleTransition;
    // static DatagramSocket socketReceiver;
    //   DatagramSocket socketReceiver1;
//   static DatagramSocket socketSender;
    //  DatagramSocket socketSender1;
    double prevAngle;
    double prevAngle1;
    InetAddress IPAddress;
    int var;
    int var1;
    int startTimer = 1;
    private static final Integer STARTTIME = 30;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    ImageView collision;

    int flag1 = -1;
    int counter4 = 0;
    int counter5 = 0;
    int passedCorner1 = 0;
    int passedCorner2 = 0;
    int passedCorner3 = 0;
    int passedCorner4 = 0;
    int passedCorner1again = -1;
    int OpassedCorner1 = 0;
    int OpassedCorner2 = 0;
    int OpassedCorner3 = 0;
    int OpassedCorner4 = 0;
    int OpassedCorner1again = -1;
    double nitPX;
    double nitPY;
    int iWin = 0;
    int oWin = 0;
    int showOnce = 0;
    Label warning;
    int showTimeUp = 0;
    public static  String [] arguments ;
    ImageView nitros;
    InputStream in = null;
    AudioStream audioStream = null;
    static int once=0;
    public Main() throws SocketException, UnknownHostException, IOException {

      this.serverIP = arguments[0];
  //   this.serverIP = "127.0.0.1";
        this.via = "cse";
        Car1.SetInitialDirection(0.0, 1.0);
        rect[0] = new Rectangle(45, 500, 70, 50);
        rect[1] = new Rectangle(125, 500, 70, 50);

//        name = "zahin";
//        clientPort = 12346;
//        OName = "q";

        prevAngle = 0.0;
        prevAngle1 = 0.0;
//
//       var=0;
//       var1=1;
////
//       try {
//            client2 = new Client2(Main.name, Main.clientPort, Main.serverIP, Main.via);
//            IPAddress = InetAddress.getByName(Main.serverIP);
////                    Main.socketReceiver = new DatagramSocket(Main.clientPort);
////                    Main.socketSender = new DatagramSocket();
//
//        } catch (SocketException ex) {
//        } catch (UnknownHostException ex) {
//        }
    }

    public void playSound(long time){
            
            AudioPlayer.player.start(audioStream);
    }
    public void update(long time, Stage stage) {
        counter1++;
        
        //  System.out.println("in update");
        if ((counter1 % 50) == 0) {
            px = Main.Car1.posX;
            py = Main.Car1.posY;
            pAngle = Main.Car1.angle;
            //   System.out.println("in update: px: "+px +"py: "+ py + "angle: "+ pAngle);
        }
        //  timeline.
        Duration d = timeline.getCurrentTime();
        //    System.out.println(d.toSeconds());
        if (d.toSeconds() > 20 && d.toSeconds() < 30) {
            warning.setText("Time's running out!!!!");
            //  playSound();
        }
        Main.Car1.Move();
//
        if ((Main.Car1.posX < 20 && -Main.Car1.posY > 30 && -Main.Car1.posY < 650)
                || (Main.Car1.posX > 190 && Main.Car1.posX < 890 && -Main.Car1.posY > 180 && -Main.Car1.posY < 500)
                || (Main.Car1.posX > 1060 && -Main.Car1.posY > 30 && -Main.Car1.posY < 650)
                || (-Main.Car1.posY < 30 && Main.Car1.posX > 20 && Main.Car1.posX < 1060)
                || (-Main.Car1.posY > 650 && Main.Car1.posX > 20 && Main.Car1.posX < 1060)) {
           if(counter1%2==0){
                Main.Car1.DecreaseSpeed();
             
           } 
        }
        collision.setVisible(false);
        double nx = Main.Car1.posX;
//                 
        double ny = Main.Car1.posY;
        double angle1 = Main.Car1.angle;
        double speed1 = Main.Car1.speed;

        String str = nx + "#" + ny + "#" + angle1 + "#" + speed1;
        //   System.out.println("in sender thread:"+str);
        String fullMsg = "Via: " + via + '\n' + "To: " + OName + '\n' + "From: " + name + '\n' + "Body: " + str;
       //   System.out.println("in sender thread:" + fullMsg);

        byte[] data5 = fullMsg.getBytes();
        DatagramPacket sendPacket5 = new DatagramPacket(data5, data5.length, IPAddress, 4545);
        try {
            Client2.socketSender.send(sendPacket5);
        } catch (IOException ex) {
        }
        if (Main.Car1.speed > 0 && Main.Car1.angle == prevAngle) {
            rect[var].setX(Main.Car1.posX);
            rect[var].setY(-Main.Car1.posY);
            carImages[var].setX(Main.Car1.posX);
            carImages[var].setY(-Main.Car1.posY);

        } else if (Main.Car1.speed > 0 && Main.Car1.angle != prevAngle) {
      //      rect[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
            rect[var].setX(Main.Car1.posX);
            rect[var].setY(-Main.Car1.posY);
       //     carImages[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
            carImages[var].setX(Main.Car1.posX);
            carImages[var].setY(-Main.Car1.posY);
            prevAngle = Main.Car1.angle;

        }
        if (rect[var].getY() < 335 && rect[var].getX() > 40 && rect[var].getX() < 190 && passedCorner1 == 0) {
            passedCorner1 = 1;
            passedCorner1again = 0;
            nitros.setVisible(true);
            nitPX=rect[var].getX()+30;
            nitPY=rect[var].getY()-30;
            nitros.setX(rect[var].getX()+30);
            nitros.setY(rect[var].getY()-30);
        }
        if(rect[var].getX() > nitPX && rect[var].getY() < nitPY && passedCorner1 == 1){
             nitros.setVisible(false);
            
          //  System.out.println("boosted");
            Main.Car1.IncreaseSpeed();
            if(counter1%8==0){
            Main.Car1.IncreaseSpeed();
            }
             
        }
        if (rect[var].getX() > 540 && rect[var].getY() > 30 && rect[var].getY() < 180 && passedCorner1 == 1) {
            passedCorner2 = 1;
            passedCorner1again = 1;
            nitros.setVisible(true);
            nitPX=rect[var].getX()+30;
            nitPY=rect[var].getY()-30;
            nitros.setX(rect[var].getX()+30);
            nitros.setY(rect[var].getY()-30);
        }
         if(rect[var].getX() > nitPX && rect[var].getY() < nitPY && passedCorner2 == 1){
             nitros.setVisible(false);
             
          //  System.out.println("boosted");
            if(counter1%8==0){
            Main.Car1.IncreaseSpeed();
            }
             
        }
        if (rect[var].getY() > 335 && rect[var].getX() > 890 && rect[var].getX() < 1040 && passedCorner2 == 1) {
            passedCorner3 = 1;
            passedCorner1again = 2;
              nitros.setVisible(true);
            nitPX=rect[var].getX()+30;
            nitPY=rect[var].getY()-30;
            nitros.setX(rect[var].getX()+30);
            nitros.setY(rect[var].getY()-30);
//            //   System.out.println(rect[var].getX());
//            System.out.println("third corner");
//            System.out.println("1 again: " + passedCorner1again);
        }
         if(rect[var].getX() > nitPX && rect[var].getY() < nitPY && passedCorner3 == 1){
             nitros.setVisible(false);
             if(counter1%8==0){
         //   System.out.println("boosted");
              Main.Car1.IncreaseSpeed();
             }
        }
        if (rect[var].getX() < 540 && rect[var].getY() > 500 && rect[var].getY() < 650 && passedCorner3 == 1) {
            passedCorner4 = 1;
            passedCorner1again = 3;
              nitros.setVisible(true);
            nitPX=rect[var].getX()+30;
            nitPY=rect[var].getY()-30;
            nitros.setX(rect[var].getX()+30);
            nitros.setY(rect[var].getY()-30);
//            System.out.println("fourth corner");
//            System.out.println("1 again: " + passedCorner1again);
        }
         if(rect[var].getX() > nitPX && rect[var].getY() < nitPY && passedCorner2 == 1){
             nitros.setVisible(false);
         
            if(counter1%8==0){
            Main.Car1.IncreaseSpeed();
            }
               
        }
        if (rect[var].getY() < 335 && rect[var].getX() > 40 && rect[var].getX() < 190 && passedCorner1again == 3 && passedCorner4 == 1) {
            passedCorner1again = 4;
         //   System.out.println("first corner again");
          //  System.out.println("1 again: " + passedCorner1again);
        }
        if (passedCorner1 == 1 && passedCorner2 == 1 && passedCorner3 == 1 && passedCorner4 == 1 && passedCorner1again == 4) {
            iWin = 1;
            //   System.out.println(iWin);
        }
        if (d.toSeconds() == 30 ){
             long l =   System.currentTimeMillis();
   System.out.println("ends at"+l);
        }
        if (d.toSeconds() > 30 && iWin == 0 && showTimeUp == 0) {

            stage.close();
                  long l =   System.currentTimeMillis();
   System.out.println("ends at"+l);
            showTimeUp = 1;
            String str1 = "time up";
            String fullMsg1 = "Via: " + via + '\n' + "To: " + OName + '\n' + "From: " + name + '\n' + "Body: " + str1;
         

            byte[] data6 = fullMsg1.getBytes();
            DatagramPacket sendPacket6 = new DatagramPacket(data6, data6.length, IPAddress, 4545);
            try {
                Client2.socketSender.send(sendPacket6);
            } catch (IOException ex) {
            }
            warning.setText("Your time is up!! you couldn't finish!!!!");
            
            Stage dialogStage = new Stage();
            VBox vbox = new VBox();
            vbox.setId("pane6");

            Scene newscene = new Scene(vbox, 2000, 2000);
       newscene.getStylesheets().addAll(this.getClass().getResource("style6.css").toExternalForm());
            dialogStage.setScene(newscene);
            dialogStage.show();
             String str4 = "CLOSE";
            String fullMsg4 = "Via: " + via + '\n' + "To: " + "anonymous" + '\n' + "From: " + name + '\n' + "Body: " + str4;
         

            byte[] data4 = fullMsg4.getBytes();
            DatagramPacket sendPacket4 = new DatagramPacket(data4, data4.length, IPAddress, 4545);
            try {
                Client2.socketSender.send(sendPacket4);
            } catch (IOException exce) {
            }
             Client2.socketSender.close();
             Client2.socketReceiver.close();
        }
        if (passedCorner1 == 1 && passedCorner2 == 1 && passedCorner3 == 1 && passedCorner4 == 1 && passedCorner1again == 4 && iWin == 1 && showOnce == 0) {
            showOnce = 1;
            double p = d.toSeconds() * Main.Car1.speed;
            int score = (int) p;

            //System.out.println(p);
           
              stage.close();
          
            Stage dialogStage = new Stage();

            Pane root2 = new Pane();
            root2.setId("pane2");
            VBox vbox = new VBox(20);
            vbox.setAlignment(Pos.CENTER);
            vbox.setId("pane2");
            Label scoreL = new Label(Integer.toString(score));
            
            vbox.getChildren().addAll(scoreL);
            vbox.setLayoutY(500);
            vbox.setLayoutX(100);
            root2.getChildren().add(vbox);
            Scene newscene = new Scene(root2, 2000, 2000);
       newscene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
            dialogStage.setScene(newscene);
            dialogStage.show();
             String str4 = "CLOSE";
            String fullMsg4 = "Via: " + via + '\n' + "To: " + "anonymous" + '\n' + "From: " + name + '\n' + "Body: " + str4;
         

            byte[] data4 = fullMsg4.getBytes();
            DatagramPacket sendPacket4 = new DatagramPacket(data4, data4.length, IPAddress, 4545);
            try {
                Client2.socketSender.send(sendPacket4);
            } catch (IOException exce) {
            }
             Client2.socketSender.close();
 Client2.socketReceiver.close();
        }

    }

    public void update1(long time, Stage stage) throws IOException {

        counter2++;
        DatagramPacket packR3 = new DatagramPacket(new byte[1024], 1024);
        
        Client2.socketReceiver.receive(packR3);
        int length = packR3.getLength();
        String response = new String(packR3.getData());
        String[] strArray = new String[4];
        //   System.out.println("in receiver thread: " + response);
        String[] lines = response.split("\n");
        strArray[0] = lines[0].split(":")[1];
        strArray[1] = lines[1].split(":")[1];
        strArray[2] = lines[2].split(":")[1];
        strArray[3] = lines[3].split(":")[1];
        if (strArray[3].trim().equals("time up") && showTimeUp == 0) {
            System.out.println("your opponent's time is up!!!");
            showTimeUp = 1;
            stage.close();
            Stage dialogStage = new Stage();
            VBox vbox = new VBox();
            vbox.setId("pane7");

            Scene newscene = new Scene(vbox, 2000, 2000);
       newscene.getStylesheets().addAll(this.getClass().getResource("style7.css").toExternalForm());
            dialogStage.setScene(newscene);
            dialogStage.show();
             String str4 = "CLOSE";
            String fullMsg4 = "Via: " + via + '\n' + "To: " + "anonymous" + '\n' + "From: " + name + '\n' + "Body: " + str4;
         

            byte[] data4 = fullMsg4.getBytes();
            DatagramPacket sendPacket4 = new DatagramPacket(data4, data4.length, IPAddress, 4545);
            try {
                Client2.socketSender.send(sendPacket4);
            } catch (IOException exce) {
            }
             Client2.socketSender.close();
 Client2.socketReceiver.close();
        } else {
            StringTokenizer st = new StringTokenizer(strArray[3], "#");
            double positionX = Double.parseDouble(st.nextToken());
            double positionY = Double.parseDouble(st.nextToken());
            double angle1 = Double.parseDouble(st.nextToken());
            double speed1 = Double.parseDouble(st.nextToken());

            if (speed1 > 0 && angle1 == prevAngle1) {
                Main.rect[var1].setX(positionX);
                Main.rect[var1].setY(-positionY);
                carImages[var1].setX(positionX);
                carImages[var1].setY(-positionY);
            } else if (speed1 > 0 && angle1 != prevAngle1) {
                Main.rect[var1].setRotate(-angle1 * 180.0 / 3.1416);
                Main.rect[var1].setX(positionX);
                Main.rect[var1].setY(-positionY);
                carImages[var1].setRotate(-angle1 * 180.0 / 3.1416);
                carImages[var1].setX(positionX);
                carImages[var1].setY(-positionY);
                prevAngle1 = angle1;
            }
            if (rect[var1].getY() < 335 && rect[var1].getX() > 40 && rect[var1].getX() < 190 && OpassedCorner1 == 0) {
                OpassedCorner1 = 1;
                OpassedCorner1again = 0;
             //   System.out.println("first corner oppo");
            }

            if (rect[var1].getX() > 540 && rect[var1].getY() > 30 && rect[var1].getY() < 180 && OpassedCorner1 == 1) {
                OpassedCorner2 = 1;
                OpassedCorner1again = 1;
              //  System.out.println("second corner  oppo");
            }
            if (rect[var1].getY() > 335 && rect[var1].getX() > 890 && rect[var1].getX() < 1040 && OpassedCorner2 == 1) {
                OpassedCorner3 = 1;
                OpassedCorner1again = 2;
            //    System.out.println("third corner  oppo");
            }
            if (rect[var1].getX() < 540 && rect[var1].getY() > 500 && rect[var1].getY() < 650 && OpassedCorner3 == 1) {
                OpassedCorner4 = 1;
                OpassedCorner1again = 3;
             //   System.out.println("fourth corner  oppo");
            }
            if (rect[var1].getY() < 335 && rect[var1].getX() > 40 && rect[var1].getX() < 190 && OpassedCorner4 == 1 && OpassedCorner1again == 3) {
                OpassedCorner1again = 4;
             //   System.out.println("first  again oppo");
            }
            if (OpassedCorner1 == 1 && OpassedCorner2 == 1 && OpassedCorner3 == 1 && OpassedCorner4 == 1 && OpassedCorner1again == 4) {
                oWin = 1;
               // System.out.println("oppo wins");
            }
            if (OpassedCorner1 == 1 && OpassedCorner2 == 1 && OpassedCorner3 == 1 && OpassedCorner4 == 1 && OpassedCorner1again == 4 && oWin == 1 && showOnce == 0) {
                showOnce = 1;
                Stage dialogStage = new Stage();
                stage.close();
                VBox vbox = new VBox();//ekhan theke start hocchee
                vbox.setId("pane3");

                Scene newscene = new Scene(vbox, 2000, 2000);
       newscene.getStylesheets().addAll(this.getClass().getResource("style3.css").toExternalForm());
                dialogStage.setScene(newscene);
                dialogStage.show();    
                String str4 = "CLOSE";
            String fullMsg4 = "Via: " + via + '\n' + "To: " + "anonymous" + '\n' + "From: " + name + '\n' + "Body: " + str4;
         

            byte[] data4 = fullMsg4.getBytes();
            DatagramPacket sendPacket4 = new DatagramPacket(data4, data4.length, IPAddress, 4545);
            try {
                Client2.socketSender.send(sendPacket4);
            } catch (IOException exce) {
            }
             Client2.socketSender.close();
 Client2.socketReceiver.close();
                

            }
        }
    }

    public void update2(long time) {
        if (rect[var].getBoundsInParent().intersects(rect[var1].getBoundsInParent())) {
            Shape intersect = Shape.intersect(Main.rect[var], Main.rect[var1]);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                collision.setX(rect[var].getX());
                collision.setY(rect[var].getY());

                collision.setVisible(true);


            }
        }
        if (rect[var].getBoundsInParent().intersects(rect[var1].getBoundsInParent())
                || rect[var].getX() < 0 || rect[var].getX() > 1010
                || rect[var].getY() < 0 || rect[var].getY() > 610
                || (rect[var].getX() > 500 && rect[var].getX() < 540 && rect[var].getY() > 40 && rect[var].getY() < 100)
                || (rect[var].getX() > 500 && rect[var].getX() < 540 && rect[var].getY() > 580 && rect[var].getY() < 640)
                || (rect[var].getY() > 295 && rect[var].getY() < 335 && rect[var].getX() > 900 && rect[var].getX() < 960)) {
            Shape intersect = Shape.intersect(Main.rect[var], Main.rect[var1]);
            if ((intersect.getBoundsInLocal().getWidth() != -1)
                    || rect[var].getX() < 0 || rect[var].getX() > 1010
                    || rect[var].getY() < 0 || rect[var].getY() > 610
                    || (rect[var].getX() > 500 && rect[var].getX() < 540 && rect[var].getY() > 40 && rect[var].getY() < 100)
                    || (rect[var].getX() > 500 && rect[var].getX() < 540 && rect[var].getY() > 580 && rect[var].getY() < 640)
                    || (rect[var].getY() > 295 && rect[var].getY() < 335 && rect[var].getX() > 900 && rect[var].getX() < 960)) {
                rect[var].setX(px);
                rect[var].setY(-py);
                carImages[var].setX(px);
                carImages[var].setY(-py);
                //     System.out.println("update 2:  posX: " + px + "posY: " + py + "angle: " + pAngle);
                //  Main.Car1.DecreaseSpeed();
                String str1 = rect[var].getX() + "#" + (-rect[var].getY()) + "#" + Main.Car1.angle + "#" + Main.Car1.speed;
                Main.Car1.posX = rect[var].getX();
                Main.Car1.posY = (-rect[var].getY());
                px = rect[var].getX();
                py = (-rect[var].getY());
                //    System.out.println("in sender thread:"+str);
                String fullMsg1 = "Via: " + via + '\n' + "To: " + OName + '\n' + "From: " + name + '\n' + "Body: " + str1;
                byte[] data1 = fullMsg1.getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(data1, data1.length, IPAddress, 4545);
                try {
                    Client2.socketSender.send(sendPacket1);
                } catch (IOException ex) {
                }

            }
        }
    }

    public void update3(long time) throws IOException {
        if (rect[var1].getBoundsInParent().intersects(rect[var].getBoundsInParent())
                || (rect[var1].getX() < 0 || rect[var1].getX() > 1010
                || rect[var1].getY() < 0 || rect[var1].getY() > 610
                || (rect[var1].getX() > 500 && rect[var1].getX() < 540 && rect[var1].getY() > 40 && rect[var1].getY() < 100)
                || (rect[var1].getX() > 500 && rect[var1].getX() < 540 && rect[var1].getY() > 580 && rect[var1].getY() < 640)
                || (rect[var1].getY() > 295 && rect[var1].getY() < 335 && rect[var1].getX() > 900 && rect[var1].getX() < 960))) {
            Shape intersect = Shape.intersect(Main.rect[var1], Main.rect[var]);
            if (intersect.getBoundsInLocal().getWidth() != -1
                    || (rect[var1].getX() < 0 || rect[var1].getX() > 1010
                    || rect[var1].getY() < 0 || rect[var1].getY() > 610
                    || (rect[var1].getX() > 500 && rect[var1].getX() < 540 && rect[var1].getY() > 40 && rect[var1].getY() < 100)
                    || (rect[var1].getX() > 500 && rect[var1].getX() < 540 && rect[var1].getY() > 580 && rect[var1].getY() < 640)
                    || (rect[var1].getY() > 295 && rect[var1].getY() < 335 && rect[var1].getX() > 900 && rect[var1].getX() < 960))) {
                //  System.out.println("collsion in update3");

                DatagramPacket packR1 = new DatagramPacket(new byte[1024], 1024);
                Client2.socketReceiver.receive(packR1);
                int length1 = packR1.getLength();
                String response1 = new String(packR1.getData());
                String[] strArray1 = new String[4];

                String[] lines1 = response1.split("\n");
                strArray1[0] = lines1[0].split(":")[1];
                strArray1[1] = lines1[1].split(":")[1];
                strArray1[2] = lines1[2].split(":")[1];
                strArray1[3] = lines1[3].split(":")[1];
                StringTokenizer strr = new StringTokenizer(strArray1[3], "#");
                double positionX1 = Double.parseDouble(strr.nextToken());
                double positionY1 = Double.parseDouble(strr.nextToken());

                double angle11 = Double.parseDouble(strr.nextToken());
                double speed11 = Double.parseDouble(strr.nextToken());

                Main.rect[var1].setX(positionX1);
                Main.rect[var1].setY(-positionY1);
                carImages[var1].setX(positionX1);
                carImages[var1].setY(-positionY1);
                prevAngle = angle11;

            }
        }
    }

    @Override
    public void start(Stage stage) {
        Image img = new Image("file:front.jpg");
        ImageView i = new ImageView();
        i.setImage(img);
        i.setFitHeight(600);
        i.setFitWidth(1000);
        i.setX(0);
        i.setY(0);

        Image img1 = new Image("file:title.png");
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));

        Button btnP = new Button("PLAY");
        Button btnI = new Button("INSTRUCTIONS");
        Button btnQ = new Button("QUIT");

        btnP.setMaxWidth(100);
        btnI.setMaxWidth(100);
        btnQ.setMaxWidth(100);

        VBox root = new VBox();//ekhan theke start hocchee
        root.setId("pane");
        root.setSpacing(50);
        root.setPadding(new Insets(100, 10, 1000, 20));
        Button btn = new Button("PLAY");
        Button btn1 = new Button("QUIT");
        Button btn2 = new Button("INSTRUCTIONS");
        btn.setStyle("-fx-base:red;");
        btn1.setStyle("-fx-base:red;");
        btn2.setStyle("-fx-base:red;");
        btn.setMaxSize(100, 100);
        btn1.setMaxSize(100, 100);
        btn2.setMaxSize(100, 100);
        btn.setOnAction(e -> {
            login(stage);

        });
        btn1.setOnAction(e -> {
            System.out.println("quit clicked");
            stage.close();

        });
        btn2.setOnAction(e -> {
            System.out.println("instruction clicked");
            Stage dialogStage = new Stage();
            // dialogStage.initModality(Modality.WINDOW_MODAL);
            Pane root1 = new Pane();
            VBox vbox = new VBox(20);//ekhan theke start hocchee
            // gap between components is 20
            vbox.setAlignment(Pos.CENTER);        // center the components within VBox
            Button ok = new Button("ok");
            ok.setStyle("-fx-base:red;");
            vbox.setPrefWidth(2300);
            vbox.getChildren().addAll(ok);
            vbox.setLayoutY(500);
            root1.setId("pane1");

            root1.getChildren().add(vbox);
            ok.setOnAction(e1 -> {
                dialogStage.close();
            }
            );
            Scene newscene = new Scene(root1, 2000, 2000);
       newscene.getStylesheets().addAll(this.getClass().getResource("style1.css").toExternalForm());
            dialogStage.setScene(newscene);
            dialogStage.show();

        });
        
       
        root.getChildren().addAll(btn, btn1, btn2);
        Scene scene = new Scene(root, 2000, 2000);
        //scene.getStylesheets().addAll("style.css");
       scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
            
        stage.setScene(scene);
        stage.show();
    }

    public void login(Stage stage) {

        GridPane root = new GridPane();
        root.setId("pane4");
        Image log = new Image("file:login.jpg");
        ImageView logIm = new ImageView();
        logIm.setImage(log);

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        cb = new ChoiceBox<>();
        Label Name = new Label("Name");
        Label Port = new Label("Port");
        Label OpposName = new Label("Opposition Name");

        TextField playerName = new TextField();
        TextField playerPort = new TextField();
        TextField oppositionName = new TextField();
        playerName.setPromptText("Enter player's name: ");
        playerPort.setPromptText("Enter player's port: ");
        oppositionName.setPromptText("Enter opposite player's port: ");

        Button login = new Button("Login");
        HBox loginContainer = new HBox();
        //    root.getChildren().add(loginPage);
        loginContainer.getChildren().add(login);
        loginContainer.setAlignment(Pos.CENTER_RIGHT);

        Label warning = new Label();
        root.add(Name, 0, 0);
        root.add(Port, 0, 1);
        root.add(playerName, 1, 0);
        root.add(playerPort, 1, 1);

        root.add(loginContainer, 1, 3);
        login.setOnAction(e -> {
            Main.name = playerName.getText();
            Main.clientPort = Integer.parseInt(playerPort.getText());
            //   OName = oppositionName.getText();

            try {
                client2 = new Client2(Main.name, Main.clientPort, Main.serverIP, Main.via);
                IPAddress = InetAddress.getByName(Main.serverIP);
//                    Main.socketReceiver = new DatagramSocket(Main.clientPort);
//                    Main.socketSender = new DatagramSocket();

            } catch (SocketException ex) {
            } catch (UnknownHostException ex) {
            }

            try {
                //  track(stage);
                flag1 = 1;
                playerSelection(stage);
            } catch (Exception ex) {
            }

        });

        Scene scene = new Scene(root, 400, 400);

        
       scene.getStylesheets().addAll(this.getClass().getResource("style4.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        //   scaleTransition.play();
    }

    public void playerSelection(Stage stage) throws IOException {

        VBox root = new VBox();
        root.setId("pane5");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        Button button = new Button("Click here to start the game");
        Button button2 = new Button("View more players active");
        Label opponentName = new Label();
        // cb = new ChoiceBox<>();
        button2.setOnAction(
                e -> {
                    String identityG = "Via: " + via + '\n' + "To: " + name + '\n' + "From: " + name + '\n' + "Body: " + "Get players info";
                    try {
                        IPAddress = InetAddress.getByName(serverIP);

                        byte[] dataG = identityG.getBytes();
                        DatagramPacket sendingPack1 = new DatagramPacket(dataG, dataG.length, IPAddress, 4545);
                        Client2.socketSender.send(sendingPack1);

                    } catch (Exception exp) {
                    }
                    try {
                        DatagramPacket packR = new DatagramPacket(new byte[1024], 1024);
                      //  Client2.socketReceiver.setSoTimeout(10000); 
                        Client2.socketReceiver.receive(packR);
                        
                        System.out.println("within player selection pack received");
                        int length = packR.getLength();
                        String response = new String(packR.getData());
                        String[] strArray = new String[4];
                        String[] lines = response.split("\n");
                        strArray[0] = lines[0].split(":")[1];
                        strArray[1] = lines[1].split(":")[1];
                        strArray[2] = lines[2].split(":")[1];
                        strArray[3] = lines[3].split(":")[1];
                        System.out.println("in receiver thread" + strArray[3]);
                        
                        StringTokenizer st = new StringTokenizer(strArray[3],"#");
                        String c= strArray[3].trim();
                      
                                
                                OName = c.trim();
                                opponentName.setText(OName);
                                opponentName.setTextFill(Color.WHITE);
                                opponentName.setStyle("-fx-font-size: 4em;");
                                System.out.println("got->"+OName);
                             
            
                    } catch (Exception io) {
                        stage.close();
                    }


                });
        button.setOnAction(
                (ActionEvent e) -> {
                    System.out.println("click to start the game");
                    String carSelection = "Via: " + via + '\n' + "To: " + name + '\n' + "From: " + name + '\n' + "Body: " + "Get car number";
                    try {
                        IPAddress = InetAddress.getByName(serverIP);

                        byte[] dataG = carSelection.getBytes();
                        DatagramPacket sendingPack1 = new DatagramPacket(dataG, dataG.length, IPAddress, 4545);
                        Client2.socketSender.send(sendingPack1);

                    } catch (Exception exp) {
                           stage.close();
                    }
                    flag1 = 0;
                    try {
                        
                        DatagramPacket packR = new DatagramPacket(new byte[1024], 1024);
                    
                        System.out.println(t);
                        Client2.socketReceiver.receive(packR);
                        System.out.println("line 779");
                        
                        System.out.println("within player selection pack received");
                        int length = packR.getLength();
                        String response = new String(packR.getData());
                        String[] strArray = new String[4];
                       
                        String[] lines = response.split("\n");
                        strArray[0] = lines[0].split(":")[1];
                        strArray[1] = lines[1].split(":")[1];
                        strArray[2] = lines[2].split(":")[1];
                        strArray[3] = lines[3].split(":")[1];
                        System.out.println("in receiver thread" + strArray[3]);
                        StringTokenizer st1 = new StringTokenizer(strArray[3].trim(), "#");
                        var = Integer.parseInt(st1.nextToken().trim());

                        var1 = Integer.parseInt(st1.nextToken().trim());
                        System.out.println("var:-" + var + "var1:-" + var1); try {
                        car(stage);
                    } catch (Exception e1) {

                    }
                    } catch (IOException io) {
                        System.out.println( io);
                        stage.close();
                    } 
                   

                });
        root.getChildren().addAll(button2, opponentName, button);

        Scene scene = new Scene(root, 400, 400);
        
       scene.getStylesheets().addAll(this.getClass().getResource("style5.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Player Selection  " + name);
        stage.show();
    }

    public void getChoice(ChoiceBox<String> cb) {
        String c = cb.getValue();
        OName = c.trim();
        System.out.println("got: " + OName);
    }

    public void car(Stage stage) {
        Group root = new Group();
        //  root.setAlignment(Pos.CENTER);
        //  root.setSpacing(10);
        Label carNum = new Label(Integer.toString(var));
        Button button = new Button("Click here to start the game");
        if (var == 0) {
            Image im = new Image("file:BinaryContent\\car-red.png");
            carIm = new ImageView();
            carIm.setImage(im);
            carIm.setFitWidth(400);
            carIm.setFitHeight(285);
        }
        if (var == 1) {
            Image im = new Image("file:BinaryContent\\car-yellow.png");
            carIm = new ImageView();
            carIm.setImage(im);
            carIm.setFitWidth(400);
            carIm.setFitHeight(285);
        }

        button.setOnAction(e -> {
            try {
                track(stage);
            } catch (Exception e1) {

            }

        });

        root.getChildren().addAll(carIm, carNum, button);
        Scene scene = new Scene(root, 400, 285);
        stage.setScene(scene);
        stage.setTitle("Got car  " + name);
        stage.show();
    }

    public void track(Stage stage) throws SocketException, IOException {

        if (var == 0) {
            Car1.SetInitialPosition(45, -500);
            px = 45;
            py = -500;
            pAngle = 3.1416 / 2;
        } else {
            Car1.SetInitialPosition(125, -500);
            px = 125;
            py = -500;
            pAngle = 3.1416 / 2;
        }
        String gongFile = "BinaryContent\\Car_Driving_Sound_Effect_1_converted.wav";
        
        try {
            in = new FileInputStream(gongFile);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        try {
            audioStream = new AudioStream(in);
        } catch (IOException e) {
        }
        
        
        Group root = new Group();
        warning = new Label();
        warning.setTextFill(Color.RED);
        warning.setStyle("-fx-font-size: 2em;");
       
        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.BLUE);
        timerLabel.setStyle("-fx-font-size: 4em;");
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME + 1),
                        new KeyValue(timeSeconds, 0)));

        VBox vb = new VBox(20);             // gap between components is 20
        vb.setAlignment(Pos.CENTER);        // center the components within VBox

        vb.setPrefWidth(2300);
        vb.getChildren().addAll(warning, timerLabel);
        vb.setLayoutY(100);

        String s = null;

        Rectangle mainScreen = new Rectangle(1080, 680);
        mainScreen.setFill(Color.BLACK);
        rect[0].setFill(Color.TRANSPARENT);

        rect[1].setFill(Color.TRANSPARENT);
        Image image = new Image("file:BinaryContent\\car-red.png");
        carImages[0] = new ImageView();
        Image image1 = new Image("file:BinaryContent\\car-yellow.png");
        carImages[1] = new ImageView();
        carImages[1].setImage(image1);
        carImages[0].setImage(image);
        carImages[0].setFitHeight(50);
        carImages[0].setFitWidth(70);
        carImages[1].setFitHeight(50);
        carImages[1].setFitWidth(70);
        carImages[0].setX(45);
        carImages[0].setY(500);
        carImages[1].setX(125);
        carImages[1].setY(500);
        Image coll = new Image("file:BinaryContent\\explosion-1 (1).gif-c200");
        collision = new ImageView();
        collision.setImage(coll);
        collision.setVisible(false);
        collision.setFitHeight(50);
        collision.setFitWidth(50);
        Image nitro = new Image("file:BinaryContent\\banks_nos_trans3.gif");
        nitros = new ImageView();
        nitros.setImage(nitro);
        nitros.setVisible(false);
        nitros.setFitHeight(50);
        nitros.setFitWidth(50);
        
        Image audi = new Image("file:BinaryContent\\audience.jpg");
        ImageView audience = new ImageView();
        audience.setImage(audi);
        audience.setFitHeight(680);
        audience.setFitWidth(1080);
      
        Line l1 = new Line(235, 105, 845, 105);
        Line l2 = new Line(965, 225, 965, 445);
        Line l3 = new Line(235, 575, 845, 575);
        Line l4 = new Line(115, 225, 115, 445);
        Line corner1 = new Line(115, 105, 155, 145);
        Line corner2 = new Line(115, 575, 155, 525);
        Line corner3 = new Line(965, 105, 925, 145);
        Line corner4 = new Line(965, 575, 925, 525);
        Line hinderance1 = new Line(540, 40, 540, 100);
        Line hinderance2 = new Line(960, 335, 900, 335);
        Line hinderance3 = new Line(540, 640, 540, 580);
        Line finishingLine = new Line(230, 510, 230, 640);
        hinderance1.setStroke(Color.SADDLEBROWN);
        hinderance1.setStrokeWidth(20);
        hinderance2.setStroke(Color.SADDLEBROWN);
        hinderance2.setStrokeWidth(20);
        hinderance3.setStroke(Color.SADDLEBROWN);
        hinderance3.setStrokeWidth(20);

        finishingLine.setStroke(Color.FIREBRICK);
        finishingLine.setStrokeWidth(20);
        Line finishingLine1 = new Line(230, 505, 230, 645);
        finishingLine1.getStrokeDashArray().addAll(25d, 20d, 5d, 20d);
        l1.setStroke(Color.WHITE);
        l1.setStrokeWidth(150);
        l2.setStroke(Color.WHITE);
        l2.setStrokeWidth(150);
        l3.setStroke(Color.WHITE);
        l3.setStrokeWidth(150);
        l4.setStroke(Color.WHITE);
        l4.setStrokeWidth(150);
        corner1.setStroke(Color.FIREBRICK);
        corner1.setStrokeWidth(20);
        corner2.setStrokeWidth(20);
        corner3.setStrokeWidth(20);
        corner4.setStrokeWidth(20);
        QuadCurve quad1 = new QuadCurve();
        quad1.setStartX(155);
        quad1.setStartY(105);
        quad1.setEndX(115);
        quad1.setEndY(145);
        quad1.setControlX(115);
        quad1.setControlY(105);
        quad1.setStroke(Color.LIGHTSLATEGRAY);
        quad1.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));   //
        quad1.setStrokeWidth(150);
        quad1.setStrokeLineCap(StrokeLineCap.SQUARE);

        QuadCurve quad2 = new QuadCurve();
        quad2.setStartX(925);
        quad2.setStartY(105);
        quad2.setEndX(965);
        quad2.setEndY(145);
        quad2.setControlX(965);
        quad2.setControlY(105);
        quad2.setStroke(Color.LIGHTSLATEGRAY);
        quad2.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
        quad2.setStrokeWidth(150);
        quad2.setStrokeLineCap(StrokeLineCap.SQUARE);
        QuadCurve quad3 = new QuadCurve();
        quad3.setStartX(965);
        quad3.setStartY(525);
        quad3.setEndX(925);
        quad3.setEndY(575);
        quad3.setControlX(965);
        quad3.setControlY(575);
        quad3.setStroke(Color.LIGHTSLATEGRAY);
        quad3.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
        quad3.setStrokeWidth(150);
        quad3.setStrokeLineCap(StrokeLineCap.SQUARE);
        QuadCurve quad4 = new QuadCurve();
        quad4.setStartX(115);
        quad4.setStartY(525);
        quad4.setEndX(155);
        quad4.setEndY(575);
        quad4.setControlX(115);
        quad4.setControlY(575);
        quad4.setStroke(Color.LIGHTSLATEGRAY);
        quad4.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
        quad4.setStrokeWidth(150);
        quad4.setStrokeLineCap(StrokeLineCap.SQUARE);
        root.getChildren().addAll(audience, vb, l1, l2, l3, l4, quad1, quad2, quad3, quad4, finishingLine, finishingLine1, hinderance1, hinderance2, hinderance3, nitros,rect[0], rect[1], carImages[0],
                carImages[1], collision);

        stage.setTitle(name);

        Scene scene = new Scene(root, 3000, 3000);
        stage.setScene(scene);
        stage.show();
          AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                update(now, stage);
            }
        };

        timer.start();

        AnimationTimer timer1 = new AnimationTimer() {

            @Override
            public void handle(long now) {
                try {
                    update1(now, stage);
                } catch (IOException e) {

                }

            }
        };
        timer1.start();
        AnimationTimer timer2 = new AnimationTimer() {

            @Override
            public void handle(long now) {
                try {
                    update2(now);
                } catch (Exception e) {

                }

            }
        };

        timer2.start();
        AnimationTimer timer3 = new AnimationTimer() {

            @Override
            public void handle(long now) {
                try {
                    update3(now);
                } catch (Exception e) {

                }

            }
        };

        timer3.start();
                AnimationTimer soundTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                try {
                    playSound(now);
                } catch (Exception e) {

                }

            }
        };

        soundTimer.start();
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME + 1),
                        new KeyValue(timeSeconds, 0)));

        rect[var].requestFocus();

        rect[var].setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case LEFT:

                    Main.Car1.TurnLeft();
                          rect[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
           
            carImages[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);

                    break;
                case RIGHT:

                    Main.Car1.TurnRight();
                             rect[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
           
            carImages[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
                    break;
                case UP:
                   
                    Main.Car1.IncreaseSpeed();
if(once==0){
   // System.out.println("starts");
 long l =   System.currentTimeMillis();
   System.out.println("starts at"+l);
                             rect[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
           
            carImages[var].setRotate(-Main.Car1.angle * 180.0 / 3.1416);
            once=1;
}
                    timeline.playFromStart();
                    break;

                case DOWN:
                    Main.Car1.DecreaseSpeed();

                    break;

            }
        }
        );
//          stage.setOnCloseRequest((event) -> {
//            System.out.println("CLOSED");
//            String str4 = "CLOSE";
//            String fullMsg4 = "Via: " + via + '\n' + "To: " + name + '\n' + "From: " + name + '\n' + "Body: " + str4;
//         
//
//            byte[] data4 = fullMsg4.getBytes();
//            DatagramPacket sendPacket4 = new DatagramPacket(data4, data4.length, IPAddress, 4545);
//           try {
//                Client2.socketSender.send(sendPacket4);
//            } catch (IOException exce) {
//            }
//            
//             Client2.socketSender.close();
//              Client2.socketReceiver.close();
//            });

    }

    public static void main(String[] args) {
          arguments = args;
        launch(args);
    }

}
