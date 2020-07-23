package newpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Process3 extends Clock3 implements Runnable
{
	   private Thread t;
	   private String threadName;
	   int logicalClock=30;
	   
	   Process3(String name) 
	   {
	      threadName = name;
	      System.out.println("Creating " +  threadName );
	   }
	   void sending() throws IOException, InterruptedException
	   {
		   System.out.println("Sender Started"+clock3);
		   Thread.sleep(10000);
		   int i,l,j;
		   byte send[] = new byte[500];
		   String s;
		   DatagramSocket client = new DatagramSocket();
		   String str="Hi there! I am Shrutikirti Saxena from Ahmedabad, India";
		   String strArr[] = str.split(" ");
		   l=strArr.length;
		   DatagramPacket sp=null;
		   for(i=0;i<l;i++)
		   {
			   
				   logicalClock+=3;
				   updateClock();
				   String val=Integer.toString(clock3.get(0))+"#"+Integer.toString(clock3.get(1))+"#"+Integer.toString(clock3.get(2))+"$Process 3: ";
				   s=val.concat(strArr[i]);
				   s=s.concat(","+logicalClock);
				   //System.out.println(s);
				   send=s.getBytes();
				   sp = new DatagramPacket(send,send.length,InetAddress.getByName("localhost"),8080);
				   client.send(sp);
				   sp=null;
				   sp = new DatagramPacket(send,send.length,InetAddress.getByName("localhost"),8081);
				   client.send(sp);
				   sp=null;
				   sp = new DatagramPacket(send,send.length,InetAddress.getByName("localhost"),8082);
				   client.send(sp);
				   sp=null;
				   Thread.currentThread().sleep(1000);
			   
		   }
	   }
	   void receiving() throws IOException 
	   {
		   System.out.println("Receiver Started"+clock3);
		   int i=0, c0, c1, c2;
		   String data="";
		   String arr[] = new String[100];
		   int time[] = new int[50];
		   int logicalTime[] = new int[50];
		   DatagramSocket server = new DatagramSocket(8082);
		   for(i=0;i<27;i++) 
		   {
		   
		   byte receive[] = new byte[500];
		   DatagramPacket rp = new DatagramPacket(receive,receive.length);
		   server.receive(rp);
		   logicalClock+=3;
		   updateClock();
		   String line = new String(rp.getData(),"UTF-8");
		   System.out.println("----------Data received----------");
		   System.out.println(line);
		   String vectorString = line.substring(0,line.indexOf("$")).trim();
		   String vectorArr[] = vectorString.split("#");
		   c0 = Integer.parseInt(vectorArr[0]);
		   c1 = Integer.parseInt(vectorArr[1]);
		   c2 = Integer.parseInt(vectorArr[2]);
		   clock3.set(0, Math.max(clock3.get(0), c0));
		   clock3.set(1, Math.max(clock3.get(1), c1));
		   clock3.set(2, Math.max(clock3.get(2), c2));
		   System.out.println("Vector clock: "+clock3);
		   data = line.substring(0, line.indexOf(','));
		   String timeStamp = line.substring((line.indexOf(',')+1),line.length());
		   char ch = line.charAt(line.indexOf(',')+1);
		   int temp = (int)(ch);
		   temp=temp-48;
		   arr[i]=data;
		   time[i]=temp;
		   if(time[i]>logicalClock)
		   {
			   logicalClock=time[i]+1;
		   }
		   logicalTime[i]=logicalClock;
		   }
		   print(arr,time,logicalTime);
//		   for(int i=0;i<c;i++)
//			   System.out.println(arr[i]);
	   }
	   void print(String arr[],int time[], int logical[])
	   {
		   int i,j,temp,n=27;
		   String temp2="";
		   for(i=0;i<n;i++)
		   {
			   for(j=0;j<(n-i-1);j++)
			   {
				   if(logical[j]>logical[j+1])
				   {
					   temp=logical[j];
					   logical[j]=logical[j+1];
					   logical[j+1]=temp;
					   
					   temp=time[j];
					   time[j]=time[j+1];
					   time[j+1]=temp;
					   
					   temp2=arr[j];
					   arr[j]=arr[j+1];
					   arr[j+1]=temp2;
				   }
			   }
		   }
		   for(i=0;i<27;i++)
		   {
			   String tempo=arr[i]+"::"+time[i]+"::"+logical[i];
			   //System.out.println(tempo);
		   }
		   System.out.println("Vector at process 3 is"+clock3);
	   }
	   public void run() 
	   {
	      System.out.println("Running " +  threadName );
	      try 
	      {
	         if(threadName.contains("Sender"))
	         {
	        	 sending();
	            Thread.sleep(100);
	         }
	         else
	         {
	        	 receiving();
	        	 Thread.sleep(100);
	         }
	      } catch (InterruptedException e) 
	      {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      } catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
//	   public void start() 
//	   {
//	      System.out.println("Starting " +  threadName );
//	      if (t == null) 
//	      {
//	         t = new Thread (this, threadName);
//	         t.start ();
//	      }
//	   }
	   public static void main(String args[])throws IOException
	   {
		   clock3.add(0);
		   clock3.add(0);
		   clock3.add(0);
		   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   System.out.println("Process 3: "+clock3);
		   System.out.println("Press Enter to start...");
		   br.readLine();
		      Thread T1 = new Thread(new Process3("Process 3 Sender"));
		      T1.start();
		     
		      Thread T2 = new Thread(new Process3("Process 3 Receiver"));
		      T2.start();
		   }   

}
