package test.mac_repair.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import mac_repair.model.Assignment;
import mac_repair.model.AssignmentMessage;

@RunWith(JUnitParamsRunner.class)
public class AssignmentTest {
	
	Assignment assignment;
	AssignmentMessage assignmentMessage;
	DateUtils dateUtils;
	//User user;

	@Before
	public void setUp() throws Exception {
		assignment = new Assignment();
		assignmentMessage = new AssignmentMessage();
		dateUtils = EasyMock.strictMock(DateUtils.class);
	}  

	@Test
	@FileParameters("src/test/mac_repair/model/AssignmentTestCases.csv")
	public void testAssignment(int testCaseNumber, String action, int marId, String assignedTo, String urgency, int estimate,String date, String marIdMessage
			, String assignedToMessage, String urgencyMessage, String estimateMessage, String errorMessage, String today, String dateErrorMessage, String description, String duration) {
		 
		EasyMock.expect(dateUtils.now()).andReturn(Date.valueOf(today));
		EasyMock.replay(dateUtils);
		
		Date todayDate = dateUtils.now();
		
		assignment.setMarId(marId);
		assignment.setAssignedTo(assignedTo);
		assignment.setAssignedDate(Date.valueOf(date));
		assignment.setUrgency(urgency);
		assignment.setEstimate(estimate);
		
		assignment.validateAssignment(action, assignmentMessage, todayDate,duration);
		
		assertEquals(0, assignment.getAssignmentId());
		assertEquals(marId, assignment.getMarId());
		assertEquals(assignedTo, assignment.getAssignedTo());
		assertEquals(Date.valueOf(date), assignment.getAssignedDate());
		assertEquals(urgency, assignment.getUrgency());
		assertEquals(estimate, assignment.getEstimate());
		
		assertEquals(marIdMessage, assignmentMessage.getMarIdMessage());
		assertEquals(assignedToMessage, assignmentMessage.getAssignedToMessage());
		assertEquals(urgencyMessage, assignmentMessage.getUrgencyMessage());
		assertEquals(estimateMessage, assignmentMessage.getEstimateMessage());
		assertEquals(dateErrorMessage, assignmentMessage.getDateErrorMessage());
		assertEquals(errorMessage, assignmentMessage.getErrorMessage());
		
	}

}
