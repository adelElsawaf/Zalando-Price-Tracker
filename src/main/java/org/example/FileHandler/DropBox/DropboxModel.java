package org.example.FileHandler.DropBox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetMetadataErrorException;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;

public class DropboxModel {
    public static final String ACCESS_TOKEN = "sl.BnHqSrMcWrbaJ1IymHJsuByN208D0FD8ZsDBikYDg-Wx1wOgFFfVCRWikSOezpdoCjfKUuxkJTyGpNMoFInixOzWMQplfCQEWFNl5C75biGawfmZZg7tFJFoFcW41f6v7z1U_fAHbj30vFwcLhwcaxs";
    static DbxRequestConfig  config = DbxRequestConfig.newBuilder("https://www.dropbox.com/developers/apps/info/ovdwwv0m48ucdaf").build();
   static DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    public static String getAccountName() {
        {
            try {
                FullAccount account = client.users().getCurrentAccount();
                return account.getName().getDisplayName();
            } catch (DbxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void upload(String sourceFilePath, String destinationFilePath) throws IOException, DbxException {
        if(!isRepeated(destinationFilePath)) {
            InputStream in = new FileInputStream(sourceFilePath);
            FileMetadata metadata = client.files().uploadBuilder(destinationFilePath)
                    .uploadAndFinish(in);
        }
        else {
            throw new Error("File Already Exist");
        }
    }
    public static void download(String sourceFilePath,String destinationFilePath) throws IOException, DbxException {
        OutputStream downloadFile = new FileOutputStream(destinationFilePath);
        FileMetadata metadata = client.files().downloadBuilder(sourceFilePath).
                download(downloadFile);
        System.out.println(getAccountName());
    }
    public static String getDownloadLink(String filePath) throws DbxException {
        return client.files().getTemporaryLink(filePath).getLink();
    }
    public static boolean isRepeated(String filePath) throws DbxException {
        try {
            client.files().getMetadata(filePath);
        } catch (GetMetadataErrorException e){
            if (e.errorValue.isPath() && e.errorValue.getPathValue().isNotFound()) {
               return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
