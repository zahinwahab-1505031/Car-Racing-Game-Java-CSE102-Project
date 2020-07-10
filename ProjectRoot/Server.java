import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Zahin on 11/18/2016.
 */
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {

    String via;
    String getNames;
    DatagramSocket socketReceiver;
    DatagramSocket socketSender;
    DatagramPacket receivingPack;
    DatagramPacket sendingPack;
    String[] strArray;
    Hashtable< String, User> table;
    Hashtable< String, Integer> table1;
    Thread t;
    String names;
    int c1= -1;
    int c2;
    int own;
    int opp;
    public Server(String via) {
        this.via = via;
        getNames= "Get players info";
        table = new Hashtable();
        table1 = new Hashtable();
        try {
            socketReceiver = new DatagramSocket(4545);
            socketSender = new DatagramSocket();
        } catch (SocketException e) {

        }
        t = new Thread(this);
        t.start();

    }

    public void run() {
        while (true) {
            try {
                listenReceiveandSend();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public int listenReceiveandSend() {
        try {

            receivingPack = new DatagramPacket(new byte[1024], 1024);
            strArray = new String[4];

            socketReceiver.receive(receivingPack);
            byte[] receivedData = receivingPack.getData();
            String str1 = new String(receivedData, 0, receivingPack.getLength());
            String msgToBeSent = new String(receivedData, 0, receivingPack.getLength());
            InetAddress IPAddress = receivingPack.getAddress();
        //    names = new String[10];

            String[] lines = str1.split("\n");
            strArray[0] = lines[0].split(":")[1];
            strArray[1] = lines[1].split(":")[1];
            strArray[2] = lines[2].split(":")[1];
            strArray[3] = lines[3].split(":")[1];

            int counter1 = 0;
            for (String s : lines) {
                String[] tokens = s.split(":");
                strArray[counter1++] = tokens[1].substring(1, tokens[1].length());
            //    System.out.println(strArray[counter1]);
         }
           for(int i=0;i<4;i++){
                System.out.println ( "after tokenizing->>>" + strArray[i]);
            }
            int var1=0;
            if (strArray[0].equals(via) == false) {
              //  System.out.println("Warning: Server name mismatch. Message dropped.");
            } else if (strArray[0].equals(via) == true && strArray[1].equals(via) == true) {
                int portR = Integer.parseInt(strArray[3]);
                User user = new User(portR, IPAddress);
                table.put(strArray[2], user);
                System.out.println("new user added->"+strArray[2] + portR );
                c1++;
                System.out.println(strArray[2]+ c1);
                table1.put(strArray[2],c1);

                }


            else if (strArray[0].equals(via) == true && !table.containsKey(strArray[1])) {

               System.out.println("Warning: Unknown recipient. Message dropped when sending to .-> "+strArray[1] );//add condition for port
                if(strArray[3].equals("CLOSE")){
                    table.remove(strArray[2]);
                    System.out.println("removed user:" + strArray[2]);
                }
            }
               else if (strArray[0].equals(via) == true && strArray[1].equals(via) == false && table.containsKey(strArray[1])) {
                User recipient = table.get(strArray[1]);
                if (strArray[3].equals("Get players info")) {
                                    Set set = table.keySet(); // get set view of keys
                String playersInfo;
                Iterator<String> itr = set.iterator();     // get iterator

                while(itr.hasNext()) {
                    String s =itr.next();
                    if(s.equals(strArray[1])==false){
                        names = s;



                    }

                }


                 //   playersInfo = playersInfo.concat("null");
                    System.out.println(names);
                    String msg = "Via: " + via + '\n' + "To: " + strArray[1] + '\n' + "From: " + strArray[1] + '\n' + "Body: " + names;
                    byte[] sendingData1 = msg.getBytes();
                    DatagramPacket sendingPack1 = new DatagramPacket(sendingData1, sendingData1.length, recipient.IPAddress, recipient.port);
                 //  System.out.println("sending data packet to acknowledged user->"+ strArray[1]);
                    socketSender.send(sendingPack1);
                }
              else  if (strArray[3].equals("Get car number")) {
                   own = table1.get(strArray[1]);
                    System.out.println("my car: "+ own);
                    if(own%2==1) own =1;
                    else if (own%2==0)
                    own = 0;
                    if(own == 0) opp = 1;
                    else if(own==1) opp=0;
                    System.out.println("opponent's car: "+ opp);
                   String car = own +"#" + opp;
                    System.out.println(car);
                    String msg1 = "Via: " + via + '\n' + "To: " + strArray[1] + '\n' + "From: " + strArray[1] + '\n' + "Body: " + car;
                    byte[] sendingData2 = msg1.getBytes();
                    DatagramPacket sendingPack2 = new DatagramPacket(sendingData2, sendingData2.length, recipient.IPAddress, recipient.port);
                //    System.out.println("sending data packet to acknowledged user->"+ strArray[1]);
                    socketSender.send(sendingPack2);
                }

              else {
                    byte[] sendingData = msgToBeSent.getBytes();
                    sendingPack = new DatagramPacket(sendingData, sendingData.length, recipient.IPAddress, recipient.port);
                 //   System.out.println(strArray[2] + "->sending data packet to acknowledged user-> " + strArray[1]);
                    socketSender.send(sendingPack);
                }

            }

        } catch (SocketException e) {
            System.out.println(e);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            new Server(args[0]);
        } else {
            new Server("cse");
        }
    }
}
