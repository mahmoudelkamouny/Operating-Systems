import java.util.ArrayList;

public class MySemaphore {
	boolean key;//true -> available false-> unavailabe
	ArrayList<Process> Queue;
	public MySemaphore() {
		key = true;
		Queue = new ArrayList<Process>();
	}


	@SuppressWarnings("deprecation")
	public void semWait(Process p) {
		if(key==false) {
//			p.stop();
			p.status = ProcessState.Waiting;
			Queue.add(p);
			p.suspend();
		
		}
		else {
			key = false;
			Queue.remove(p);
		}
	}
	public void semPost() {
		if(!Queue.isEmpty()) {
			Process p = Queue.remove(0);
			p.status = ProcessState.Ready;
			OperatingSystem.readyQueue.add(p);
//				p.resume();	

		}
		else {
			key = true;
		}	
	}
	
}
