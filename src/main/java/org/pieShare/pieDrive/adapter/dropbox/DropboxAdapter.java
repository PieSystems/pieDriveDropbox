package org.pieShare.pieDrive.adapter.dropbox;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.pieShare.pieDrive.adapter.api.Adaptor;
import org.pieShare.pieDrive.adapter.model.PieDriveFile;

import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.core.*;
import static com.dropbox.core.v2.DbxFiles.CreateFolderError.Tag.path;

import org.pieShare.pieDrive.adapter.exceptions.AdaptorException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.pieShare.pieTools.pieUtilities.service.pieLogger.PieLogger;

/**
 * Created by Roland on 30.11.2015.
 */
public class DropboxAdapter implements Adaptor {

    private String ACCESS_TOKEN = "";
    private DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
    private DbxClientV2 client; 
	
	public DropboxAdapter(){
		loadToken();
		
	}
	
	private void loadToken(){
		String path = System.getProperty("user.home");
		File pieDrive = new File(path, ".pieDrive");
		File dropboxtoken = new File(pieDrive, "dropboxtoken");
		
		try{
			ACCESS_TOKEN = new String(Files.readAllBytes(Paths.get(dropboxtoken.getAbsolutePath())));
		} catch(IOException e){
			
		}
	}


    @Override
    public void delete(PieDriveFile file) throws AdaptorException {
        try {
            client.files.delete("/"+file.getUuid());
			PieLogger.trace(DropboxAdapter.class, "{} deleted", file.getUuid());
        } catch (DbxException e) {
            throw new AdaptorException(e);
        }
    }

    @Override
    public void upload(PieDriveFile file, InputStream stream) throws AdaptorException {
        try {
            client.files.uploadBuilder("/"+file.getUuid()).run(stream);
			PieLogger.trace(DropboxAdapter.class, "{} uploaded", file.getUuid());
        } catch (DbxException|IOException e) {
            throw new AdaptorException(e);
        }
    }

    @Override
    public void download(PieDriveFile file, OutputStream stream) throws AdaptorException {
        try {
            client.files.downloadBuilder("/"+file.getUuid()).run(stream);
			PieLogger.trace(DropboxAdapter.class, "{} downloaded", file.getUuid());
        } catch (DbxException|IOException e) {
            throw new AdaptorException(e);
        }
    }

	@Override
	public boolean authenticate() {
		client = new DbxClientV2(config, ACCESS_TOKEN);
		try{
			client.users.getCurrentAccount();
		}catch(Exception e){
			return false;
		}
				
		return true;
	}
}
