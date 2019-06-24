package trapManagement.app.trapsGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import trapManagement.app.model.fakeTrapUsers.FakeUser;

@SuppressWarnings("restriction")
@Component
public class TrapsGenerator {

	private ArrayList<FakeUser> httpFakeUsers;
	private int httpUsersCounter = 0;
	private ArrayList<FakeUser> ftpFakeUsers;
	private int ftpUsersCounter = 0;
	private ArrayList<FakeUser> commonFakeUsers;
	private int commonUsersCounter = 0;

	private String sourcerDir;
	private String targetDir;

	private final String HTML_TRAP = "htmlTrap/afekaLogin.html";
	private final String USERNAME_URL_FILE = "login.txt";
	private final String HTML_PWD_TYPE_TRAP = "htmlPwdTypeTrap/loginPage.html";

	private final String ZIPPED_IMAGE_PATH = "zippedImage/";
	private final String ZIP_PWD_FILE = "z_crypt.txt";
	private final String ZIP_PWD_IMG = "ftp_creds.png";
	private final String ZIP_FINAL_NAME = "ftp_creds.zip";
	
	private final String FTP_BATCH_FILE = "ftp_connect.bat";
	

	private Environment env;
	
	@Value("${files.traps.sourceDir:trapsSources/}")
	public void setSourceDir(String sourceDir) {
		this.sourcerDir = sourceDir;
	}
	
	@Value("${files.traps.targetDir:/traps/}")
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	
	private enum ServerType {
		HTTP, FTP, COMMON
	};

	public TrapsGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public void setEnvironment(Environment env) {
		this.env = env;
	}

	public void setFakeUsers(ArrayList<FakeUser> httpUsers, ArrayList<FakeUser> ftpUsers,
			ArrayList<FakeUser> commonUsers) {
		this.httpFakeUsers = httpUsers;
		this.ftpFakeUsers = ftpUsers;
		this.commonFakeUsers = commonUsers;
	}
	
	public String getAllTrapsZipFileName() throws URISyntaxException {
//		URL root = getClass().getProtectionDomain().getCodeSource().getLocation();
//		String root = System.getProperty("user.dir");
		String AllTrapsZipFile = getTargetTrapsContainingDirectory(this.env.getProperty("files.allTraps.zip"));
		System.err.println("\n\nROOT: " + AllTrapsZipFile);
		return AllTrapsZipFile;
	}

	@Async
	public void createTraps() {
		System.err.println("TrapsGenerator.createTraps()\nFake Users: " + this.httpFakeUsers + "\nThread: "
				+ Thread.currentThread() + "\n");
		System.err.println("\n\nUSER DIR: " + System.getProperty("user.dir"));

		try {
			plantUsernameInHttpServiceLoginPage();
			htmlPasswordTypeExplotation();
			userNameUrlTextFile();
			createLockedZipWithImage();
			createFtpConnectionBatFile();
			createTrapsZipFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private String getTargetTrapsContainingDirectory(String relativePath) {
		return System.getProperty("user.dir") + relativePath;
	}

	private void writeToHtmlFile(String targetFileName, Document doc) throws IOException {
		File htmlTargetFile = openFile(targetFileName);
		
		PrintWriter pw = new PrintWriter(htmlTargetFile);
		pw.print(doc.toString());
		pw.close();
		System.out.println(doc.toString());
	}
	
//	private InputStream getStreamOfExistingFile(String srcFile) {
//		InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(srcFile);
//	}

	//open the original html file, parse it with Jsoup, change the element - input.
	private Document manipulateUserNameInHtmlFileById(String srcFileName, String id) throws IOException, URISyntaxException {
//		File htmlSrcFile = openFile(srcFileName);
//		getClass().getResource(srcFileName).toExternalForm().replaceFirst("file:/", "")
		System.out.println("\n\nSOMETHING: " + ClassLoader.getSystemClassLoader().getResource(srcFileName) + "\nSOMETHING 2: " + getClass().getResource(srcFileName)
				+ "\nSOMETHING 3: " + TrapsGenerator.class.getResource(srcFileName));
		
//		System.err.println("\n\nHTML FILE NAME: " + srcFileName + "\n\nResource: " + ClassLoader.getSystemClassLoader().getResource(srcFileName).getFile());

//		File file = new File(TrapsGenerator.class.getResource(srcFileName).getFile().replaceFirst("file:/", ""));
		InputStream inputHtml = TrapsGenerator.class.getResourceAsStream(srcFileName);
//		System.err.println("\n\nFILE: " + file);
//		Document doc = Jsoup.parse(file, "UTF-8"); 
		Document doc = Jsoup.parse(inputHtml, "UTF-8", TrapsGenerator.class.getResource(srcFileName).toURI().toString());
		System.err.println("\n\nHTML: " + doc.toString());
		Element username = doc.getElementById(id);
		username.attr("value", getFakeUser(ServerType.HTTP).getUserName());
		return doc;
	}

	private Document manipulateUserNameAndPasswordInHtmlFileById(String srcFileName, String userNameId, String pwdId)
			throws IOException, URISyntaxException {
//		File htmlSrcFile = openFile(srcFileName);
		InputStream inputHtml = TrapsGenerator.class.getResourceAsStream(srcFileName);

		Document doc = Jsoup.parse(inputHtml, "UTF-8", TrapsGenerator.class.getResource(srcFileName).toURI().toString());
		FakeUser tmpUser = getFakeUser(ServerType.HTTP);

		Element username = doc.getElementById(userNameId);
		username.attr("value", tmpUser.getUserName());

		Element pwd = doc.getElementById(pwdId);
		pwd.attr("value", tmpUser.getPassword());

		return doc;
	}
	
	private File openFile(String fileName) throws IOException {
		
////		
////		if(fileName.charAt(0) == '/')
////			filePath = filePath.substring(1, filePath.length());
//		System.out.println("\nfilePath: " + filePath);
		
//		System.out.println("\n\nin OPEN FILE: " + fileName + "\nexists? : " +getClass().getResource(fileName).toExternalForm() + 
//				"\ngetPath: " + getClass().getResource(fileName).toExternalForm().replaceFirst("file:/", "") + "\nDoes file exists? : ");
//		File file;
//		URL filePath = TrapsGenerator.class.getResource(fileName);
//		System.err.println("\n\nFile Path: " + filePath);
//		if(filePath != null)
//			file = new File(filePath.getFile().replaceFirst("file:/", ""));
//		else {
//			String justFileName = Paths.get(fileName).getFileName().toString();
//			System.err.println("\n\nJust file name: "  + justFileName);
//			filePath = TrapsGenerator.class.getResource(fileName.replace(justFileName, ""));
//			System.err.println("\n\nFile Path: " + filePath.getFile().replaceFirst("file:/", ""));
//			file = new File(filePath.getFile().replaceFirst("file:/", "") + justFileName);
//			file.createNewFile();
//		}
		
		System.err.println("\n\nIN OPEN FILE, FILE NAME: " + (System.getProperty("user.dir") + fileName)); //+ "\n\nResource: " + ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		String rootDirFileName = getTargetTrapsContainingDirectory(fileName);//System.getProperty("user.dir") + fileName;
		File file = new File(rootDirFileName);//ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		if(!file.exists())
			file.createNewFile();
			
		
//		File file = ResourceUtils.getFile("classpath:" + fileName);
//		InputStream input = new FileInputStream(ResourceUtils.getFile("classpath:" + fileName));
//		System.out.println("\n\nInput: " + input.read());
//		System.err.flush();
		System.err.println(file.exists());
//		return null;
		
			
		return file;
	}

	private void plantUsernameInHttpServiceLoginPage() throws IOException, URISyntaxException {
		Document doc = manipulateUserNameInHtmlFileById(this.sourcerDir + this.HTML_TRAP, "input_1");
		writeToHtmlFile(this.targetDir + this.HTML_TRAP, doc);
	}

	private void htmlPasswordTypeExplotation() throws IOException, URISyntaxException {
		Document doc = manipulateUserNameAndPasswordInHtmlFileById(this.sourcerDir + this.HTML_PWD_TYPE_TRAP,
				"userNameInput", "pwdInput");
		writeToHtmlFile(this.targetDir + this.HTML_PWD_TYPE_TRAP, doc);

	}

	private FakeUser getFakeUser(ServerType type) {
		FakeUser user = null;

		if (type.equals(ServerType.HTTP)) {
			user = this.httpFakeUsers.get(httpUsersCounter++);
			this.httpUsersCounter %= this.httpFakeUsers.size();
		} else if (type.equals(ServerType.FTP)) {
			user = this.ftpFakeUsers.get(ftpUsersCounter++);
			this.ftpUsersCounter %= this.ftpFakeUsers.size();
		} else if (type.equals(ServerType.COMMON)) {
			user = this.commonFakeUsers.get(commonUsersCounter++);
			this.commonUsersCounter %= this.commonFakeUsers.size();
		}

		return user;

	}

	private void writeToTextFile(String fileName, String... paramsToWrite) throws IOException {
		File file = openFile(fileName);
		PrintWriter pw = new PrintWriter(file);

		for (String param : paramsToWrite) {
			pw.println(param);
		}

		pw.close();
	}

	private void userNameUrlTextFile() throws IOException {

		String urlFormat = "%s://%s:%s";
		String hostName = env.getProperty("http.server");
		String protocol = env.getProperty("http.protocol");
		String hostPort = env.getProperty("http.port");
		String hostPath = env.getProperty("http.path");

		String url = String.format(urlFormat, protocol, hostName, hostPort, hostPath);

		System.out.println("TrapsGenerator.userNameUrlTextFile()\nURL: " + url);

		writeToTextFile(this.targetDir + this.USERNAME_URL_FILE, getFakeUser(ServerType.COMMON).getUserName(), url);
	}

	private String createFileWithEncodedPassword(String passwordFileName) throws IOException {
		final String zipPasswrod = "zipper";
		String base64_paswword = Base64.encode(zipPasswrod.getBytes());
		writeToTextFile(passwordFileName, base64_paswword);
		return zipPasswrod;
	}

	private File createImageWithCredentialsAndLink() throws IOException {
		int width = 250;
		int height = 250;

		FakeUser user = getFakeUser(ServerType.FTP);

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();

		// fill all the image with white
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);

		// create a string with yellow
		g2d.setColor(Color.black);
		g2d.drawString(user.getUserName(), 50, 120);
		g2d.drawString(user.getPassword(), 50, 140);

		// Disposes of this graphics context and releases any system resources that it
		// is using.
		g2d.dispose();

		// Save as PNG
		File file = openFile(targetDir + ZIPPED_IMAGE_PATH + ZIP_PWD_IMG);//new File(targetDir + ZIPPED_IMAGE_PATH + ZIP_PWD_IMG);
//		if (!file.exists())
//			file.createNewFile();

		ImageIO.write(bufferedImage, "png", file);

		return file;
	}
	
	private void zipFile(String zipFileName, File fileToZip, Optional<String> password) throws ZipException, IOException {
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		
		password.ifPresent(pass -> {
			params.setEncryptFiles(true);
			params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			params.setPassword(pass);
		});
		
		ZipFile zipFile;
		String rootDirFileName = getTargetTrapsContainingDirectory(zipFileName);//System.getProperty("user.dir") + zipFileName;
		String justFileName = Paths.get(zipFileName).getFileName().toString();
		System.err.println("\n\nJust file name: "  + justFileName + "\nzipFileName: " + zipFileName + "\nrootDirFileName: " + rootDirFileName);
//		URL filePath = getClass().getResource(zipFileName.replace(justFileName, ""));
//		System.err.println("\n\nFile Path: " + filePath);
		
//		if(filePath == null) {
			File newDir = new File(getTargetTrapsContainingDirectory(zipFileName.replace(justFileName, "")));
			if(!newDir.exists())
				newDir.mkdir();
			
			System.err.println("\n\nWTF?! : " + newDir);
			
//			System.err.println("\n\nZIP: " + getClass().getResource(zipFileName.replace(justFileName, "")));	
//			filePath = getClass().getResource(zipFileName.replace(justFileName, ""));
			
			zipFile = new ZipFile(rootDirFileName);
			System.err.println("\n\nThe Zip Folder: " + zipFile + "\nFile to add to zip: " + fileToZip);
//			System.err.println("\n\nIN if : is file created: " + zipFile.);
//		}
//		else
//			zipFile = new ZipFile(filePath.toExternalForm().replaceFirst("file:/", "") + justFileName);
		
		if(fileToZip.isDirectory())
			zipFile.addFolder(fileToZip+"/", params);
		else
			zipFile.addFile(fileToZip, params);
	}



	private void createLockedZipWithImage() throws IOException, URISyntaxException {
		
//		ClassLoader classLoader = getClass().getClassLoader();
//		
//		InputStream in = getClass().getResourceAsStream("/traps/" + ZIPPED_IMAGE_PATH + ZIP_PWD_FILE); 
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		
//		System.out.println("\n\nBLAAA:::" + getClass().getResource("/traps/" + ZIPPED_IMAGE_PATH + ZIP_PWD_FILE));
		
//		System.out.println("\n\nPath: " + classgetResource(ZIP_PWD_FILE));

		String zipPwd = createFileWithEncodedPassword(targetDir + ZIPPED_IMAGE_PATH + ZIP_PWD_FILE);

		File imgToZip = createImageWithCredentialsAndLink();
		try {
			zipFile(targetDir + ZIPPED_IMAGE_PATH + ZIP_FINAL_NAME, imgToZip, Optional.of(zipPwd));
			imgToZip.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createFtpConnectionBatFile() throws IOException {
		String openFtpConnection = this.env.getProperty("ftp.trap.protocol") + " " + this.env.getProperty("ftp.trap.server");
		FakeUser ftpUser = getFakeUser(ServerType.FTP);
		String userName = "USER " + ftpUser.getUserName();
		String userPassword = "PWD " + ftpUser.getPassword();
		
		writeToTextFile(this.targetDir + this.FTP_BATCH_FILE, openFtpConnection, userName, userPassword);
	}
	
	private void createTrapsZipFile() throws ZipException, URISyntaxException, IOException {
		String trapsDirPath = getTargetTrapsContainingDirectory(this.targetDir);//System.getProperty("user.dir") + this.targetDir;
		File trapsDir = new File(trapsDirPath);
		System.err.println("\n\nCREATE ALL ZIP: " + trapsDir);
		if(trapsDir.exists() && trapsDir.isDirectory()) {
			zipFile(/*getAllTrapsZipFileName()*/this.env.getProperty("files.allTraps.zip"), trapsDir, Optional.empty());
		}
			
	}

}
