package org.pieShare.pieDrive.adapter.dropbox.configuration;

import org.pieShare.pieDrive.adapter.dropbox.DropboxAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Roland on 30.11.2015.
 */
@Configuration
public class DropboxAdapterConfig {

    @Bean
    public DropboxAdapter dropboxAdapter(){
        return new DropboxAdapter();
    }
}
