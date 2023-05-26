package com.piseth.api.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    Resource download(String name);


    /**
     *
     * @param name
     */
    void deleteByName(String name);

    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    FileDto findByName(String name) throws IOException;

    /**
     * use to upload signal file
     * @param file request form data from client
     * @return FileDto
     */
    FileDto uploadSingle(MultipartFile file);

    /**
     * use to upload multiple files
     * @param files request form data from client
     * @return FileDto
     */
    List<FileDto> uploadMultiple(List<MultipartFile> files);

}
