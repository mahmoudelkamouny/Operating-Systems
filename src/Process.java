import java.util.Queue;

import com.sun.beans.WeakCache;
import com.sun.jmx.snmp.tasks.ThreadService;
import com.sun.webkit.ThemeClient;
import com.sun.xml.internal.bind.v2.model.core.MaybeElement;

//import java.util.concurrent.Semaphore;


public class Process extends Thread {
	
	public int processID;
    ProcessState status=ProcessState.New;	

	
	public Process(int m) {
		processID = m;
	}
	@Override
	public void run() {
		
		System.out.println("Running Running Running Running Running Running Running Running Running Running " + processID);
		
		
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		}
		OperatingSystem.wakeNextProcess();
	}
	
	private void process1() {
		OperatingSystem.semPrintText.semWait(this);
		OperatingSystem.semTakeInput.semWait(this);
		OperatingSystem.semReadFile.semWait(this);
		
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));
		
		OperatingSystem.semPrintText.semPost();
		OperatingSystem.semTakeInput.semPost();
		OperatingSystem.semReadFile.semPost();
		
	
		setProcessState(this,ProcessState.Terminated);
//		OperatingSystem.semRunning.semPost();
		}
	
	private void process2() {
		OperatingSystem.semPrintText.semWait(this);
		OperatingSystem.semTakeInput.semWait(this);
		
		OperatingSystem.printText("Enter File Name: ");
		String filename= OperatingSystem.TakeInput();
		
//		OperatingSystem.semTakeInput.semPost();
//		OperatingSystem.semPrintText.semPost();
//		
//		OperatingSystem.semPrintText.semWait(this);
//		OperatingSystem.semTakeInput.semWait(this);
		
		OperatingSystem.printText("Enter Data: ");
		String data= OperatingSystem.TakeInput();
		
		OperatingSystem.semTakeInput.semPost();
		OperatingSystem.semPrintText.semPost();
		
		OperatingSystem.semWriteFile.semWait(this);
		OperatingSystem.writefile(filename,data);
		OperatingSystem.semWriteFile.semPost();
		
		setProcessState(this,ProcessState.Terminated);
//		OperatingSystem.semRunning.semPost();
		}
	private void process3() {
		int x=0;
		OperatingSystem.semPrintText.semWait(this);
		while (x<301)
		{ 
			OperatingSystem.printText(x+"\n");
			x++;
		}
		OperatingSystem.semPrintText.semPost();
		setProcessState(this,ProcessState.Terminated);
		
		}
	
	private void process4() {
	
		int x=500;
		OperatingSystem.semPrintText.semWait(this);
		while (x<1001)
		{
			OperatingSystem.printText(x+"\n");
			x++;
		}	
		OperatingSystem.semPrintText.semPost();
		setProcessState(this,ProcessState.Terminated);

		}
	private void process5() {
		
		OperatingSystem.semPrintText.semWait(this);
		OperatingSystem.semTakeInput.semWait(this);
		
		OperatingSystem.printText("Enter LowerBound: ");
		String lower= OperatingSystem.TakeInput();
		
//		OperatingSystem.semPrintText.semPost();
//		OperatingSystem.semTakeInput.semPost();
//		
//		OperatingSystem.semPrintText.semWait(this);
//		OperatingSystem.semTakeInput.semWait(this);
		
		OperatingSystem.printText("Enter UpperBound: ");
		String upper= OperatingSystem.TakeInput();
		
		OperatingSystem.semPrintText.semPost();
		OperatingSystem.semTakeInput.semPost();
		
		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		String data="";
		
		while (lowernbr<=uppernbr)
		{
			data+=lowernbr++ +"\n";
		}	
		OperatingSystem.semWriteFile.semWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.semWriteFile.semPost();
		
		setProcessState(this,ProcessState.Terminated);

	}
	
	 public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
		 if (s == ProcessState.Terminated)
		 {
			 OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		 }
	}
	 
	 public static ProcessState getProcessState(Process p) {
		 return p.status;
	}
}
