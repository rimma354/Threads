public class FindUpperCase extends WorkWithBuffer implements Runnable {

	public FindUpperCase(Buffer buffer, String name) {
		super(buffer, name);
		job = "It finds upper-case letters: ";
	}

	@Override
	public void run() {
		synchronized (buffer) {
			start = System.currentTimeMillis();
			while (buffer.isFileCopied()!=true)
				try {
					buffer.wait();
				} catch (InterruptedException e) {
				}
			buffer.writeToFile(hello + job + line + buffer.findUpperLetters()
					+ line + bye);
			end = System.currentTimeMillis();
			time = start - end;
			ThreadManager.getInstance().incrementStep();
			buffer.notifyAll();
		}
	}

}
