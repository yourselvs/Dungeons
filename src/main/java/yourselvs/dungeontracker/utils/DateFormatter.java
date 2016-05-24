package yourselvs.dungeontracker.utils;

import java.text.SimpleDateFormat;

public class DateFormatter {
	private static SimpleDateFormat longFormat;
	private static SimpleDateFormat shortFormat;
	
	/**
	 * Formats date and time to readable strings.
	 */
	public DateFormatter(){
		longFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SS");
		shortFormat = new SimpleDateFormat("mm:ss.SS");		
	}
	
	/**
	 * @return	A formatter that formats dates in a long string.
	 */
    public SimpleDateFormat getLongFormatter() {return longFormat;}
    
    /**
     * @return	A formatter that formats dates in a short string.
     */
    public SimpleDateFormat getShortFormatter() {return shortFormat;}
}
