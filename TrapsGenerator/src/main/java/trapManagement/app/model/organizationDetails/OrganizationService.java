package trapManagement.app.model.organizationDetails;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trapManagement.app.connections.ConnectionsToServices;
import trapManagement.app.dal.OrganizationDetailsDao;
import trapManagement.app.model.fakeTrapUsers.FakeUser;
import trapManagement.app.trapsGenerator.TrapsGenerator;

@Service
public class OrganizationService {

	private OrganizationDetailsDao oraganizationDao;
	private ArrayList<FakeUser> fakeUsersHttp;
	private ArrayList<FakeUser> fakeUsersFtp;
	private ConnectionsToServices conncetionsToServices;
	private TrapsGenerator trapGenerator;
	
	@Autowired
	public void setOrganizationDat(OrganizationDetailsDao organizationDao, ConnectionsToServices conncetionsToServices, TrapsGenerator trapsGenerator) {
		this.oraganizationDao = organizationDao;
		fakeUsersHttp = new ArrayList<FakeUser>();
		fakeUsersFtp = new ArrayList<FakeUser>();
		this.conncetionsToServices = conncetionsToServices;
		this.trapGenerator = trapsGenerator;
	}
	
	public OrganizationDetails createOrganizationUser(OrganizationDetails organizationDetails) {	
		
		System.out.println("\n\nFake users before: " + organizationDetails.getFakeUsers());
		initOrganizationUsers(organizationDetails);
		System.out.println("\n\nFake users after: " + organizationDetails.getFakeUsers());
		
//		this.conncetionsToServices.sendFakeUsersToHttp(fakeUsersHttp);
//		this.conncetionsToServices.sendFakeUsersToFtp(fakeUsersFtp);
		
		this.trapGenerator.setFakeUsers(organizationDetails.getFakeUsers());
		
		System.err.println("\n\nI'm done...sending to controller...\nThread: " + Thread.currentThread() + "\n");
//		this.trapGenerator.createTraps();
		return this.oraganizationDao.save(organizationDetails);
	}
	
	private void generateUserPassWord(FakeUser user) {
		final int MAX_PASS_LEN = 12;
		final int MIN_PASS_LEN = 8;
		
		String fName = user.getFirstName();
		String lName = user.getLastName();
		
		Random random = new Random();
		int randomPasswordLen = random.nextInt((MAX_PASS_LEN - 2 - MIN_PASS_LEN) + 1) + MIN_PASS_LEN;
		
		StringBuilder password = new StringBuilder().append(fName.charAt(0)).append(lName.charAt(0));
		
		System.err.println("TEst.generateUserPassWord()\nUser name: " + user.getFirstName() + "\nfirst letters: " + password );
		
		for(int i = 0; i < randomPasswordLen; i++) {
			password.append(Integer.toString(random.nextInt(10)));
		}
		
		user.setPassword(password.toString());
		
		System.err.println("\n\nUser: " + user + "\n");
		
	}
	
	
	
	private void initOrganizationUsers(OrganizationDetails organizationDetails) {
		List<FakeUser> users = organizationDetails.getFakeUsers();
		int numOfUsers = users.size();		
		
		for(int i = 0; i < numOfUsers; i++) {
			
			FakeUser fakeUser = users.get(i);
			generateUserPassWord(fakeUser);
			generateUserName(fakeUser, organizationDetails.getEmailPostfix());
			
			if(i < numOfUsers / 2) {
				fakeUser.setDedicationServer("HTTP");
				fakeUsersHttp.add(fakeUser);
			}

			else {
				fakeUser.setDedicationServer("FTP");
				fakeUsersFtp.add(fakeUser);
			}
				
			
		}
	}

	private void generateUserName(FakeUser fakeUser, String emailPostfix) {
		String fName = fakeUser.getFirstName();
		String lName = fakeUser.getLastName();
		
		StringBuilder userName = new StringBuilder().append(fName).append(".");
		
		Random random = new Random();
		int randomLastNameLen = random.nextInt(lName.length()) + 1;
		
		userName.append(lName.substring(0, randomLastNameLen)).append("@" + emailPostfix);
		 
		fakeUser.setUserName(userName.toString());
		
		System.err.println("\n\nUser after setUserName: " + fakeUser + "\n");
	}
	
	
	
	
	
}
