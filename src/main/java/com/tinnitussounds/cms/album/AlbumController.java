package com.tinnitussounds.cms.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/api/admin/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return ResponseEntity.status(200).body(albumRepository.findAll());
    }

    @PostMapping("/api/admin/albums")
    public void registerAlbum(@RequestPart Album album, @RequestPart List<MultipartFile> files ) {
        Album entity = albumRepository.insert(album);
        String id = entity.getId();
    }
}
