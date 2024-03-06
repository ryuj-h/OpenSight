package org.example.b104.domain.amazon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
public class AmazonRekognitionService {

    private final RekognitionClient rekognitionClient;

    public AmazonRekognitionService(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretAccessKey}") String secretAccessKey){

        this.rekognitionClient = RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .region(Region.US_EAST_1)
                .build();
    }

    public String createCollection(String collectionName) {
        CreateCollectionRequest request = CreateCollectionRequest.builder()
                .collectionId(collectionName)
                .build();

        CreateCollectionResponse response = rekognitionClient.createCollection(request);
        return response.collectionArn();
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

    public String registerUser(String imageS3Bucket, String imageS3Key) {
        Image image = Image.builder()
                .s3Object(S3Object.builder()
                        .bucket(imageS3Bucket)
                        .name(imageS3Key)
                        .build())
                .build();
//cloud-open-sight-collection
        IndexFacesRequest request = IndexFacesRequest.builder()
                .collectionId("cloud-open-sight-collection")//collectionId)
                .image(image)
                .build();

        IndexFacesResponse response = rekognitionClient.indexFaces(request);
        return response.faceRecords().get(0).face().faceId();
    }


    //
    //arn:aws:s3:::cloud-open-sight/mypic.png
    public List<FaceMatch> recognizeFace(String collectionId, String imageS3Bucket, String imageS3Key) {
        Image image = Image.builder()
                .s3Object(S3Object.builder()
                        .bucket(imageS3Bucket)
                        .name(imageS3Key)
                        .build())
                .build();

        SearchFacesByImageRequest request = SearchFacesByImageRequest.builder()
                .collectionId(collectionId)
                .image(image)
                .build();

        SearchFacesByImageResponse response = rekognitionClient.searchFacesByImage(request);
        return response.faceMatches();
    }
}
