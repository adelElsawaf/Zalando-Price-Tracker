package org.example.FileHandler.DropBox;


import com.dropbox.core.DbxException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dropbox/")
@CrossOrigin
public class DropboxController {
    @GetMapping("upload")
    public static String upload (@RequestParam(name = "from") String from,@RequestParam(name = "to") String to) throws IOException, DbxException {
       return DropboxModel.upload(from,to);

    }
}
