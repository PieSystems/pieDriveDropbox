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
import javax.annotation.PostConstruct;
import org.pieShare.pieTools.pieUtilities.service.pieLogger.PieLogger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Roland on 30.11.2015.
 */
public class DropboxAdapter implements Adaptor {

	@Autowired
	private DropboxAuthentication authentication;
		
	public DropboxAdapter(){
		
	}
	
	@PostConstruct
	private void auth(){
		authentication.authenticate();
	}
	
    @Override
    public void delete(PieDriveFile file) throws AdaptorException {
        try {
            authentication.getClient().files.delete("/"+file.getUuid());
			PieLogger.trace(DropboxAdapter.class, "{} deleted", file.getUuid());
        } catch (DbxException e) {
            throw new AdaptorException(e);
        }
    }

    @Override
    public void upload(PieDriveFile file, InputStream stream) throws AdaptorException {
        try {
            authentication.getClient().files.uploadBuilder("/"+file.getUuid()).run(stream);
			PieLogger.trace(DropboxAdapter.class, "{} uploaded", file.getUuid());
        } catch (DbxException|IOException e) {
            throw new AdaptorException(e);
        }
    }

    @Override
    public void download(PieDriveFile file, OutputStream stream) throws AdaptorException {
        try {
            authentication.getClient().files.downloadBuilder("/"+file.getUuid()).run(stream);
			PieLogger.trace(DropboxAdapter.class, "{} downloaded", file.getUuid());
        } catch (DbxException|IOException e) {
            throw new AdaptorException(e);
        }
    }
}
