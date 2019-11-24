package youtrek.db;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Base64;

public class KeyManager {

    private final String[] secret;
    private static KeyManager manager;

    private KeyManager() {
        secret = getConnectionInfo();
    }

    public static KeyManager getInstance() {
        if (manager == null) manager = new KeyManager();
        return manager;
    }

    String getHost() {
        if (secret == null) return null;
        return secret[0];
    }

    String getUser() {
        if (secret == null) return null;
        return secret[1];
    }

    String getPass() {
        if (secret == null) return null;
        return secret[2];
    }


    private static String getSecret() {
        String secretName = "ChekovDB";
        String region = "us-east-2";

        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException | InternalServiceErrorException | InvalidParameterException | InvalidRequestException | ResourceNotFoundException e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            return(secret);
        }
        else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            return(decodedBinarySecret);
        }
    }

    private String[] getConnectionInfo() {
        String secret = getSecret();
        if (secret == null) {
            return null;
        }
        else {
            String[] secretArray = new String[3];
            JsonObject jo = new JsonParser().parse(secret).getAsJsonObject();
            secretArray[0] = jo.get("host").getAsString();
            secretArray[1] = jo.get("username").getAsString();
            secretArray[2] = jo.get("password").getAsString();
            return secretArray;
        }
    }

}
