public class FindLowerCase extends WorkWithBuffer implements Runnable {

	public FindLowerCase(Buffer buffer, String name) {
		super(buffer, name);
		job = "It finds lower-case letters: ";
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
			buffer.writeToFile(hello + job + line + buffer.findLowerLetters()
					+ line + bye);
			end = System.currentTimeMillis();
			time = start - end;
			ThreadManager.getInstance().incrementStep();
			buffer.notifyAll();
		}
	}

}
