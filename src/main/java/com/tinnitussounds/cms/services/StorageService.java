package com.tinnitussounds.cms.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:application.properties")
public class StorageService {

    public StorageService() {

    }

    public void putFile(String dir, String name, MultipartFile file) throws IOException {
        new File(dir).mkdirs();
        try {
            Files.copy(file.getInputStream(), Path.of(dir + "/" + name));
        } catch (IOException e) {
            throw e;
        }
    }

    public FileSystemResource readAudioFile(String dir, String name) {
        File audioFile = new File(dir + "/" + name);
        // Create a FileSystemResource object for the audio file
        FileSystemResource audioResource = new FileSystemResource(audioFile);

        return audioResource;
    }

    public void deleteFolder(String dir) throws IOException {
        FileUtils.deleteDirectory(new File(dir));
    }

}
