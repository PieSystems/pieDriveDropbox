/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pieShare.pieDrive.adapter.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DropboxAuthentication {
	private String ACCESS_TOKEN = "";
    private DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
    private DbxClientV2 client; 
	private String path;
	
	public DropboxAuthentication(){
		String path = System.getProperty("user.home");
		File pieDrive = new File(path, ".pieDrive");
		File dropboxtoken = new File(pieDrive, "dropboxtoken");
		path = dropboxtoken.getAbsolutePath();
	}
	
	public boolean authenticate(){
		
		try{
			ACCESS_TOKEN = new String(Files.readAllBytes(Paths.get(path)));
		} catch(IOException e){
			
		}
		
		client = new DbxClientV2(config, ACCESS_TOKEN);
		
		try{
			client.users.getCurrentAccount();
		}catch(Exception e){
			return false;
		}
				
		return true;
	}
	
	public DbxClientV2 getClient(){
		return client;
	}
}
