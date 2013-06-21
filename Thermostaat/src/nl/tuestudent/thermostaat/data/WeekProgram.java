package nl.tuestudent.thermostaat.data;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import nl.tuestudent.thermostaat.CommunicationClass;
import nl.tuestudent.thermostaat.CommunicationClass.SubmitResult;
import android.util.Log;

public class WeekProgram implements CommunicationClass.SubmitResult {
	
	private HashMap<String, DayProgram> days = new HashMap<String, DayProgram>();
	String state;
	
	public WeekProgram() {
		getWeekProgram();
	}
	
	public void getWeekProgram() {
		new CommunicationClass(this, "weekProgram", "GET");
	}
	
	public DayProgram findDayProgram(String dayName) {
		return days.get(dayName);
	}


	private void parseWeekProgram(String contents) {
		
		state = "off";
		int ind = contents.indexOf('=');
		if(contents.charAt(ind + 2) == 'n') {
			state = "on";
		}
		
		//Using a real XML parser is lots of code ... using regexps instead
		String[] dayArray = contents.replaceAll("<week.+?\">","").split("(?=<day)");
		// newline at line 1 does not get deleted (and can't get it to work..) therefore I'll just ignore 
		// the first element of the array
		for(int i=1; i <8;i++) {
			String day = dayArray[i];
			String[] daySplit = dayArray[i].replaceAll("</day>", "").split("(?<=</.{3,6}>)|(?<=name=\".{2,9}\">)");
			String dayName = daySplit[0].replaceAll("(<day name=\")|((?<=name=\".{2,9})\">)", "");
			
			DayProgram dp = new DayProgram(dayName, daySplit);
			days.put(dayName, dp);
		}
	}
	
	@Override
	public void submitResult(String function, String method, String contents) {
		if(function.equals("weekProgram") && method.equals("GET")) {
			if(contents.equals("Error")) {
				Log.d("thermostaat",contents); //TODO iets doen
			} else {
				parseWeekProgram(contents);
			}
		}	
	}
	
	public String toXML() {
		String ret = "<week_program state = \"" + this.state + "\">\n";
		for(String day : days.keySet()) {
			DayProgram dp = days.get(day);
			ret += dp.toXML();
		}
		ret += "</week_program>\n";
		return ret;
	}
}
