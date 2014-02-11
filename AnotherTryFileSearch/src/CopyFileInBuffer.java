import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class CopyFileInBuffer extends WorkWithBuffer implements Runnable {

	public CopyFileInBuffer(Buffer buffer, String name) {
		super(buffer, name);
		job = "It copies file to buffer. ";
	}

	@Override
	public void run() {
		synchronized (buffer) {
			ThreadManager tm=ThreadManager.getInstance();
			start = System.currentTimeMillis();
			buffer.copyInBuffer();
			buffer.writeToFile(hello + job);
			while (tm.getStep() != tm.getAllThreads().size() - 1) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
				}
			}

			for (Entry<Integer, List<Runnable>> entry : ThreadManager
					.getInstance().getAllThreads().entrySet()) {
				List<Runnable> th = entry.getValue();
				for (Iterator<Runnable> iter = th.listIterator(); iter
						.hasNext();) {
					Runnable r = iter.next();
					if (this != r)
						buffer.writeToFile(r.toString());

				}
			}
			end = System.currentTimeMillis();
			time = start - end;
			buffer.writeToFile(name + " stopped. " + toString());
			buffer.notifyAll();
		}
	}

}
