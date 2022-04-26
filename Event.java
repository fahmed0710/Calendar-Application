/*
 * A class that represents an event, which consists of a name and a TimeInterval
 * 
 * @author Fariha Ahmed
 * @version 1.0
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {
	
	private String name, days;
	private ArrayList<Integer> daysOfWeek;
	private LocalDate date, sd, ed;
	private TimeInterval ti;
	
	/*
	 * Constructor for one-time events
	 * 
	 * @param n the name of the event
	 * @param d the date of the event
	 * @param st the starting time of the event
	 * @param et the ending time of the event
	 */
	public Event(String n, LocalDate d, LocalTime st, LocalTime et) {
		this.name = n;
		this.date = d;
		this.ti = new TimeInterval(st,et);
		
		this.daysOfWeek = null;
	}
	
	/*
	 * Constructor for recurring events
	 * 
	 * @param n the name of the event
	 * @param d the days of the week on which the event occurs
	 * @param sd the starting date of the event
	 * @param ed the ending date of the event
	 * @param st the starting time of the event
	 * @param et the ending time of the event
	 */
	public Event(String n, String d, LocalTime st, LocalTime et, LocalDate sd, LocalDate ed) {
		this.name = n;
		this.days = d;
		this.daysOfWeek = new ArrayList<Integer>();
		this.ti = new TimeInterval(st,et);
		this.sd = sd;
		this.ed = ed;
		
		d = d.toLowerCase();
		for(int i = 0; i < d.length(); i++) {
			if(d.charAt(i) == 'm')
				this.daysOfWeek.add(1);
			else if(d.charAt(i) == 't')
				this.daysOfWeek.add(2);
			else if(d.charAt(i) == 'w')
				this.daysOfWeek.add(3);
			else if(d.charAt(i) == 'r')
				this.daysOfWeek.add(4);
			else if(d.charAt(i) == 'f')
				this.daysOfWeek.add(5);
			else if(d.charAt(i) == 'a')
				this.daysOfWeek.add(6);
			else if(d.charAt(i) == 's')
				this.daysOfWeek.add(7);
		}
	}
	
	/*
	 * Identifies events as recurring or not
	 * 
	 * @return true/false depending on if the event is recurring
	 */
	public boolean isOneTime() {
		if(this.daysOfWeek == null)
				return true;
		return false;
	}
	
	/*
	 * Returns the name of the event
	 * 
	 * @return the event's name
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * Returns the days of the week the event occurs on
	 * 
	 * @return the days of the week the event occurs on
	 */
	public String getDaysOfWeek() {
		return this.days.toUpperCase();
	}
	
	/*
	 * Returns the array list of the values of the days of the week the event occurs on
	 * 
	 * @return the array list of the enum of the days of the week
	 */
	public ArrayList<Integer> getDaysOfTheWeek(){
		return this.daysOfWeek;
	}
	
	
	/*
	 * Returns the start to end time of the event
	 * 
	 * @return the event's time interval
	 */
	public TimeInterval getTimeInterval() {
		return this.ti;
	}
	
	/*
	 * Returns the date of the event
	 * 
	 * @return the event's starting date
	 */
	public LocalDate getStartDate() {
		LocalDate s;
		if(this.isOneTime() == true) 
			s = this.date;
		else
			s = this.sd;
		
		return s;
	}
	
	/*
	 * Returns the last occurrence of the event
	 * 
	 * @return the event's ending date
	 */
	public LocalDate getEndDate() {
		return this.ed;
	}
	
	/*
	 * Returns all the dates a recurring event occurs on
	 * 
	 * @return a list of all the dates of an event
	 */
	public ArrayList<LocalDate> getAllDates() {
		ArrayList<LocalDate> allDates = new ArrayList<LocalDate>();
		if(this.isOneTime() == false) {
			for(LocalDate date = this.sd; date.isBefore(this.ed); date = date.plusDays(1)) {
				int dayOfWeek = date.getDayOfWeek().getValue();
				for(int i = 0; i < this.daysOfWeek.size(); i ++) {
					int dayVal = this.daysOfWeek.get(i);
					if(dayVal == dayOfWeek) {
						allDates.add(date);
					}
				}
			}
			allDates.add(this.ed);
		}
		return allDates;
	}
}
