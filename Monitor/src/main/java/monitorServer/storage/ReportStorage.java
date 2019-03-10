package monitorServer.storage;

import java.io.File;
import java.util.List;

public class ReportStorage {
	
	private String storageLink;
	private String localRepoPath;
	List<String> reportsToStore;
	
	
	public ReportStorage(String localRepoPath) {
		this.localRepoPath = localRepoPath;
	}
	
	
	
	public boolean storeReport() {
		return true;
	}
	
	public void addReportToStore(String fileName) {
		
	}
	
	private File findFileInDirectory(String fileName) {
		return null;
	}
	
	

}
