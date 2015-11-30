package org.pieShare.pieDrive.adapter.dropbox;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.pieShare.pieDrive.adapter.api.Adaptor;
import org.pieShare.pieDrive.adapter.model.PieDriveFile;

import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.core.*;
import com.dropbox.core.v2.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by Roland on 30.11.2015.
 */
public class DropboxAdapter implements Adaptor {

    static final String ACCESS_TOKEN = "xH3xc5-r9gAAAAAAAAAABdOYq3F9CGn0DvpdXYkLrj0Fa4zggF34i3prqVmM5qfV";
    private DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");;
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);;


    @Override
    public void delete(PieDriveFile file)  {
        try {
            client.files.delete("/"+file.getUuid());
        } catch (DbxException e) {
            e.printStackTrace();
            //TODO error handling
        }
    }

    @Override
    public void upload(PieDriveFile file, InputStream stream) {
        try {
            client.files.uploadBuilder("/"+file.getUuid()).run(stream);
        } catch (DbxException|IOException e) {
            e.printStackTrace();
            //TODO error handling
        }
    }

    @Override
    public void download(PieDriveFile file, OutputStream stream) {
        try {
            client.files.downloadBuilder("/"+file.getUuid()).run(stream);
        } catch (DbxException|IOException e) {
            e.printStackTrace();
            //TODO error handling
        }
    }
}
