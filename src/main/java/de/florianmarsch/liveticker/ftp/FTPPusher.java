package de.florianmarsch.liveticker.ftp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPPusher {
	final static Logger logger = LoggerFactory.getLogger(FTPPusher.class);


	private FTPClient client;
	
	public void save(FTPPushTask task){
		try{
			
			login();
			String url = task.getUrl();
			String content = loadFile(url);
			String filename = task.getFilename();
			String directory = task.getDirectory();
			createDirs(directory);
			upload(content, directory + filename);
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally {
			logout();
		}
	}

	private void logout() {
	
		try {
			client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void login() {
		client = new FTPClient();
		String host = System.getenv("FTP_HOST");
		String user = System.getenv("FTP_USER");
		String pw = System.getenv("FTP_PASSWD");
		try {
			client.connect(host);
			client.login(user, pw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Login failed :"+e.getMessage(), e);
		} 

	}
	
	
	private void createDirs(String directory) {
		List<String> asList = Arrays.asList(directory.split("/"));
		for (String string : asList) {
			if (string != null) {
				String pathname = string.trim();
				if (!pathname.isEmpty()) {
					try {
						if(client.isConnected() || client.isAvailable()){
							client.makeDirectory(pathname);
							client.changeWorkingDirectory("./" + pathname);
						}else{
							throw new Exception("Client not connected || available");
						}
					} catch (Exception e) {
						throw new RuntimeException("Error creating dirs "+e.getMessage());
					}
				}
			}
		}
	}

	private void upload(String content, String filename) {
		InputStream fis = null;

		try {

			fis = new ByteArrayInputStream(content.getBytes());

			//
			// Store file to server
			//
			if(client.isConnected() || client.isAvailable()){
				client.storeFile(filename, fis);
			}else{
				throw new Exception("Client not connected || available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error creating file"+e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
			}
		}
	}
	

	
	public String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (Exception e) {
			throw new RuntimeException("Errir reciving file "+e.getMessage());
		}
		return tempReturn.toString();
	}

}