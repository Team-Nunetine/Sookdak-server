package server.sookdak.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file, String path) throws IOException {
        ObjectMetadata objMeta = new ObjectMetadata();

        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objMeta.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).concat(Long.toString(System.nanoTime()));

        amazonS3Client.putObject(new PutObjectRequest(bucket, path + "/" + fileName, byteArrayIs, objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String imageURL = amazonS3Client.getUrl(bucket, path + "/" + fileName).toString();
        return imageURL;
    }

    public String postUpload(MultipartFile file) throws IOException {
        return upload(file, "post");
    }

    public String commentUpload(MultipartFile file, int num) throws IOException {
        return upload(file, "comment");
    }

    public void delete(String url) {
        String key = url.split("https://sookdak.s3.ap-northeast-2.amazonaws.com/")[1];
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
