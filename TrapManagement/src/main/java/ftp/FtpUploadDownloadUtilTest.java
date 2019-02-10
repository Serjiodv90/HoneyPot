package ftp;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;



public class FtpUploadDownloadUtilTest {

	
	private static final int FTP_PORT = 21;//2121;
	   private static final String FTP_HOST = "localhost";
	   private static final String FTP_HOME_DIR = "target/FtpHome";
	   private static final String FTPUSERSPROPS_FILE = "target/FtpUsers.properties";
	   private static final String READ_USER_NAME = "ReadUserName";
	   private static final String READ_USER_PWD = "ReadUserPwd";
	   private static final String WRITE_USER_NAME = "WriteUserName";
	   private static final String WRITE_USER_PWD = "WriteUserPwd";
	   private static FtpServer ftpServer;

//	   @BeforeClass
	   public static void startFtpServer () throws FtpException, IOException
	   {
		   ftpServer = FtpTestUtil. createFtpServer (FTP_PORT, FTP_HOME_DIR,
	            READ_USER_NAME, READ_USER_PWD, WRITE_USER_NAME, WRITE_USER_PWD, FTPUSERSPROPS_FILE, 0);
		   ftpServer.start ();
	   }
	   
//	   @AfterClass
	   public static void stoppFtpServer ()
	   {
	      // To transfer the FTP server from outside the test for a while
	      // ftp:// WriteUserName: WriteUserPwd @ localhost: 2121
	      //, the following line can be activated:
	       try {Thread.sleep (55000); } catch (InterruptedException e) {/* ok */} 
	      ftpServer.stop ();
	      ftpServer = null;
	   }
	   
//	   @Test
	   public static void testFtp () throws IOException
	   {
	      final String LOCAL_SRC_FILE = "TestSrc.txt";
	      final String LOCAL_DST_FILE = "target/TestDst.txt";
	      final String REMOTE_FILE = "Test.txt";

	      File testFile = new File(LOCAL_SRC_FILE);
	      if (!testFile.exists ())
	    	  testFile.createNewFile ();

	      // upload attempt without write permission must fail: 
	      FtpUploadDownloadUtil.upload(LOCAL_SRC_FILE, REMOTE_FILE, FTP_HOST, FTP_PORT,
	            READ_USER_NAME, READ_USER_PWD, false);

	      // Test upload with write permission: 
	      FtpUploadDownloadUtil.upload(LOCAL_SRC_FILE, REMOTE_FILE, FTP_HOST, FTP_PORT,
	            WRITE_USER_NAME, WRITE_USER_PWD, false);
	       new File (FTP_HOME_DIR, REMOTE_FILE) .exists ();

	      // Try Download:
	       FtpUploadDownloadUtil.download (LOCAL_DST_FILE, REMOTE_FILE, FTP_HOST, FTP_PORT,
	            READ_USER_NAME, READ_USER_PWD, false);
	      new File (LOCAL_DST_FILE) .exists ();

	      // Test Listing: 
	      String [] remoteFilenameList = FtpUploadDownloadUtil.list (FTP_HOST, FTP_PORT, READ_USER_NAME, READ_USER_PWD);
	      Arrays.asList (remoteFilenameList) .contains (REMOTE_FILE);
	      
	      for (String string : remoteFilenameList) {
			System.out.println("File: " + string);
		}
	   }
	   
	   
}
