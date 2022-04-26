# Calendar-Application
The initial screen shows the current month looking like this. 
It also highlights today's date using a pair of brackets. 

 February 2021      
Su Mo Tu We Th Fr Sa  
    1  [2]  3  4  5  6  
 7  8  9 10 11 12 13  
14 15 16 17 18 19 20  
21 22 23 24 25 26 27  
28   

When the program starts, it loads events from events.txt and populates the calendar with them. 
The events.txt is a text file of an event that is prepared ahead of the program execution.

There are two different types of events the program manages: recurring and one-time.
A recurring event is one that is scheduled every week on the same day or days, 
such as a lecture that meets every Monday and Wednesday.
A one-time event is scheduled on a particular date, such as 10/15/21, and doesn't repeat.

In the events.txt file, each event takes up two lines.
Recurring event
The first line contains the name of the event which may contain spaces. 
The second line consists of a sequence of day abbreviations (SMTWRFA, upper or lower case) followed by a starting date and an ending date of the recurring event.
One time event
The first line contains the name of the event which may contain spaces. 
The second line consists of a date in the format mm/dd/yy, e.g. 3/22/21. 

There cannot be any spaces within a date description. The date description is followed by a starting time and an ending time. 
For the starting and ending times, only military 24-hour time will be used for simplicity. 
For example, 18:15 instead of 6:15 pm The minutes cannot be left off in which case zeros should be specified for the minutes, e.g. 14:00.

Here is a sample events.txt:

CS151 Lecture

TR 10:30 11:45 1/28/21 5/13/21

CS157C Lecture

MW 11:45 13:15 1/27/21 5/17/21

Interview at BigCorp 

9/28/21 9:30 11:30

Dentist appt

10/3/21 16:15 17:00

Course Committee Meeting

F 18:30 20:30  1/27/21 5/13/21

After loading the events, the program prompts "Loading is done!".

The program now displays a MAIN MENU with following options: View by, Create, Go to, Event list, Delete, and Quit. 

Select one of the following main menu options:
[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit

The user may enter one of the letters highlighted with a pair of the bracket to choose an option. 
The main menu will be displayed after each option is done. 

**[V]iew by**
The user can choose a Day or a Month view. 
If a Day view is chosen, the program prints today's date and displays the events scheduled for the day in order of start time.
If a Month view is chosen, the program displays the current month and highlights day(s) with a pair of brackets {} if any event scheduled on that day. 
After the selected view is displayed, the calendar gives the user three options: P, N, and G, where P, N, and M stand for Previous, Next and Go back to the main menu, respectively. 
The previous and next options allow the user to navigate the current view back and forth. 
If the day view was selected, the view goes back (P) and forth (N) by day. 
If the month view was chosen, the view goes back (P) and forth (N) by month. 

Here are sample runs:

[D]ay view or [M]view ? 

If the user selects D, then today's date is displayed along with scheduled events.

Thu, March 19, 2021
CS151 Lecture : 10:30 - 11:45 

[P]revious or [N]ext or [G]o back to the main menu ?  <-- The option menu allows the user to choose navigating the Day view or going back to the main menu

If the user selects M, then

 February 2021      
Su Mo Tu We Th Fr Sa  
    1  2  3  4  5  6  
 7  8  9 10 11 12 13  
14 {15} 16 17 18 19 20  
21 22 23 24 25 26 27  
28  

[P]revious or [N]ext or [G]o back to main menu ? 


**[C]reate**
The user can schedule an event. 
The calendar asks the user to enter the name, date, starting time, and ending time of an event. 
For simplicity, only one-time events are considered for the Create function. 
The program checks if a new event is a conflict with existing events. 
The following format is used to enter data:
Name: a string (doesn't have to be one word)
Date: MM/DD/YYYY
Starting time and ending time: 24 hour clock such as 06:00 for 6 AM and 15:30 for 3:30 PM.


**[G]o to**
The user enters a date in the form of MM/DD/YYYY, then the calendar displays the Day view of the requested date including an event scheduled on that day in the order of starting time.


**[E]vent list**
The user can browse scheduled events. 
The scheduled events are presented in two categories: One time events and Recurring events. 
Any canceled events will not show. 
One-time events are displayed in the order of starting date and starting time. 
Recurring events are presented in the order of starting date. 

An example presentation of events is as follows: 

ONE TIME EVENTS
2020
  Friday March 15 13:15 - 14:00 Dentist 
  Thursday April 25 15:00 - 16:00 Job Interview 
2021 
  ... 

RECURRING EVENTS
CS157C Lecture
MW 11:45 13:15 1/27/20 5/17/21
CS151 Lecture
TR 10:30 11:45 1/28/21 5/13/21


**[D]elete**
The user can delete an event from the Calendar. 
There are three different ways to delete an event: Selected, All and DeleteRecurring.
[S]elected: the user specifies the date and name of an ONE-TIME event. The specific one-time event will be deleted.
[A]ll: the user specifies a date and then all ONE-TIME events scheduled on the date will be deleted.
[DR]: the user specifies the name of a RECURRING event. The specified recurring event will be deleted. 
This will delete the recurring event throughout the calendar.

[S]elected  [A]ll   [DR] 
Here is a scenario of [S]elected as an example. 
If the user enters S, then the calendar asks for the date and displays all the events scheduled on that date. 
The program then asks the name of the event to be deleted and deletes the specified event. 
If there is no such event, the program prompts an error message.

Enter the date [dd/mm/yyyy]
03/15/2021

  13:15 - 14:00 Dentist 
  17:00 - 17:45 Piano Lesson

Enter the name of the event to delete: Dentist

**[Q]uit**
The program prompts "Good Bye", saves the current events in a file called output.txt, and terminates. 
