package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

import java.util.Calendar;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		//Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		//Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date(119,10,1));
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);
		DataStorage.eventData.put(event.getEventID(), event);
		
		//Create Event2
		Event event2 = new Event();
		event2.setEventID(2);
		Date event2_time = new Date(119,5,1);
		event2.setDate(event2_time);
		event2.setName("Event 2");
		Location location2 = new Location(-112, 67);
		event2.setLocation(location2);
		event2.setStudents(eventStudents);
		DataStorage.eventData.put(event2.getEventID(), event2);
		
	


	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}
	
	
	@Test
	void testUpdateEventName_Namelength_badCase() throws StudyUpException{
		int eventID = 1;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "This a great and fabulus Event 1, come and we welcome everyone");
		  });
	}
	@Test
	void testUpdateEventName_NoName_badCase() throws StudyUpException{
		int eventID = 1;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "");
		  });
	}
	
	
	@Test
	void testUpdateEventName_WrongEventID_badCase() throws StudyUpException{
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		  });
	}
	
	
	@Test
	void testGetActiveEvents_GoodCase() throws StudyUpException{
        List<Event> events = eventServiceImpl.getActiveEvents();
		
		for(Event i : events) {
			Assertions.assertFalse(i.getDate().before(new Date()));
		}
	}
	
	@Test
	void testGetActiveEvents_RetrivePastEvents_BadCase() throws StudyUpException{
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		
		Event event3 = new Event();
		event3.setEventID(3);
		Date event3_time = new Date();
		event3_time.setTime(10000);
		event3.setDate(event3_time);
		event3.setName("Event 2");
		Location location2 = new Location(-102, 57);
		event3.setLocation(location2);
		event3.setStudents(eventStudents);
		DataStorage.eventData.put(event3.getEventID(), event3);
		
		List<Event> events = eventServiceImpl.getActiveEvents();
		
		for(Event i : events) {
			Assertions.assertFalse(i.getDate().before(new Date()));
		}
		
		
		
	}
	
	@Test
	void testGetPastEvents_GoodCase() throws StudyUpException{
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		
		Event event3 = new Event();
		event3.setEventID(3);
		Date event3_time = new Date();
		event3_time.setTime(10000);
		event3.setDate(event3_time);
		event3.setName("Event 2");
		Location location2 = new Location(-102, 57);
		event3.setLocation(location2);
		event3.setStudents(eventStudents);
		DataStorage.eventData.put(event3.getEventID(), event3);
        List<Event> events = eventServiceImpl.getPastEvents();
		
		for(Event i : events) {
			Assertions.assertTrue(i.getDate().before(new Date()));
		}
	}
	
	@Test
	void testGetPastEvents_RetriveFutureEvents_badCase()throws StudyUpException {
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);
		
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		
		Event event3 = new Event();
		event3.setEventID(3);
		Date event3_time = new Date();
		event3_time.setTime(10000);
		event3.setDate(event3_time);
		event3.setName("Event 2");
		Location location2 = new Location(-102, 57);
		event3.setLocation(location2);
		event3.setStudents(eventStudents);
		DataStorage.eventData.put(event3.getEventID(), event3);
        List<Event> events = eventServiceImpl.getPastEvents();
		
		for(Event i : events) {
			Assertions.assertFalse(i.getDate().after(new Date()));
		}
	}
	
	@Test
	void testaddStudentToEvent_morethantwopeople_badCase() throws StudyUpException {
		int eventID = 1;
		//Create Student2
		Student student2 = new Student();
		student2.setFirstName("Tim");
		student2.setLastName("Yu");
		student2.setEmail("TimYu@email.com");
		student2.setId(2);
		
		//Create Student3
		Student student3 = new Student();
		student3.setFirstName("Amy");
		student3.setLastName("White");
		student3.setEmail("AmyWhite@email.com");
		student3.setId(3);
		
		eventServiceImpl.addStudentToEvent(student2,eventID);
		eventServiceImpl.addStudentToEvent(student3,eventID);
		List<Student> s=DataStorage.eventData.get(eventID).getStudents();
		boolean b=true;
		if (s.size()>2) {b=false;}
		assertEquals(true, b);
	}
	@Test
	void testaddStudentToEvent_NoEvent_badCase() throws StudyUpException {
		int eventID = 4;
		
		//Create Student2
				Student student2 = new Student();
				student2.setFirstName("Tim");
				student2.setLastName("Yu");
				student2.setEmail("TimYu@email.com");
				student2.setId(2);
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student2,eventID);
		  });
	}
	@Test
	void testaddStudentToEvent_Nostudent_GoodCase() throws StudyUpException {
		
		
		//Create Student2
		Student student2 = new Student();
		student2.setFirstName("Tim");
		student2.setLastName("Yu");
		student2.setEmail("TimYu@email.com");
		student2.setId(2);
				
				Event event3 = new Event();
				event3.setEventID(3);
				event3.setDate(new Date());
				event3.setName("Event 3");
				Location location = new Location(-112, 67);
				event3.setLocation(location);
				
				
				
				DataStorage.eventData.put(event3.getEventID(), event3);
				int eventID = event3.getEventID();
			
				
				eventServiceImpl.addStudentToEvent(student2,eventID);

				List<Student> s=DataStorage.eventData.get(eventID).getStudents();
				boolean b=true;
				if (s.size()>2) {b=false;}
				assertEquals(true, b);
	}
	@Test
	void testaddStudentToEvent_GoodCase() throws StudyUpException {
		int eventID = 1;
		//Create Student2
		Student student2 = new Student();
		student2.setFirstName("Tim");
		student2.setLastName("Yu");
		student2.setEmail("TimYu@email.com");
		student2.setId(2);
		

		eventServiceImpl.addStudentToEvent(student2,eventID);

		List<Student> s=DataStorage.eventData.get(eventID).getStudents();
		boolean b=true;
		if (s.size()>2) {b=false;}
		assertEquals(true, b);
	}
	@Test
	void testdeleteEvent_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.deleteEvent(eventID);
		assertEquals(null, DataStorage.eventData.get(eventID));
	}
	@Test
	void testdeleteEvent_badCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.deleteEvent(eventID);
		assertEquals(null, DataStorage.eventData.get(eventID));
	}
	
	
}
