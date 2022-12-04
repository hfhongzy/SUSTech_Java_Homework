// 文件名 GreetingServer.java
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread  {
  private ServerSocket serverSocket;
  
  public GreetingServer(int port) throws IOException
  {
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(10000);
  }
  
  public void run()  {
    try {
      System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
      Socket server = serverSocket.accept();
      System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
      DataInputStream in = new DataInputStream(server.getInputStream());
      DataOutputStream out = new DataOutputStream(server.getOutputStream());
      
      while(true) {
        String s = in.readUTF();
        if(s.equals("exit")) {
          break;
        }
        out.writeUTF(s);
      }
      server.close();
    } catch(SocketTimeoutException s) {
      System.out.println("Socket timed out!");
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  public static void main(String [] args) {
    int port = Integer.parseInt(args[0]);
    try {
      Thread t = new GreetingServer(port);
      t.run();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}