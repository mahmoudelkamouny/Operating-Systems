import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class OperatingSystem {
	
	public static ArrayList<Thread> ProcessTable;
	public static ArrayList<Process> readyQueue;
	public static MySemaphore semReadFile = new MySemaphore();
	public static MySemaphore semWriteFile = new MySemaphore();
	public static MySemaphore semPrintText = new MySemaphore();
	public static MySemaphore semTakeInput = new MySemaphore();
//	public static MySemaphore semRunning = new MySemaphore();

//	public static int activeProcess= 0;
	//system calls:
	// 1- Read from File
	@SuppressWarnings("unused")
	public static String readFile(String name) {
		String Data="";
		File file = new File(name);
	 try {
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine())
		{
			Data+= scan.nextLine()+"\n";
		}
		scan.close();
	} catch (FileNotFoundException e) {
		System.out.println(e.getMessage());
	}
		return Data;
	}
	
	// 2- Write into file
	@SuppressWarnings("unused")
	public static void writefile(String name, String data) {
		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

	}
	//3- print to console
	@SuppressWarnings("unused")
	public static void printText(String text) {

		System.out.println(text);
		
	}
	
	//4- take input
	
	@SuppressWarnings("unused")
	public static String TakeInput() {
		Scanner in= new Scanner(System.in);
		String data = in.nextLine();
		return data;
		
	}
	
	private static void createProcess(int processID){
		Process p = new Process(processID);
		ProcessTable.add(p);
		Process.setProcessState(p,ProcessState.Ready);
//		readyQueue.add(p);
		boolean flag = true;
		for(int i=0;i<ProcessTable.size();i++) {
			if(ProcessTable.get(i).isAlive())
				flag = false;
		}
		if(flag) {
			p.start();
		}
		else {
			readyQueue.add(p);
		}
	}
	public static void wakeNextProcess() {
		boolean flag = true;
		for(int i=0;i<ProcessTable.size();i++) {
			if(ProcessTable.get(i).isAlive())
				flag = false;
		}
		if(flag) {
			if(!readyQueue.isEmpty()){	
				Process p = readyQueue.remove(0);
				p.start();
			}
		}
	}
	
	public static void main(String[] args) {
   		ProcessTable = new ArrayList<Thread>();
   		readyQueue = new ArrayList<Process>();
		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);
//		while(!readyQueue.isEmpty()) {
//			Process p = readyQueue.get(0);
//			p.start();
//		}
   		

	}
}



