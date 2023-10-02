package org.example.FileHandler.DropBox;


import com.dropbox.core.DbxException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/dropbox/")
public class DropboxController {
    @GetMapping("upload")
    public static String upload (@RequestParam(name = "from") String from,@RequestParam(name = "to") String to) throws IOException, DbxException {
        DropboxModel.upload(from,to);
        return DropboxModel.getDownloadLink(to);
    }
}
