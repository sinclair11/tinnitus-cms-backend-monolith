package com.tinnitussounds.cms.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinnitussounds.cms.album.Album;
import com.tinnitussounds.cms.album.AlbumRepository;
import com.tinnitussounds.cms.category.CategoryRepository;
import com.tinnitussounds.cms.preset.PresetRepository;
import com.tinnitussounds.cms.sample.SampleRepository;

@RestController
public class DashboardController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SampleRepository sampleRepository;
    @Autowired
    private PresetRepository presetRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/api/admin/dashboard/resources")
    public ResponseEntity<DashboardResources> getResources() {
        DashboardResources response = new DashboardResources();
        int songs = 0;
        response.setAlbums((int) albumRepository.count());

        List<Album> albums = albumRepository.findAll();
        for(Album album : albums) {
            songs += album.getTotalSongs();
        }
        response.setSongs(songs);
        response.setSamples((int) sampleRepository.count());
        response.setPresets((int) presetRepository.count());
        response.setCategories((int) categoryRepository.count());

        return ResponseEntity.ok().body(response);
    }
}
