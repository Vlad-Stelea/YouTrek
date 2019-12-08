package youtrek.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.Base64;

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
        InputStream videoStream = decodeAndConvertBase64StringToStream(base64EncodedString);

        PutObjectRequest request = new PutObjectRequest(location, videoName, videoStream, metadata);
        AccessControlList acl = setupAccess();
        request.setAccessControlList(acl);
        return s3.putObject(request);
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
    InputStream decodeAndConvertBase64StringToStream(String base64String) throws IOException {
        byte [] bytes = base64String.getBytes();
        bytes = Base64.decode(bytes);
        InputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

    private ObjectMetadata setupMetadataForVideo() {
        return new ObjectMetadata();
    }

    private AccessControlList setupAccess() {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        return acl;
    }

}
