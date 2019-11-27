package youtrek.s3;

import static org.junit.Assert.*;

import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class TestS3Util {
    S3Util s3Util;
    String base64String = "W4p5qHNrgMb5iOl4oub7mK2t6/6G11rNRpl1IsLGZ73oqon2vFN9OKHYvAP7FPhbv1cPixiq7mkVMTRuAKFj";
    String testingBucketName = "s3-utils-test-bucket";
    String testFileName = "Test File";

    @Before
    public void setup() {
        s3Util = S3Util.getInstance();
    }

    @Test
    public void testConvertBase64StringToInputStreamIsLossless() throws IOException {
        InputStream is = s3Util.convertBase64StringToStream(base64String);
        String decodedString = getStringInputStream(is);
        assertEquals(base64String, decodedString);
    }

    @Test
    public void testPutVideoToBucket() throws Exception {
        //If uploadVideoFails, test will fail anyways because of exception
        PutObjectResult result = s3Util.uploadVideoToBucket(testingBucketName, testFileName, base64String);
        assertNotNull(result);
        //Delete the uploaded file to not use up space for no reason
        s3Util.deleteFile(testingBucketName, testFileName);
    }

    private String getStringInputStream(InputStream is) {
        StringBuffer parsedStringBuffer = new StringBuffer();
        Scanner sc = new Scanner(is);
        while(sc.hasNext()) parsedStringBuffer.append(sc.next());
        return parsedStringBuffer.toString();
    }
}
