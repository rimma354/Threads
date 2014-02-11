public class WorkWithBuffer {
	protected Buffer buffer;
	protected long start;
	protected long end;
	protected long time;
	protected String hello;
	protected String bye;
	protected String line;
	protected String job;
	protected String name;

	public WorkWithBuffer(Buffer buffer, String name) {
		this.buffer = buffer;
		this.name = name;
		hello = "Started " + this.name+". ";
		bye = this.name + " stopped.";
		line = System.getProperty("line.separator");
	}
	
	@Override
	public String toString(){
		return name+" worked "+time+" sec.";
	}
}
