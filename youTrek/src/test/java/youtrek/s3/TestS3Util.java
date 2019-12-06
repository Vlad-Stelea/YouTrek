package youtrek.s3;

import static org.junit.Assert.*;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.Base64;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class TestS3Util {
    S3Util s3Util;
    String testFileString = "TestString";
    String testingBucketName = "s3-utils-test-bucket";
    String testFileName = "Test File";

    @Before
    public void setup() {
        s3Util = S3Util.getInstance();
    }

    @Test
    public void testConvertBase64StringToInputStreamIsLossless() throws IOException {
        String encodedString = Base64.encodeAsString(testFileString.getBytes());
        InputStream is = s3Util.decodeAndConvertBase64StringToStream(encodedString);
        String decodedString = getStringFromInputStream(is);
        assertEquals(testFileString, decodedString);
    }

    @Test
    public void testPutVideoToBucket() throws Exception {
        //If uploadVideoFails, test will fail anyways because of exception
        String encodedString = Base64.encodeAsString(testFileString.getBytes());
        PutObjectResult result = s3Util.uploadVideoToBucket(testingBucketName, testFileName, encodedString);
        assertNotNull(result);
        //Delete the uploaded file to not use up space for no reason
        s3Util.deleteFile(testingBucketName, testFileName);
    }

    private String getStringFromInputStream(InputStream is) {
        StringBuffer parsedStringBuffer = new StringBuffer();
        Scanner sc = new Scanner(is);
        while(sc.hasNext()) parsedStringBuffer.append(sc.next());
        return parsedStringBuffer.toString();
    }
}
