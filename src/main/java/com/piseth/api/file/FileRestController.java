package com.piseth.api.file;

import com.piseth.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileRestController {

    private final FileService fileService;

    @GetMapping("/download/{name}")
    public ResponseEntity<?> download(@PathVariable String name){
        Resource resource = fileService.download(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deletedByName(@PathVariable String name){
        fileService.deleteByName(name);
    }

    @GetMapping("/{name}")
    public BaseRest<?> findByName(@PathVariable String name) throws IOException {
        FileDto fileDto = fileService.findByName(name);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Has Been Found")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @PostMapping
    public BaseRest<?> uploadSignal(@RequestPart MultipartFile file){

        log.info("My File {} ", file);

        FileDto fileDto = fileService.uploadSingle(file);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Has Been Uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart List<MultipartFile> files){

        log.info("My File {} ", files);

        List<FileDto> fileDto = fileService.uploadMultiple(files);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Files Has Been Uploaded")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

}
