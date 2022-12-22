package com.tinnitussounds.cms.album;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinnitussounds.cms.config.Constants;
import com.tinnitussounds.cms.services.StorageService;

import io.vavr.control.Option;

@Controller
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;
    private StorageService storageService;

    public AlbumController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/api/admin/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return ResponseEntity.status(200).body(albumRepository.findAll());
    }

    @GetMapping("/api/admin/albums/search/{pattern}")
    public ResponseEntity<List<Album>> getAlbumsBySearch(@PathVariable("pattern") String pattern) {
        List<Album> albums = albumRepository.findBySearch(pattern);

        return ResponseEntity.ok().body(albums);
    }

    @GetMapping("/api/admin/album/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable("id") String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(album.get());
        }
    }

    @GetMapping("/api/admin/album/check/{name}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable("name") String name) {
        List<Album> albums = albumRepository.findByName(name);
        Boolean response = albums.size() > 0 ? true : false;

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/admin/album")
    public ResponseEntity<String> registerAlbum(@RequestBody Album album) {
        album.setUploadDate(DateTime.now().toDateTime(DateTimeZone.UTC).toString());
        Album dbAlbum = albumRepository.insert(album);

        return ResponseEntity.ok().body(dbAlbum.getId());
    }

    @PutMapping("/api/admin/album")
    public ResponseEntity<String> updateAlbum(@RequestBody AlbumEdit albumEdit) {
        Optional<Album> albumDb = albumRepository.findById(albumEdit.getId());

        if(albumDb.isPresent()) {
            Album album = albumDb.get();
            album.setDescription(albumEdit.getDescription());
            album.setCategory(albumEdit.getCategory());
            album.setTags(albumEdit.getTags());
            albumRepository.save(album);
            return ResponseEntity.ok().body("Album updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/admin/album/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeAudioFile(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {

        try {
            storageService.putFile(Constants.albumsPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Album song " + name + " uploaded successfully" );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/api/admin/album/artwork", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeAlbumArtwork(
        @RequestParam("id") String id, 
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file)
        {
        
        try {
            storageService.putFile(Constants.albumsPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Album artwork uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    } 

    @GetMapping("/api/admin/album/assets/audio/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getAudioFile(
        @PathVariable("dir") String dir,
        @PathVariable("name") String name
        ) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.albumsPath + "/" + dir, name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(fileSystemResource);
    }

    @GetMapping("/api/admin/album/assets/artwork/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getAlbumArtwork(
        @PathVariable("dir") String dir,
        @PathVariable("name") String name
    ) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.albumsPath + "/" + dir, name);
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(fileSystemResource);
    }

    @DeleteMapping("/api/admin/album/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") String id) {
        Optional<Album> album = albumRepository.findById(id);
        if(album.isPresent()) {
            albumRepository.delete(album.get());
            try {
                storageService.deleteFolder(Constants.albumsPath + "/" + id);
                return ResponseEntity.ok().body("Resource deleted successfully");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Could not delete requested resource");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
