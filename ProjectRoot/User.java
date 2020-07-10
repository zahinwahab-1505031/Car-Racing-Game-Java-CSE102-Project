/**
 * Created by Zahin on 11/18/2016.
 */
import java.net.InetAddress;
public class User {

    int port;
    InetAddress IPAddress;
    public User(int port,InetAddress IPAddress){
        this.port = port;
        this.IPAddress=IPAddress;
    }

}