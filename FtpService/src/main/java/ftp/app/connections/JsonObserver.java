package ftp.app.connections;

import java.util.List;

public interface JsonObserver {
	public void notifyJsonSaved(List<RequestFormat> reqArrList);
}
