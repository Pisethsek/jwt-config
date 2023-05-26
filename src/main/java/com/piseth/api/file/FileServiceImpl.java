package com.piseth.api.file;


import com.piseth.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileServiceImpl implements FileService{

    private FileUtil fileUtil;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public Resource download(String name) {
        return fileUtil.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        fileUtil.deleteByName(name);
    }

    @Override
    public FileDto findByName(String name) throws IOException {
        Resource resource = fileUtil.findByName(name);
        return FileDto.builder()
                .name(name)
                .extension(fileUtil.getExtension(resource.getFilename()))
                .url(String.format("%s%s", fileUtil.getFileBaseUrl(), resource.getFilename()))
                .downloadUrl(String.format("%s%s", fileUtil.getFileDownloadUrl(), name))
                .size(resource.contentLength())
                .build();
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();
        for (MultipartFile file : files){
            filesDto.add( fileUtil.upload(file));
        }
        return filesDto;
    }

}
