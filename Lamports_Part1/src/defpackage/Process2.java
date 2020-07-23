package defpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Process2 extends Thread {
	private Thread thread;
	private String threadName;
	int clockProcess2 = 20;

	Process2(String name) {
		threadName = name;
	}

	public void run() {
		System.out.println("Running " + threadName);
		try {
			if (threadName.equalsIgnoreCase("Sender")) {
				threadForSending();
				Thread.sleep(100);
			} else {
				threadForReceiving();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread " + threadName + " interrupted.");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}

	void threadForReceiving() throws IOException {
		int i = 0;
		String data = "";
		String dataArray[] = new String[100];
		int time[] = new int[50];
		int logicalTimeArray[] = new int[50];
		DatagramSocket serverSocket = new DatagramSocket(9091);
		for (i = 0; i < 27; i++) {

			byte receive[] = new byte[500];
			DatagramPacket rp = new DatagramPacket(receive, receive.length);
			serverSocket.receive(rp);// Receiving the data
			clockProcess2++;// The logical is also increased when receiving the data because it is another
							// operation
			String dataReceived = new String(rp.getData(), "UTF-8");
			data = dataReceived.substring(0, dataReceived.indexOf(','));
			String timeStamp = dataReceived.substring(dataReceived.indexOf(',') + 1);
			char ch = dataReceived.charAt(dataReceived.indexOf(',') + 1);
			int temp = (int) (ch);
			temp = temp - 48;
			dataArray[i] = data;
			time[i] = temp;
			if (time[i] > clockProcess2)// Checking if the time it occured is greater than the current logical clock
			// If so, then the logical clock is updated
			{
				clockProcess2 = time[i] + 1;
			}
			logicalTimeArray[i] = clockProcess2;
		}
		print(dataArray, time, logicalTimeArray);
	}

	void print(String dataArr[], int timeArr[], int logicalArr[]) {
		int i, j, temp, n = 27;
		String temp2 = "";
		for (i = 0; i < n; i++) {
			for (j = 0; j < (n - i - 1); j++) {
				if (logicalArr[j] > logicalArr[j + 1])// Ordering everything by the logical clocks.
				{
					temp = logicalArr[j];
					logicalArr[j] = logicalArr[j + 1];
					logicalArr[j + 1] = temp;

					temp = timeArr[j];
					timeArr[j] = timeArr[j + 1];
					timeArr[j + 1] = temp;

					temp2 = dataArr[j];
					dataArr[j] = dataArr[j + 1];
					dataArr[j + 1] = temp2;
				}
			}
		}
		for (i = 0; i < 27; i++) {
			String temporary = dataArr[i] + "::" + logicalArr[i];
			System.out.println(temporary);
		}
	}

	void threadForSending() throws IOException, InterruptedException {
		Thread.sleep(10000);
		int i, length, j;
		byte sendBytesArray[] = new byte[500];
		String s;
		DatagramSocket clientSocket = new DatagramSocket();
		String str = "Hi there! I am Shrutikirti Saxena from Ahmedabad, India";
		String strArr[] = str.split(" ");
		length = strArr.length;
		DatagramPacket sendingPacket = null;
		for (i = 0; i < length; i++) {

			clockProcess2++;
			// Whenever something is sent, the logical clock of the process is incremented
			// and appeneded
			// to the message and multicasted to all other processes.
			String stringForSending = "Process 2: ";
			s = stringForSending.concat(strArr[i]);
			s = s.concat("," + clockProcess2);
			sendBytesArray = s.getBytes();
			sendingPacket = new DatagramPacket(sendBytesArray, sendBytesArray.length, InetAddress.getByName("localhost"), 9090);
			clientSocket.send(sendingPacket);
			sendingPacket = null;
			sendingPacket = new DatagramPacket(sendBytesArray, sendBytesArray.length, InetAddress.getByName("localhost"), 9091);
			clientSocket.send(sendingPacket);
			sendingPacket = null;
			sendingPacket = new DatagramPacket(sendBytesArray, sendBytesArray.length, InetAddress.getByName("localhost"), 9092);
			clientSocket.send(sendingPacket);
			sendingPacket = null;
			Thread.sleep(1000);

		}
	}

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please press enter to start");
		br.readLine();
		Process2 T1 = new Process2("Sender");
		T1.start();

		Process2 T2 = new Process2("Receiver");
		T2.start();
	}

}
