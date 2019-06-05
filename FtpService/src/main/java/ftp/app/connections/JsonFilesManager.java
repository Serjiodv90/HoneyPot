package ftp.app.connections;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JsonFilesManager implements JsonObserver {
	
	private JsonDelegatorConnection jsonToMonitorServer;
	private ArrayList<RequestFormat> reqArrList;
//	private Gson gson;
	private String jsonPath;
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	private static JsonFilesManager jsonFileManager = null;
	
	public static JsonFilesManager getInstance() {
		if(jsonFileManager == null)
			jsonFileManager = new JsonFilesManager();
		return jsonFileManager;
	}
	
	private JsonFilesManager() {
		reqArrList = new ArrayList<RequestFormat>();
//		gson = new Gson();
		//httpInterceptor = HttpRequestsInterceptor.getInstance();
		System.out.println("In c'tor");
 	}
	 
	public void manage() {
		jsonPath = JsonSave.JSON_PATH;
//		File file = new File(httpJson);
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//		System.out.println("Last Modified: " + sdf.format(file.lastModified()));
//		if(Files.exists(Paths.get(httpJson))) {
//			FileReader reader = null;
//			try {
//				reader = new FileReader(httpJson);
//				Files.getLastModifiedTime(Paths.get(httpJson));
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			reqArrList = gson.fromJson(reader, reqArrList.getClass());
//			System.out.println(reqArrList);
//		}
		
		try (WatchService service = FileSystems.getDefault().newWatchService()){
			Map<WatchKey, Path> keyMap = new HashMap<>();
			Path path = Paths.get(jsonPath);
			keyMap.put(path.register(service, StandardWatchEventKinds.ENTRY_MODIFY), path);
			WatchKey watchKey;
			System.out.println("In try");
			do {
				System.out.println("In do");
				watchKey = service.take();
				Path eventDir = keyMap.get(watchKey);
				
				for(WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					Path eventPath = (Path)event.context();
					System.out.println(eventDir + ": " + kind + ": " + eventPath);
				}
			} while(watchKey.reset());
			System.out.println("After while");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void notifyJsonSaved(List<RequestFormat> reqArrList) {
		System.out.println("In Manager- Saved!!");
		JsonDelegatorConnection connection = new JsonDelegatorConnection();
		connection.sendJson(reqArrList);
		
	}
	
}
