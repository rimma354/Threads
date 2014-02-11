public class TestFile {

	public static void main(String[] args) {
		String fileUrl = "res//myfile.txt";
		Buffer buffer = Buffer.getInstance(fileUrl);
		
		ThreadManager manager = ThreadManager.getInstance();
		FindLowerCase r3 = new FindLowerCase(buffer,"Thread3");
		FindDigitsSymbols r4 = new FindDigitsSymbols(buffer,"Thread4");
		CopyFileInBuffer r1 = new CopyFileInBuffer(buffer,"Thread1");
		FindUpperCase r2 = new FindUpperCase(buffer,"Thread2");

		manager.addThread(10, r1);
		manager.addThread(4, r3);
		manager.addThread(5, r2);
		manager.addThread(3, r4);
		manager.startThread();
	}

}
