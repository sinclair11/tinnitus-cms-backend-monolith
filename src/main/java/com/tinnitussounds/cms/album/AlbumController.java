package com.tinnitussounds.cms.album;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/api/admin/albums")
    public ResponseEntity<List<Album>> getAlbums() {
        return ResponseEntity.status(200).body(albumRepository.findAll());
    }

}
