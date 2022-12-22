package com.tinnitussounds.cms.sample;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tinnitussounds.cms.config.Constants;
import com.tinnitussounds.cms.services.StorageService;

@RestController
public class SampleController {
    @Autowired
    private SampleRepository sampleRepository;
    private StorageService storageService;

    public SampleController(SampleRepository sampleRepository, StorageService storageService) {
        this.sampleRepository = sampleRepository;
        this.storageService = storageService;
    }

    @GetMapping("/api/admin/samples")
    public ResponseEntity<List<Sample>> getSamples() {
        return ResponseEntity.ok().body(sampleRepository.findAll());
    }

    @GetMapping("/api/admin/sample/{id}")
    public ResponseEntity<Sample> getSample(@PathVariable("id") String id) {
        Optional<Sample> sample = sampleRepository.findById(id);
        if (sample.isPresent()) {
            return ResponseEntity.ok().body(sample.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/admin/sample/check/{name}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable("name") String name) {
        List<Sample> samples = sampleRepository.findByName(name);
        Boolean response = samples.size() > 0 ? true : false;

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/admin/sample")
    public ResponseEntity<String> registerSample(@RequestBody Sample sample) {
        sample.setUploadDate(DateTime.now().toDateTime(DateTimeZone.UTC).toString());
        Sample dbSample = sampleRepository.insert(sample);

        return ResponseEntity.ok().body(dbSample.getId());
    }

    @PutMapping("/api/admin/sample")
    public ResponseEntity<String> updateSample(@RequestBody SampleEdit sampleEdit) {
        Optional<Sample> dbSample = sampleRepository.findById(sampleEdit.getId());

        if (dbSample.isPresent()) {
            Sample sample = dbSample.get();
            sample.setDescription(sampleEdit.getDescription());
            sample.setCategory(sampleEdit.getCategory());
            sample.setTags(sampleEdit.getTags());
            sampleRepository.save(sample);
            return ResponseEntity.ok().body("Sample updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/admin/sample/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeSampleFile(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        try {
            storageService.putFile(Constants.samplesPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Sample file uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/api/admin/sample/artwork", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeSampleArtwork(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        try {
            storageService.putFile(Constants.samplesPath + "/" + id, name, file);
            return ResponseEntity.status(201).body("Sample artwork uploaded successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());;
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/api/admin/sample/assets/audio/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getSampleFile(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.samplesPath + "/" + dir,
                name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(fileSystemResource);
    }

    @GetMapping("/api/admin/sample/assets/artwork/{dir}/{name}")
    public ResponseEntity<FileSystemResource> getSampleArtwork(
            @PathVariable("dir") String dir,
            @PathVariable("name") String name) {
        FileSystemResource fileSystemResource = storageService.readResourceFile(Constants.samplesPath + "/" + dir, name);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileSystemResource);
    }

    @DeleteMapping("/api/admin/sample/{id}")
    public ResponseEntity<?> deleteSample(@PathVariable("id") String id) {
        Optional<Sample> sample = sampleRepository.findById(id);
        if(sample.isPresent()) {
            sampleRepository.delete(sample.get());
            try {
                storageService.deleteFolder(Constants.samplesPath + "/" + id);
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
