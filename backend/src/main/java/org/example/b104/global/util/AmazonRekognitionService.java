package org.example.b104.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class AmazonRekognitionService {

    private final RekognitionClient rekognitionClient;

    public AmazonRekognitionService() {
        this.rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("***REMOVED***", "***REMOVED***")))
                .region(Region.US_EAST_1) // Your preferred region
                .build();
    }

    public void detectFaces(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            InputStream imageStream = new FileInputStream(imageFile);
            Image image = Image.builder()
                    .bytes(SdkBytes.fromInputStream(imageStream))
                    .build();

            DetectFacesRequest request = DetectFacesRequest.builder()
                    .image(image)
                    .build();

            DetectFacesResponse response = rekognitionClient.detectFaces(request);

            List<FaceDetail> faceDetails = response.faceDetails();
            for (FaceDetail faceDetail : faceDetails) {
                System.out.println("Detected face: " + faceDetail);
            }
        } catch (RekognitionException | FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
