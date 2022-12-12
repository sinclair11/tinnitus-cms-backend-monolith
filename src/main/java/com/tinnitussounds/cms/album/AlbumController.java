package com.tinnitussounds.cms.album;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinnitussounds.cms.services.ObjectStorageService;

@Controller
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;

    private ObjectStorageService objectStorageService;


    public AlbumController(AlbumRepository albumRepository, ObjectStorageService objectStorageService) {
        this.objectStorageService = objectStorageService;
    }

    @GetMapping("/api/admin/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return ResponseEntity.status(200).body(albumRepository.findAll());
    }

    @GetMapping("/api/admin/albums/album{id}")
    public ResponseEntity<Album> getAlbum(@RequestParam("id") String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(album.get());
        }
    }

    @PostMapping("/api/admin/albums")
    public ResponseEntity<String> registerAlbum(@RequestBody Album album) {
        album.setUploadDate(DateTime.now().toDateTime(DateTimeZone.UTC).toString());
        albumRepository.insert(album);

        return ResponseEntity.ok().body("Album successfully registered in database");
    }

    @PostMapping("/api/admin/albums/files")
    public ResponseEntity<String> uploadFile(@RequestParam("type") String fileType, 
    @RequestParam("name") String fileName,
    @RequestParam("file") MultipartFile file) {
        try {
            int status = objectStorageService.uploadObject("resources", "albums/"+fileName, file.getInputStream());
            if(status == 200) {
                return ResponseEntity.ok().body(fileName + " uploaded successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload file " + fileName);
        }
        return ResponseEntity.ok().body(fileName + " uploaded successfully");
    }
}
