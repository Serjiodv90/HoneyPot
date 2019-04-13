package trapManagementServer;

import java.util.ArrayList;

public interface JsonObserver {
	public void notifyJsonSaved(ArrayList<RequestFormat> reqArrList);
}
