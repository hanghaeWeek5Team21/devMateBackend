package com.sparta.devmatebackend.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

@Component
public class S3Object {
    private Regions clientRegion = Regions.AP_NORTHEAST_2;
    private String BUCKET_NAME = "devmate/image";


    public void upload(File file) throws IOException, SdkClientException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, file.getName(), file);

        ObjectMetadata metadata = new ObjectMetadata();

        String contentType = URLConnection.guessContentTypeFromName(file.getName());

        metadata.setContentType(contentType);
        metadata.addUserMetadata("title", "whatever");

        request.setMetadata(metadata);
        s3Client.putObject(request);
    }

    public String getObjectUrl(File file) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
        return s3Client.getUrl(BUCKET_NAME, file.getName()).toString();
    }

    public boolean objectExists(String objectName) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();
            return s3Client.doesObjectExist(BUCKET_NAME, objectName);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            return true;
        }
    }
}
