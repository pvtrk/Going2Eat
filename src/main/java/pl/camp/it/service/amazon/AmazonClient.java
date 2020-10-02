package pl.camp.it.service.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


@Service
public class AmazonClient {

    private AmazonS3 s3client;
    private final static String ENDPOINT_URL = "https://s3.us-east-1.amazonaws.com";
    private final static String BUCKET_NAME = "going2eatimages";
    private final static String ACCESS_KEY = "";
    private final static String SECRET_KEY = "";

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.ACCESS_KEY, this.SECRET_KEY);
    this.s3client = AmazonS3ClientBuilder.standard().withRegion("us-east-1")
            .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename()
                .replace(" ", "_");
    }

    private void uploadFileToS3Bucket(String fileName,File file) {
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            File result = convertMultiPartToFile(file);
            String fileName = generateFileName(file);
            fileUrl = ENDPOINT_URL + "/" +BUCKET_NAME + "/" + fileName;
            uploadFileToS3Bucket(fileName, result);
            result.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUrl;
    }
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(BUCKET_NAME + "/", fileName));
        return "Successfully deleted";
    }
}
