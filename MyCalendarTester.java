/*
 * A tester class to test the MyCalendar class
 * 
 * @author Fariha Ahmed
 * @version 1.0
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MyCalendarTester {
	public static void main(String[] args) throws IOException {
		MyCalendar c = new MyCalendar();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        //Display calendar
     	LocalDate currentDate = LocalDate.now(); // capture today
     	printCalendar(currentDate);
        
        //Read from a file to load in events
        BufferedReader br = new BufferedReader(new FileReader("files/events.txt"));
        ArrayList<String> fileContent = new ArrayList<String>();
        String line = br.readLine();
        
        while(line != null) {
        	fileContent.add(line);
        	line = br.readLine();
        }
        
        br.close();
        
        for(int i = 0; i < fileContent.size(); i += 2) {
        	String name = fileContent.get(i);
        	String[] otherInfo = fileContent.get(i + 1).split(" ");

        	//One-time event info
        	if(otherInfo.length == 3) {
        		String[] dateComponents = otherInfo[0].split("/");
        		String date = dateComponents[2] + "-" + dateComponents[0] + "-" + dateComponents[1];
        		LocalDate d = LocalDate.parse(date);
        		
        		String startingTime = otherInfo[1];
        		LocalTime l1 = LocalTime.parse(startingTime);
        		
        		String endingTime = otherInfo[2];
        		LocalTime l2 = LocalTime.parse(endingTime);
        		
        		Event e = new Event(name, d, l1, l2);
        		c.add(e);
        	}
        	
        	//Recurring event info
        	else if(otherInfo.length == 5) {
        		String daysOfWeek = otherInfo[0];
        		
        		String startingTime = otherInfo[1];
        		LocalTime l1 = LocalTime.parse(startingTime);
        		
        	    String endingTime = otherInfo[2];
        		LocalTime l2 = LocalTime.parse(endingTime);
        		
        		String[] dateComponents1 = otherInfo[3].split("/");
        		String startDate = dateComponents1[2] + "-" + dateComponents1[0] + "-" + dateComponents1[1];
        		LocalDate sd = LocalDate.parse(startDate);
        		
        		String[] dateComponents2 = otherInfo[4].split("/");
        		String endDate = dateComponents2[2] + "-" + dateComponents2[0] + "-" + dateComponents2[1];
        		LocalDate ed = LocalDate.parse(endDate);
        		
        		Event e = new Event(name, daysOfWeek, l1, l2, sd, ed);
        		c.add(e);
        	}
        }
        
        System.out.println("\nAll events have been loaded!");
		
		//Read input from user with a loop
		System.out.println("\nSelect one of the following main menu options: ");
		System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
		
		while(sc.hasNextLine()) {
			String option = sc.nextLine().toLowerCase();
			
			//View - view events on a day or a month
			if(option.equals("v")) {
				System.out.print("\n[D]ay view or [M]onth view? ");
				String vMode = sc.next().toLowerCase();
				
				//Day view - view today's date along with any events scheduled for the day
				if(vMode.equals("d")) {
					formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
					System.out.println("\n" + formatter.format(currentDate));
					ArrayList<Event> todaysEvents = c.onThisDay(currentDate);
					
					if(todaysEvents.size() == 0) {
						System.out.println("There are no events scheduled for today.");
					}
					
					for(int i = 0; i < todaysEvents.size(); i++) {
						Event e = todaysEvents.get(i);
						System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
					}
					
					System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
					
					while(sc.hasNext()) {
						String vOption = sc.next().toLowerCase();
						
						//Previous - view previous day's events
						if(vOption.equals("p")) {
							currentDate = currentDate.minusDays(1);
							
							formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
							System.out.println("\n" + formatter.format(currentDate));
							ArrayList<Event> prevEvents = c.onThisDay(currentDate);
							
							if(prevEvents.size() == 0) {
								System.out.println("There are no events scheduled for this day.");
							}
							
							for(int i = 0; i < prevEvents.size(); i++) {
								Event e = prevEvents.get(i);
								System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
							}
							
							System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
						}
						
						//Next - view next day's events
						else if(vOption.equals("n")) {
							currentDate = currentDate.plusDays(1);
							
							formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
							System.out.println("\n" + formatter.format(currentDate));
							ArrayList<Event> nextEvents = c.onThisDay(currentDate);
							
							if(nextEvents.size() == 0) {
								System.out.println("There are no events scheduled for this day.");
							}
							
							for(int i = 0; i < nextEvents.size(); i++) {
								Event e = nextEvents.get(i);
								System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
							}
							
							System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
						}
						
						//Go back - return to main menu
						else if(vOption.equals("g")) {
							System.out.println("\nSelect one of the following main menu options: ");
							System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
							break;
						}
					}
					currentDate = LocalDate.now();
				}
				
				//Month view - view the current month and days on which there are events scheduled
				else if(vMode.equals("m")) {
					printEventsCalendar(currentDate,c);
					System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
					
					while(sc.hasNext()) {
						String vOption = sc.next().toLowerCase();
						
						//Previous - view previous month's events
						if(vOption.equals("p")) {
							currentDate = currentDate.minusMonths(1);
							printEventsCalendar(currentDate, c);
							
							System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
						}
						
						//Next - view next month's events
						else if(vOption.equals("n")) {
							currentDate = currentDate.plusMonths(1);
							printEventsCalendar(currentDate, c);
							
							System.out.print("\n[P]revious or [N]ext or [G]o back to main menu? ");
						}
						
						//Go back - return to main menu
						else if(vOption.equals("g")) {
							System.out.println("\nSelect one of the following main menu options: ");
							System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
							break;
						}
					}
				}
				currentDate = LocalDate.now();
			}
			
			//Create - schedule one-time events
			else if(option.equals("c")) {
				System.out.print("\nName: ");
				String name = sc.nextLine();
				
				System.out.print("\nDate (MM/DD/YYYY): ");
				String[] dateComponents = sc.next().split("/");
				String date = dateComponents[2] + "-" + dateComponents[0] + "-" + dateComponents[1];
				LocalDate d = LocalDate.parse(date);
				
				System.out.print("\nStart time (24-hours, 00:00): ");
				String st = sc.next();
				LocalTime l1 = LocalTime.parse(st);
				
				System.out.print("\nEnd time (24-hours, 00:00): ");
				String et = sc.next();
				LocalTime l2 = LocalTime.parse(et);
				
				Event e = new Event(name,d,l1,l2);
				
				//Check for conflict
				LocalDate newDate = e.getStartDate();
				TimeInterval ti = e.getTimeInterval();
				
				Boolean conflict = false;
				ArrayList<Event> onSameDay = c.onThisDay(newDate);
				
				for(int i = 0; i < onSameDay.size(); i++) {
					Event e2 = onSameDay.get(i);
					TimeInterval otherTi = e2.getTimeInterval();
					if(ti.overlap(otherTi) == true) {
						System.out.println("\nEvent not added due to it overlapping with another event");
						conflict = true;
						break;
					}
				}
				if(conflict == false) {
					c.add(e);
					System.out.println("\nEvent created.");
				}
				
				System.out.println("\nSelect one of the following main menu options: ");
				System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
			}
			
			//Go to - displays all the event on the given date in View [Day] mode
			else if(option.equals("g")) {
				System.out.print("\nEnter your date (MM/DD/YYYY): ");
				String[] dateComponents = sc.next().split("/");
        		String date = dateComponents[2] + "-" + dateComponents[0] + "-" + dateComponents[1];
        		LocalDate d = LocalDate.parse(date);
        		
        		formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
				System.out.println("\n" + formatter.format(d));
				ArrayList<Event> daysEvents = c.onThisDay(d);
				
				if(daysEvents.size() == 0) {
					System.out.println("There are no events scheduled on this day.");
				}
				
				for(int i = 0; i < daysEvents.size(); i++) {
					Event e = daysEvents.get(i);
					System.out.println(e.getName() + ": " + e.getTimeInterval().toString());
				}
				
				System.out.println("\nSelect one of the following main menu options: ");
				System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
			}
			
			//Event list - browse scheduled events
			else if(option.equals("e")) {
				System.out.println(c.view());
				
				System.out.println("\nSelect one of the following main menu options: ");
				System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
			}
			
			//Delete - delete an event
			else if(option.equals("d")) {
				System.out.print("Delete [S]elected  [A]ll  [DR]? ");
				String dOption = sc.next().toLowerCase();
				
				//Selected - deletes a one time event with a given date and name
				if(dOption.equals("s")) {
					System.out.print("\nDate of the event(MM/DD/YYYY): ");
					String[] dateComponents = sc.next().split("/");
					sc.nextLine();
					String date = dateComponents[2] + "-" + dateComponents[0] + "-" + dateComponents[1];
					LocalDate d = LocalDate.parse(date);
					
					formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					System.out.println("\n" + d);
					for(int i = 0; i < c.getOneTimeList().size(); i++) {
						Event e = c.getOneTimeList().get(i);
						if(e.getStartDate().equals(d))
							System.out.println("  " + formatter.format(e.getStartDate()) + " " + e.getTimeInterval() + " " + e.getName());
					}
					
					System.out.print("\nName of the event to be deleted: ");
					String name = sc.nextLine();
					boolean found = false;
					
					for(int i = 0; i < c.getOneTimeList().size(); i++) {
						Event e = c.getOneTimeList().get(i);
						String eventName = e.getName().toLowerCase();
						if(eventName.equals(name)) {
							c.remove(e);
							found = true;
							System.out.println("\nEvent deleted");
						}
					}
					
					if(found == false)
						System.out.print("\nEvent with that name was not found.");
					
					System.out.println("\nSelect one of the following main menu options: ");
					System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
				}
				
				//All - deletes all the one-time events on the given date
				else if(dOption.equals("a")) {
					System.out.print("\nDate of the event(MM/DD/YYYY): ");
					String[] dateComponents = sc.next().split("/");
					sc.nextLine();
					String date = dateComponents[2] + "-" + dateComponents[0] + "-" + dateComponents[1];
					LocalDate d = LocalDate.parse(date);
					
					ArrayList<Event> daysEvents = c.onThisDay(d);
					for(int i = 0; i < daysEvents.size(); i++) {
						Event e = daysEvents.get(i);
						c.remove(e);
					}
					
					System.out.println("\nAll events on this date have been deleted");
					System.out.println("\nSelect one of the following main menu options: ");
					System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
				}
				
				//Delete recurring - deletes all instances of a recurring event
				else if(dOption.equals("dr")) {
					sc.nextLine();
					System.out.print("\nName of the event to be deleted: ");
					String name = sc.nextLine().toLowerCase();
					for(int i = 0; i < c.getRecurringList().size(); i++) {
						Event e = c.getRecurringList().get(i);
						if(e.getName().toLowerCase().equals(name))
							c.remove(e);
					}
					
					for(int i = 0; i < c.getList().size(); i++) {
						Event e = c.getList().get(i);
						if(e.getName().toLowerCase().equals(name))
							c.remove(e);
					}
				
					System.out.println("\nAll instances of event deleted");
					System.out.println("\nSelect one of the following main menu options: ");
					System.out.println("[V]iew by  [C]reate [G]o to  [E]vent list  [D]elete  [Q]uit");
				}
			}
			
			//Quit - saves the current events in output.txt and terminates
			else if(option.equals("q")) {
				FileWriter output = new FileWriter("files/output.txt");
				formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
				for(int i = 0; i < c.getList().size(); i++) {
					Event e = c.getList().get(i);
					output.write("\n" + e.getName());
					if(e.isOneTime() == true)
						output.write("\n" + formatter.format(e.getStartDate()) + " " + e.getTimeInterval().toString());
					else if(e.isOneTime() == false)
						output.write("\n" + e.getDaysOfWeek() + " " + e.getTimeInterval().toString() 
									 + " " + formatter.format(e.getStartDate()) + " " + formatter.format(e.getEndDate()));
				}
				output.close();
				System.out.println("Goodbye!");
				System.exit(0);
			}
		}
	}
	
	/*
	 * Helper method to print out calendar with brackets indicating the current date
	 * 
	 * @param c the user's current date
	 */
	private static void printCalendar(LocalDate c) {
		
		// Display month, year, and days of the week
		String header = c.getMonth() + " " + c.getYear() + "\nSu Mo Tu We Th Fr Sa";
		
		String output = "\n";
		String[] template = {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "};

		for(int i = 1; i <= c.lengthOfMonth(); i++) {
			//Create a day string and format it depending on if it's the current day
			String day = "";
			if(i == c.getDayOfMonth()) 
				day += "[" + i + "] ";
			else {
				if(i < 10)
					day = i + "  ";
				else
					day = i + " ";
			}
			
			//Get the DayOfWeek value 1-7
			LocalDate x = LocalDate.of(c.getYear(), c.getMonth(), i); 
			int dayOfWeek = x.getDayOfWeek().getValue();
			
			//Place the day string into template[DayOfWeek]
			template[dayOfWeek] = day;
			
			if(dayOfWeek == 7) {
				template[7] = "";
				for(int j = 0; j <= 7; j++) {
					output += template[j];
					template[j] = "";
				}
				output += "\n";
				template[0] = day;
			}
		}
		//Add any straggling dates to the output
		for(int j = 0; j <=7; j++) {
			output += template[j];
		}
		String s = header + output;
		System.out.println(s);
	}
	
	/*
	 * Helper method to print out calendar with brackets indicating any days with scheduled events
	 * 
	 * @param c the user's current date
	 * @param cal the calendar that contains all the user's scheduled events
	 */
	private static void printEventsCalendar(LocalDate c, MyCalendar cal) {
		// Display month, year, and days of the week
		String header = "\n" + c.getMonth() + " " + c.getYear() + "\nSu Mo Tu We Th Fr Sa";
				
		String output = "\n";
		String[] template = {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "};;
		
		for(int i = 1; i <= c.lengthOfMonth(); i++) {
			//Create a day string and format it depending on if there is an event that day
			String day = "";
			LocalDate currentDate = LocalDate.of(c.getYear(), c.getMonthValue(), i);
			ArrayList<Event> events = cal.onThisDay(currentDate);
				
			if(events.size() != 0) 
				day += "{" + i + "} ";
			else {
				if(i < 10)
					day = i + "  ";
				else
					day = i + " ";
			}
				
			//Get the DayOfWeek value 1-7
			LocalDate x = LocalDate.of(c.getYear(), c.getMonth(), i); 
			int dayOfWeek = x.getDayOfWeek().getValue();
					
			//Place the day string into template[DayOfWeek]
			template[dayOfWeek] = day;
					
			if(dayOfWeek == 7) {
				template[7] = "";
				for(int j = 0; j <= 7; j++) {
					output += template[j];
					template[j] = "";
				}
				output += "\n";
				template[0] = day;
			}
		}
		
		//Add any straggling dates to the output
		for(int j = 0; j <=7; j++) {
			output += template[j];
		}
		String s = header + output;
		System.out.println(s);
	}
}