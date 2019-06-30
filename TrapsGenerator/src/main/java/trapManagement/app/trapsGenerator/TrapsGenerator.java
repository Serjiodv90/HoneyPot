package trapManagement.app.trapsGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
		String allTrapsZipFile;
		if(this.env.getProperty("service.machine").equalsIgnoreCase("localhost"))
			allTrapsZipFile = getTargetTrapsContainingDirectory(this.env.getProperty("files.allTraps.zip"));
		else 
			allTrapsZipFile = this.env.getProperty("files.allTraps.zip");

		return allTrapsZipFile;
	} 

	@Async
	public void createTraps() {

		try {
			plantUsernameInHttpServiceLoginPage();
			htmlPasswordTypeExplotation();
			userNameUrlTextFile();
			createLockedZipWithImage();
			createFtpConnectionBatFile();
			createTrapsZipFile();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
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
	}

	//open the original html file, parse it with Jsoup, change the element - input.
	private Document manipulateUserNameInHtmlFileById(String srcFileName, String id) throws IOException, URISyntaxException {
		InputStream inputHtml = TrapsGenerator.class.getResourceAsStream(srcFileName);
		Document doc = Jsoup.parse(inputHtml, "UTF-8", TrapsGenerator.class.getResource(srcFileName).toURI().toString());
		Element username = doc.getElementById(id);
		username.attr("value", getFakeUser(ServerType.HTTP).getUserName());
		inputHtml.close();
		return doc;
	}

	private Document manipulateUserNameAndPasswordInHtmlFileById(String srcFileName, String userNameId, String pwdId)
			throws IOException, URISyntaxException {
		InputStream inputHtml = TrapsGenerator.class.getResourceAsStream(srcFileName);

		Document doc = Jsoup.parse(inputHtml, "UTF-8", TrapsGenerator.class.getResource(srcFileName).toURI().toString());
		FakeUser tmpUser = getFakeUser(ServerType.HTTP);

		Element username = doc.getElementById(userNameId);
		username.attr("value", tmpUser.getUserName());

		Element pwd = doc.getElementById(pwdId);
		pwd.attr("value", tmpUser.getPassword());
		inputHtml.close();
		return doc;
	}

	private File openFile(String fileName) throws IOException {

		System.err.println("\n\nIN OPEN FILE, FILE NAME: " + (System.getProperty("user.dir") + fileName)); //+ "\n\nResource: " + ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		String rootDirFileName = getTargetTrapsContainingDirectory(fileName);//System.getProperty("user.dir") + fileName;
		File file = new File(rootDirFileName);//ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		if(!file.exists())
			file.createNewFile();

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
		File file = openFile(targetDir + ZIPPED_IMAGE_PATH + ZIP_PWD_IMG);

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
		File newDir = new File(getTargetTrapsContainingDirectory(zipFileName.replace(justFileName, "")));
		if(!newDir.exists())
			newDir.mkdir();

		zipFile = new ZipFile(rootDirFileName);
		if(fileToZip.isDirectory())
			zipFile.addFolder(fileToZip+"/", params);
		else
			zipFile.addFile(fileToZip, params);
	}



	private void createLockedZipWithImage() throws IOException, URISyntaxException {

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
		String trapsDirPath = getTargetTrapsContainingDirectory(this.targetDir);
		File trapsDir = new File(trapsDirPath);
		if(trapsDir.exists() && trapsDir.isDirectory()) {
			zipFile(this.env.getProperty("files.allTraps.zip"), trapsDir, Optional.empty());
		}

	}

}
