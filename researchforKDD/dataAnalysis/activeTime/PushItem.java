package activeTime;

public class PushItem {
	public long start=0,end=0,interval=0;
	public String type="",newsid="";
	public int success=0;
	public int wrepost_count=0;
	public int pushhour;
	public int pushminute;
	public int intervalminute;
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	/**
	 * generate a push item
	 * @param start
	 * @param end
	 * @param interval
	 * @param type
	 */
	public PushItem(long start, long end, long interval, String type) {
		super();
		this.start = start;
		this.end = end;
		this.interval = interval;
		this.type = type;
	}

}
