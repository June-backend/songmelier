package june.songmelier.controller;

import june.songmelier.utils.S3UploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "aws-s3")
@RestController
public class S3TempController {

    private final S3UploadUtils s3UploadUtils;

    @PostMapping(name = "S3 파일 업로드", value = "/file")
    public String fileUpload(@RequestParam("files") MultipartFile multipartFile) throws IOException {
        s3UploadUtils.upload(multipartFile, "test"); // test 폴더에 파일 생성
        return "success";
    }

    @DeleteMapping(name = "S3 파일 삭제", value = "/file")
    public String fileDelete(@RequestParam("path") String path) {
        s3UploadUtils.delete(path);
        return "success";
    }

}
