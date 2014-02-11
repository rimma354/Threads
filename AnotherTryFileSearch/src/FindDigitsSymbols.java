public class FindDigitsSymbols extends WorkWithBuffer  implements Runnable {

	public FindDigitsSymbols(Buffer buffer, String name) {
		super(buffer,name);
		job="It finds digits and symbols: ";
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
			buffer.writeToFile(hello + job + line + buffer.findDigitsAndSymbols()
					+ line + bye);
			end = System.currentTimeMillis();
			time = start - end;
			ThreadManager.getInstance().incrementStep();
			buffer.notifyAll();
		}
	}

}
