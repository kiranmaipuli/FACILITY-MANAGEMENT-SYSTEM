package test.mac_repair.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import mac_repair.data.FacilityDAO;
import mac_repair.model.Facility;
import mac_repair.model.FacilityErrorMessages;


@RunWith(JUnitParamsRunner.class)
public class FacilityTest {

	Facility facility;
	FacilityErrorMessages facilityErrorMsg;
	
	@Before
	public void setUp() throws Exception {
	//	facility = new Facility();
		facilityErrorMsg = new FacilityErrorMessages();
	}

	
	
	@Test
	@FileParameters("src/test/mac_repair/model/facilityTest.csv")
	public void testFacility(int testCaseNumber, String facilityType, String facilityName, String facilityInterval, String facilityDuration, String facilityVenue,
			 String facilityNameError, String facilityTypeError, String facilityIntervalError, String facilityDurationError, String facilityVenueError) 

	{ 
		facility = new Facility();
		facility = new Facility(facilityName, facilityType, facilityInterval, facilityDuration, facilityVenue);
		String ten = "";
		facilityErrorMsg = facility.validateFacility();
	
		
		facility.setFacilityName(facilityName);
		facility.setFacilityVenue(facilityVenue);
		facility.setFacilityDuration(facilityDuration);
		facility.setFacilityInterval(facilityInterval);
		facility.setFacilityType(facilityType);
		
//		facilityErrorMsg = facility.validateFacility();
//		System.out.println(facilityErrorMsg.getFacilityTypeError());
		
		assertEquals(facilityName, facility.getFacilityName());
		assertEquals(facilityType, facility.getFacilityType());
		assertEquals(facilityInterval, facility.getFacilityInterval());
		assertEquals(facilityDuration, facility.getFacilityDuration());
		assertEquals(facilityVenue, facility.getFacilityVenue());
		//assertEquals(showFacilityMessage, facility.getState());
		
		
		assertEquals(facilityNameError, facilityErrorMsg.getFacilityNameError());
		assertEquals(facilityTypeError, facilityErrorMsg.getFacilityTypeError());
		assertEquals(facilityIntervalError, facilityErrorMsg.getFacilityIntervalError());
		assertEquals(facilityDurationError, facilityErrorMsg.getFacilityDurationError());
		assertEquals(facilityVenueError, facilityErrorMsg.getFacilityVenueError());
		
		//System.out.println(FacilityDAO.getFacilitiesByFacilityType(facilityType).size());	
//		facilityErrorMsg.setErrorMsg();
//		assertEquals(errorMsg, facilityErrorMsg.getErrorMsg());
		
		
			}
}
/*import registration.model.FacilityMessage;
import registration.model.User;
import registration.model.UserError;

@RunWith(JUnitParamsRunner.class)
public class FacilityTest {
	Facility facility;
	FacilityMessage facilityMessage;
	@Before
	public void setUp() throws Exception {
		facility = new Facility();
		facilityMessage = new FacilityMessage();
		
	}
	@Test
	@FileParameters("src/test/FacilityAddTestCases.csv")
	public void testAdd(int testCaseNumber, String facilityName, String facilityType, String facilityInterval, String facilityDuration, String facilityVenue,
			String facilityNameMessage, String facilityTypeMessage, String facilityIntervalMessage, String facilityDurationMessage, String facilityVenueMessage, String errorMessage){
		facility.setFacilityName(facilityName);
		facility.setFacilityType(facilityType);
		facility.setFacilityInterval(facilityInterval);
		facility.setFacilityDuration(facilityDuration);
		facility.setFacilityVenue(facilityVenue);
		
		facilityMessage = facility.validateFacility();
		
		assertEquals(facilityNameMessage, facilityMessage.getFacilityNameError());
		assertEquals(facilityTypeMessage, facilityMessage.getFacilityTypeError());
		assertEquals(facilityIntervalMessage, facilityMessage.getFacilityIntervalError());
		assertEquals(facilityDurationMessage, facilityMessage.getFacilityDurationError());
		assertEquals(facilityVenueMessage, facilityMessage.getFacilityVenueError());
		
		assertEquals(errorMessage, facilityMessage.getErrorMsg());
		
				
	}
	
	
	

}*/
