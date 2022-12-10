package com.tinnitussounds.cms.album;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;

    @GetMapping("/api/admin/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return ResponseEntity.status(200).body(albumRepository.findAll());
    }

    @GetMapping("/api/admin/albums/album{id}")
    public ResponseEntity<Album> getAlbum(@RequestParam("id") String id) {
        Optional<Album> album = albumRepository.findById(id);
        if(album.isEmpty()) {
            return  ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(album.get());
        }
    }

    @PostMapping("/api/admin/albums")
    public void registerAlbum(@RequestPart Album album, @RequestPart List<MultipartFile> files ) {
        Album entity = albumRepository.insert(album);
        String id = entity.getId();
    }
}
