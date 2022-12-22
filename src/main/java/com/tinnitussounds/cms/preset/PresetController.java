package com.tinnitussounds.cms.preset;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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

@Controller
public class PresetController {
    @Autowired
    private PresetRepository presetRepository;
    private StorageService storageService;

    public PresetController(PresetRepository presetRepository, StorageService storageService) {
        this.presetRepository = presetRepository;
        this.storageService = storageService;
    }

    @GetMapping("/api/admin/presets")
    public ResponseEntity<List<Preset>> getPresets() {
        return ResponseEntity.ok().body(presetRepository.findAll());
    }

    @GetMapping("/api/admin/preset/{id}")
    public ResponseEntity<Preset> getPreset(@PathVariable("id") String id) {
        Optional<Preset> preset = presetRepository.findById(id);
        if (preset.isPresent()) {
            return ResponseEntity.ok().body(preset.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/admin/preset/check/{name}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable("name") String name) {
        List<Preset> presets = presetRepository.findByName(name);
        Boolean response = presets.size() > 0 ? true : false;

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/admin/preset")
    public ResponseEntity<String> registerPreset(@RequestBody Preset preset) {
        preset.setUploadDate(DateTime.now().toDateTime(DateTimeZone.UTC).toString());
        Preset dbPreset = presetRepository.insert(preset);

        return ResponseEntity.ok().body(dbPreset.getId());
    }

    @PutMapping("/api/admin/preset")
    public ResponseEntity<String> updatePreset(@RequestBody PresetEdit presetEdit) {
        Optional<Preset> dbPreset = presetRepository.findById(presetEdit.getId());

        if (dbPreset.isPresent()) {
            Preset preset = dbPreset.get();
            preset.setDescription(presetEdit.getDescription());
            preset.setCategory(presetEdit.getCategory());
            preset.setTags(presetEdit.getTags());
            presetRepository.save(preset);
            return ResponseEntity.ok().body("Preset updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/admin/preset/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storePresetFile(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        try {
            storageService.putFile(Constants.presetsPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Preset file uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/api/admin/preset/artwork", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storePresetArtwork(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        try {
            storageService.putFile(Constants.presetsPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Preset artwork uploaded successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());;
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/api/admin/preset/assets/audio/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getPresetFile(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.presetsPath + "/" + dir,
                name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(fileSystemResource);
    }

    @GetMapping("/api/admin/preset/assets/artwork/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getPresetArtwork(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.presetsPath + "/" + dir, name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileSystemResource);
    }

    @DeleteMapping("/api/admin/preset/{id}")
    public ResponseEntity<?> deletePreset(@PathVariable("id") String id) {
        Optional<Preset> preset = presetRepository.findById(id);
        if(preset.isPresent()) {
            presetRepository.delete(preset.get());
            try {
                storageService.deleteFolder(Constants.presetsPath + "/" + id);
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
