package com.yofujitsu.rksp_pr5.controller;

import com.yofujitsu.rksp_pr5.entity.File;
import com.yofujitsu.rksp_pr5.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileRepository repository;

    @GetMapping
    public ResponseEntity<List<File>> getFiles() {
        return ResponseEntity.ok(repository.findAll());
    }

        @PostMapping("/create")
        public ResponseEntity<File> createFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String fileExtension =
                        fileName.substring(fileName.lastIndexOf("."));
                String newFilename = name + fileExtension;
                File newFile = new File(UUID.randomUUID(), newFilename, LocalDateTime.now(), file.getSize(), file.getBytes());
                return ResponseEntity.ok(repository.save(newFile));
            }
            return ResponseEntity.ok(new File());
        }

    @DeleteMapping("/delete")
    public void deleteFile(@RequestParam("id") UUID id) {
        repository.deleteById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        Optional<File> fileOptional =
                repository.findById(id);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
            return new ResponseEntity<>(file.getFileData(), headers,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
