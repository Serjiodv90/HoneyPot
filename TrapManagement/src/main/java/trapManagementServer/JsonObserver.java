package trapManagementServer;

import java.util.ArrayList;

public interface JsonObserver {
	public abstract void notifyJsonSaved(ArrayList<RequestFormat> reqArrList);
}
