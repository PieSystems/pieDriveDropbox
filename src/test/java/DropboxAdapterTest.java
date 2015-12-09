

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.pieShare.pieDrive.adapter.dropbox.DropboxAdapter;
import org.pieShare.pieDrive.adapter.dropbox.configuration.DropboxAdapterConfig;
import org.pieShare.pieDrive.adapter.model.PieDriveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.UUID;
import org.junit.Test;
import org.pieShare.pieDrive.adapter.exceptions.AdaptorException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DropboxAdapterConfig.class)
public class DropboxAdapterTest {

    @Autowired
    private DropboxAdapter dbxClient;
    private PieDriveFile pieFile;
    private File testFile;
    byte[] content = "Dropbox Test content".getBytes();


    @Before
    public void init(){
        UUID uuid = UUID.randomUUID();
        testFile = new File(uuid.toString());
        pieFile = new PieDriveFile();


        FileOutputStream fout;
        try {
            fout = new FileOutputStream(testFile);
            fout.write(content);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pieFile.setUuid(uuid.toString());

    }

    @After
    public void tearDown() {
        testFile.delete();
        if (testFile.exists()) {
            System.out.println(testFile.getAbsolutePath());
        }
    }

    @Test
    public void test(){
        testUpload();
        testDownload();
        testDelete();
    }

    public void testUpload(){
        FileInputStream in;

        try {
            in = new FileInputStream(testFile);
			try{
				dbxClient.upload(pieFile, in);
			} catch (AdaptorException e){
				Assert.fail();
			}
            in.close();
        } catch (FileNotFoundException e) {
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void testDownload(){

        File downFile = new File("down_" + pieFile.getUuid());
        FileOutputStream downFileOut;

        try {
            downFileOut = new FileOutputStream(downFile);
			try{
				dbxClient.download(pieFile, downFileOut);
			} catch (AdaptorException e){
				Assert.fail();
			}

            if(!downFile.exists()){
                Assert.fail();
            }
            downFileOut.close();

            downFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void testDelete(){
		try{
			dbxClient.delete(pieFile);
		} catch (AdaptorException e){
			Assert.fail();
		}
    }

}
