package trapManagement.app.model.organizationDetails;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private ArrayList<FakeUser> commonCredentials;
	private ConnectionsToServices conncetionsToServices;
	private TrapsGenerator trapGenerator;
	
	@Autowired
	public void setOrganizationDat(OrganizationDetailsDao organizationDao, ConnectionsToServices conncetionsToServices, TrapsGenerator trapsGenerator) {
		this.oraganizationDao = organizationDao;
		fakeUsersHttp = new ArrayList<>();
		fakeUsersFtp = new ArrayList<>();
		commonCredentials = new ArrayList<>();
		this.conncetionsToServices = conncetionsToServices;
		this.trapGenerator = trapsGenerator;
	}
	
	public String getTrapsDownloadPath() {
		try {
			return this.trapGenerator.getAllTrapsZipFileName();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OrganizationDetails createOrganizationUser(OrganizationDetails organizationDetails) {	
		
		initOrganizationUsers(organizationDetails);		
		this.conncetionsToServices.sendFakeUsersToHttp((ArrayList<FakeUser>) Stream.concat(this.fakeUsersHttp.stream(), this.commonCredentials.stream())
																.collect(Collectors.toList()));					
		
		this.conncetionsToServices.sendFakeUsersToFtp((ArrayList<FakeUser>) Stream.concat(this.fakeUsersFtp.stream(), this.commonCredentials.stream())
															.collect(Collectors.toList()));
		
		this.trapGenerator.setFakeUsers(this.fakeUsersHttp, this.fakeUsersFtp, this.commonCredentials);
		
		
		this.trapGenerator.createTraps();
		return this.oraganizationDao.save(organizationDetails);
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
		
		setCommonCredentialsList(organizationDetails.getEmailPostfix());

	}
	
	
	
	private void generateUserPassWord(FakeUser user) {
		final int MAX_PASS_LEN = 12;
		final int MIN_PASS_LEN = 8;
		
		String fName = user.getFirstName();
		String lName = user.getLastName();
		
		Random random = new Random();
		int randomPasswordLen = random.nextInt((MAX_PASS_LEN - 2 - MIN_PASS_LEN) + 1) + MIN_PASS_LEN;
		
		StringBuilder password = new StringBuilder().append(fName.charAt(0)).append(lName.charAt(0));
				
		for(int i = 0; i < randomPasswordLen; i++) {
			password.append(Integer.toString(random.nextInt(10)));
		}
		
		user.setPassword(password.toString());
				
	}
	
	private void generateUserName(FakeUser fakeUser, String emailPostfix) {
		String fName = fakeUser.getFirstName();
		String lName = fakeUser.getLastName();
		
		StringBuilder userName = new StringBuilder().append(fName).append(".");
		
		Random random = new Random();
		int randomLastNameLen = random.nextInt(lName.length()) + 1;
		
		userName.append(lName.substring(0, randomLastNameLen)).append("@" + emailPostfix);
		 
		fakeUser.setUserName(userName.toString());
		
	}
	
	private void setCommonCredentialsList(String emailPostfix) {
		String userNameAndPwd_1 = "admin";
		String userNameAndPwd_2 = "root";
		
		this.commonCredentials.add(new FakeUser(userNameAndPwd_1 + "@" +emailPostfix, userNameAndPwd_1));
		this.commonCredentials.add(new FakeUser(userNameAndPwd_2 + "@" +emailPostfix, userNameAndPwd_2));
	}
	
	
	
}
