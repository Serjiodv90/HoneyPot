package trapManagement.app.trapsGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class TrapsGenerator {

	private ArrayList<FakeUser> httpFakeUsers;
	private int httpUsersCounter = 0;
	private ArrayList<FakeUser> ftpFakeUsers;
	private int ftpUsersCounter = 0;
	private ArrayList<FakeUser> commonFakeUsers;
	private int commonUsersCounter = 0;

	private final String SOURCES_DIR = "./src/main/resources/trapsSources/";
	private final String TARGET_DIR = "./src/main/resources/traps/";

	private final String HTML_TRAP = "htmlTrap/afekaLogin.html";
	private final String USERNAME_URL_FILE = SOURCES_DIR + "login.txt";
	private final String HTML_PWD_TYPE_TRAP = "htmlPwdTypeTrap/loginPage.html";

	private final String ZIPPED_IMAGE_PATH = "zippedImage/";
	private final String ZIP_PWD_FILE = "z_crypt.txt";
	private final String ZIP_PWD_IMG = "ftp_creds.png";
	private final String ZIP_FINAL_NAME = "ftp_creds.zip";
	
	private final String FTP_BATCH_FILE = "ftp_connect.bat";
	
	private final String ALL_TRAPS_ZIP_FILE = "./src/main/resources/traps";

	private Environment env;

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

	@Async
	public void createTraps() {
		System.err.println("TrapsGenerator.createTraps()\nFake Users: " + this.httpFakeUsers + "\nThread: "
				+ Thread.currentThread() + "\n");

		try {
//			plantUsernameInHttpServiceLoginPage();
//			htmlPasswordTypeExplotation();
//			userNameUrlTextFile();
			createLockedZipWithImage();
//			createFtpConnectionBatFile();
			createTrapsZipFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writeToHtmlFile(String targetFileName, Document doc) throws IOException {
		File htmlTargetFile = openFile(targetFileName);

		PrintWriter pw = new PrintWriter(htmlTargetFile);
		pw.print(doc.toString());
		pw.close();
		System.out.println(doc.toString());
	}

	private Document manipulateUserNameInHtmlFileById(String srcFileName, String id) throws IOException {
		File htmlSrcFile = openFile(srcFileName);

		Document doc = Jsoup.parse(htmlSrcFile, "UTF-8");

		Element username = doc.getElementById(id);
		username.attr("value", getFakeUser(ServerType.HTTP).getUserName());
		return doc;
	}

	private Document manipulateUserNameAndPasswordInHtmlFileById(String srcFileName, String userNameId, String pwdId)
			throws IOException {
		File htmlSrcFile = openFile(srcFileName);

		Document doc = Jsoup.parse(htmlSrcFile, "UTF-8");
		FakeUser tmpUser = getFakeUser(ServerType.HTTP);

		Element username = doc.getElementById(userNameId);
		username.attr("value", tmpUser.getUserName());

		Element pwd = doc.getElementById(pwdId);
		pwd.attr("value", tmpUser.getPassword());

		return doc;
	}

	private File openFile(String fileName) throws IOException {
		File file = new File(fileName);

		if (!file.exists())
			file.createNewFile();
		return file;
	}

	private void plantUsernameInHttpServiceLoginPage() throws IOException {
		Document doc = manipulateUserNameInHtmlFileById(this.SOURCES_DIR + this.HTML_TRAP, "input_1");
		writeToHtmlFile(this.TARGET_DIR + this.HTML_TRAP, doc);
	}

	private void htmlPasswordTypeExplotation() throws IOException {
		Document doc = manipulateUserNameAndPasswordInHtmlFileById(this.SOURCES_DIR + this.HTML_PWD_TYPE_TRAP,
				"userNameInput", "pwdInput");
		writeToHtmlFile(this.TARGET_DIR + this.HTML_PWD_TYPE_TRAP, doc);

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

		writeToTextFile(this.USERNAME_URL_FILE, getFakeUser(ServerType.COMMON).getUserName(), url);
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
		File file = new File(TARGET_DIR + ZIPPED_IMAGE_PATH + ZIP_PWD_IMG);
		if (!file.exists())
			file.createNewFile();

		ImageIO.write(bufferedImage, "png", file);

		return file;
	}
	
	private void zipFile(String zipFileName, File fileToZip, Optional<String> password) throws ZipException {
		ZipParameters params = new ZipParameters();
		params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		
		password.ifPresent(pass -> {
			params.setEncryptFiles(true);
			params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			params.setPassword(pass);
		});
		
		ZipFile zipFile = new ZipFile(zipFileName);
		
		if(fileToZip.isDirectory())
			zipFile.addFolder(fileToZip, params);
		else
			zipFile.addFile(fileToZip, params);
	}



	private void createLockedZipWithImage() throws IOException {
		String zipPwd = createFileWithEncodedPassword(TARGET_DIR + ZIPPED_IMAGE_PATH + ZIP_PWD_FILE);

		File imgToZip = createImageWithCredentialsAndLink();
		try {
			zipFile(TARGET_DIR + ZIPPED_IMAGE_PATH + ZIP_FINAL_NAME, imgToZip, Optional.of(zipPwd));
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
		
		writeToTextFile(this.TARGET_DIR + this.FTP_BATCH_FILE, openFtpConnection, userName, userPassword);
	}
	
	private void createTrapsZipFile() throws ZipException {
		File trapsDir = new File(this.TARGET_DIR);
		if(trapsDir.exists() && trapsDir.isDirectory()) {
			zipFile("./src/main/resources/AllTraps.zip", trapsDir, Optional.empty());
		}
			
	}

}
