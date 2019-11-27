package youtrek.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

public class S3Util {
    private static S3Util instance;

    private S3Util() {
        //TODO write constructor
    }

    public static S3Util getInstance() {
        if(instance == null) instance = new S3Util();
        return instance;
    }

    /**
     * Uploads a video to the selected s3 bucket
     * @param location The bucket to upload the video to
     * @param videoName The name to store the video as
     * @param base64EncodedString The base64 encoded string to upload
     */
    public PutObjectResult uploadVideoToBucket(String location, String videoName, String base64EncodedString) throws IOException {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_2) //Stick to Ohio region because that's where our buckets are located
                .build();

        ObjectMetadata metadata = setupMetadataForVideo();
        InputStream videoStream = convertBase64StringToStream(base64EncodedString);
        PutObjectResult result = s3.putObject(location, videoName, videoStream, metadata);

        return result;
    }

    /**
     * Deletes the specified file from the bucket name
     * @param bucketName The bucket to delete the file from
     * @param fileName The name of the file in the bucket to delete
     * @throws Exception
     */
    public void deleteFile(String bucketName, String fileName) throws Exception{
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_2) //Stick to Ohio region because that's where our buckets are located
                .build();
        s3.deleteObject(bucketName, fileName);
    }



    //Exception should bubble up
    InputStream convertBase64StringToStream(String base64String) throws IOException {
        byte [] bytes = base64String.getBytes();
        InputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

    private ObjectMetadata setupMetadataForVideo() {
        return new ObjectMetadata();
    }

}
