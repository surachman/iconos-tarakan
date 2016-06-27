package com.qtasnim.connection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionHelper
{
  public static boolean ping(String host, String port)
  {
    int timeout = 2000;
    
    Socket socket = new Socket();
    InetSocketAddress endPoint = new InetSocketAddress(host, Integer.parseInt(port));
    if (endPoint.isUnresolved()) {
      return false;
    }
    try
    {
      socket.connect(endPoint, timeout);
      return true;
    }
    catch (IOException ioe)
    {
      return false;
    }
    finally
    {
      if (socket != null) {
        try
        {
          socket.close();
        }
        catch (IOException ioe) {}
      }
    }
  }
  
  public static void main(String[] args)
  {
    System.out.println(ping("192.168.5.2", "3700"));
  }
}
