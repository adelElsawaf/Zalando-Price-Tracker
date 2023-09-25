package org.example.FileHandler;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;

public class Dropbox {
    public static final String ACCESS_TOKEN = "sl.BmUA7ytY8t74-xN_N_eTGpoiiPXxzb9ZnaXscQLCxy2b35P_794KFIdlyR9ZU2ZDSEL5VLufHt8PhWLKcX2JTHJEKHWgYOHufp0Pu3Ip_hOorZpSrKYESxNdJU5sBuxR8L7WSxLRD0bxCCHqwcdSwD4";
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
        InputStream in = new FileInputStream(sourceFilePath);
        FileMetadata metadata = client.files().uploadBuilder(destinationFilePath).withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(in);
    }
    public static void download(String sourceFilePath,String destinationFilePath) throws IOException, DbxException {
        OutputStream downloadFile = new FileOutputStream(destinationFilePath);
        FileMetadata metadata = client.files().downloadBuilder(sourceFilePath).
                download(downloadFile);
        System.out.println(getAccountName());
    }
}
