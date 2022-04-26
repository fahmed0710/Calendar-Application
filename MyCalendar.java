/*
 * A class that defines an underlying data structure to hold events
 * 
 * @author Fariha Ahmed
 * @version 1.0
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyCalendar {
	private ArrayList<Event> events, otEvents, rEvents;
	
	/*
	 * Constructor for MyCalendar
	 */
	public MyCalendar() {
		this.events = new ArrayList<Event>();
		this.otEvents = new ArrayList<Event>();
		this.rEvents = new ArrayList<Event>();
	}
	
	/*
	 * If there isn't any conflict in the time intervals,
	 * adds an event to events list
	 * 
	 * @param e the event being added to the events list
	 */
	public void add(Event e) {
		this.events.add(e);
		if(e.isOneTime() == true) {
			this.otEvents.add(e);
			
			//Sort one-time events by order of starting date and starting time
			for(int i = 0; i < this.otEvents.size(); i++) {
				int min = i;
				for(int j = i + 1; j < this.otEvents.size(); j++) {
					Event e1 = this.otEvents.get(j);
					int compare = e1.getStartDate().compareTo(this.otEvents.get(min).getStartDate());
						if(compare < 0)
							min = j;
						else if(compare == 0) {
							int timeCompare = e1.getTimeInterval().getStart().compareTo(this.otEvents.get(min).getTimeInterval().getStart());
							if(timeCompare < 0)
								min = j;
						}
							
					Event temp = otEvents.get(min);
					this.otEvents.set(min, otEvents.get(i));
					this.otEvents.set(i, temp);
				}
			}
		}
		else {
			this.rEvents.add(e);
			
			//Sort recurring events by starting date, then by time
			for(int i = 0; i < this.rEvents.size(); i++) {
				int min = i;
				for(int j = i + 1; j < this.rEvents.size(); j++) {
					Event e1 = this.rEvents.get(j);
					int compare = e1.getStartDate().compareTo(this.rEvents.get(min).getStartDate());
					if(compare < 0)
						min = j;
					else if(compare == 0) {
						int dayCompare = e1.getDaysOfTheWeek().get(0).compareTo(this.rEvents.get(min).getDaysOfTheWeek().get(0));
						if(dayCompare < 0)
							min = j;
						else if(dayCompare == 0) {
							int timeCompare = e1.getTimeInterval().getStart().compareTo(this.rEvents.get(min).getTimeInterval().getStart());
							if(timeCompare < 0)
								min = j;
						}
					}
							
					Event temp = this.rEvents.get(min);
					this.rEvents.set(min, this.rEvents.get(i));
					this.rEvents.set(i, temp);
				}
			}
		}	
	}
	
	/*
	 * Removes an event from events list
	 * 
	 * @param e the event being removed from the events list
	 */
	public void remove(Event e) {
		this.events.remove(e);
		if(otEvents.contains(e))
			this.otEvents.remove(e);
		else if(rEvents.contains(e))
			this.rEvents.remove(e);
	}
	
	/*
	 * Returns the list of events
	 * 
	 * @return the array list of events
	 */
	public ArrayList<Event> getList(){
		return this.events;
	}
	
	/*
	 * Returns the list of one-time events
	 * 
	 * @return the array list of one-time events
	 */
	public ArrayList<Event> getOneTimeList(){
		return this.otEvents;
	}
	
	/*
	 * Returns the list of recurring events
	 * 
	 * @return the array list of recurring events
	 */
	public ArrayList<Event> getRecurringList(){
		return this.rEvents;
	}
	
	/*
	 * Returns a list of events on the given date
	 * 
	 * @param d the date
	 * @return the list of events on that date
	 */
	public ArrayList<Event> onThisDay(LocalDate d) {
		ArrayList<Event> onThisDay = new ArrayList<Event>();
		for(int i = 0; i < this.events.size(); i++) {
			Event e = this.events.get(i);
			if(e.isOneTime() == true) {
				if(e.getStartDate().equals(d))
					onThisDay.add(e);
			}
			else if(e.isOneTime() == false) {
				ArrayList<LocalDate> dates = e.getAllDates();
				for(int j = 0; j < dates.size(); j++) {
					LocalDate re = dates.get(j);
					if(re.equals(d))
						onThisDay.add(e);
				}
			}
		}
		
		//Sort the events on the given day by starting time
		for(int i = 0; i < onThisDay.size(); i++) {
			int min = i;
			for(int j = i + 1; j < onThisDay.size(); j++) {
				Event e1 = onThisDay.get(j);
				int compare = e1.getTimeInterval().getStart().compareTo(onThisDay.get(min).getTimeInterval().getStart());
					if(compare < 0)
						min = j;
						
				Event temp = onThisDay.get(min);
				onThisDay.set(min, onThisDay.get(i));
				onThisDay.set(i, temp);
			}
		}
		
		return onThisDay;
	}
	
	/*
	 * Returns all the events in the events list
	 * 
	 * @return the event list
	 */
	public String view() {
		String s = "";
		DateTimeFormatter formatter;
		
		//One-time events as string
		formatter = DateTimeFormatter.ofPattern("E MMM d");
		
		int currentYear = otEvents.get(0).getStartDate().getYear();
		String ote = "ONE-TIME EVENTS" + "\n" + currentYear;
		for(int i = 0; i < otEvents.size(); i++) {
			Event e = otEvents.get(i);
			LocalDate d = otEvents.get(i).getStartDate();
			if(d.getYear() != currentYear) {
				currentYear = d.getYear();
				ote += "\n" + currentYear;
			}
			ote += "\n   " + formatter.format(d) + " " + e.getTimeInterval() + " " + e.getName();
		}
		
		s += ote;
		
		//Recurring events as string
		formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		String re = "\n\nRECURRING EVENTS";
		for(int i = 0; i < rEvents.size(); i++) {
			Event e = rEvents.get(i);
			re += "\n" + e.getName();
			re += "\n" + e.getDaysOfWeek() + " " + e.getTimeInterval().toString() + " " 
				   + formatter.format(e.getStartDate()) + " " + formatter.format(e.getEndDate());
		}
		
		s += re;
		
		return s;
	}
}