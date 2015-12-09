package org.pieShare.pieDrive.adapter.dropbox;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.pieShare.pieDrive.adapter.api.Adaptor;
import org.pieShare.pieDrive.adapter.model.PieDriveFile;

import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.core.*;

import org.pieShare.pieDrive.adapter.exceptions.AdaptorException;
import java.io.*;
import org.pieShare.pieTools.pieUtilities.service.pieLogger.PieLogger;

/**
 * Created by Roland on 30.11.2015.
 */
public class DropboxAdapter implements Adaptor {

    static final String ACCESS_TOKEN = "xH3xc5-r9gAAAAAAAAAABdOYq3F9CGn0DvpdXYkLrj0Fa4zggF34i3prqVmM5qfV";
    private DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");;
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);;


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
}
