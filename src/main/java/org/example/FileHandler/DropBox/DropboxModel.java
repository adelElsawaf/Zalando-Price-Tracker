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
import java.time.LocalDate;
import java.util.UUID;

public class DropboxModel {
    public static final String ACCESS_TOKEN = "sl.BpxlvHxKwhJLqdKlA8VNnmMrYY5XMsy3Jf3S_pOKNnDjvXlMsqwGr7pQi3y859FY8tHMa3wektxmQ3tOdcmA_vWh8T4ZNaoBZVczlzVmEDUWB6026IQ50zN1f14MUPfq65js8NCnHwCvlMYGvYKssSk";
    static DbxRequestConfig config = DbxRequestConfig.newBuilder("https://www.dropbox.com/developers/apps/info/ovdwwv0m48ucdaf").build();
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

    public static String upload(String sourceFilePath, String destinationFilePath) throws IOException, DbxException {
    if (isRepeated(destinationFilePath)) {
        destinationFilePath = handleDuplicateFilePaths(destinationFilePath);
    }
        InputStream in = new FileInputStream(sourceFilePath);
        FileMetadata metadata = client.files().uploadBuilder(destinationFilePath)
                .uploadAndFinish(in);
        return getDownloadLink(destinationFilePath);
    }

    public static String generateFileName() {
        return ("_"+LocalDate.now() + "_" + UUID.randomUUID().toString().substring(0, 7));
    }

    public static String handleDuplicateFilePaths(String filePath) throws DbxException {
        StringBuilder newFilePath = new StringBuilder(filePath);
        int lastDotIndex = filePath.lastIndexOf(".");
        newFilePath.insert(lastDotIndex, generateFileName());
        return newFilePath.toString();
    }

    public static void download(String sourceFilePath, String destinationFilePath) throws IOException, DbxException {
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
        } catch (GetMetadataErrorException e) {
            if (e.errorValue.isPath() && e.errorValue.getPathValue().isNotFound()) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
