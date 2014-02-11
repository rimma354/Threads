import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ThreadManager {
	private volatile static ThreadManager manager;
	private TreeMap<Integer, List<Runnable>> eventBus = new TreeMap<Integer, List<Runnable>>();
	private int workingProcess;
	private int step;

	private ThreadManager() {
	}

	public Map<Integer, List<Runnable>> getAllThreads() {
		return eventBus;
	}

	public static ThreadManager getInstance() {
		if (manager == null) {
			synchronized (ThreadManager.class) {
				if (manager == null) {
					manager = new ThreadManager();
				}
			}
		}
		return manager;
	}

	public void addThread(Integer priority, Runnable r) {
		if (priority > Thread.MAX_PRIORITY || priority < Thread.MIN_PRIORITY) {
			System.out.println("Priority should be between 1 and 10.");
		} else {
			if (r != null) {
				if (r instanceof CopyFileInBuffer && priority!=Thread.MAX_PRIORITY){
					priority=10;
					System.out.println("This thread shoud have max priority. Priority automatically will be set 10.");
				}
				List<Runnable> threads = eventBus.get(priority);
				if (threads == null) {
					threads = new ArrayList<Runnable>();
				}
				threads.add(r);
				eventBus.put(priority, threads);
			}
		}

	}

	public void startThread() {
		List<Runnable> mainThreads = eventBus.get(eventBus.lastKey());
		if (mainThreads != null) {
			for (Iterator<Runnable> iter = mainThreads.listIterator(); iter
					.hasNext();) {
				Thread t=new Thread(iter.next());
				t.start();

			}
		}		
		for (workingProcess = eventBus.lastKey()-1; workingProcess > eventBus.firstKey()-1; workingProcess--) {
			List<Runnable> threads = eventBus.get(workingProcess);
			if (threads != null) {
				for (Iterator<Runnable> iter2 = threads.listIterator(); iter2
						.hasNext();) {
					Thread t2=new Thread(iter2.next());
					t2.start();
					try {
						t2.join();
					} catch (InterruptedException e) {
					}
				}
			}
		}
			
	}
	
	public int getStep(){
		return step;
	}
	
	public void incrementStep(){
		this.step++;
	}

}
