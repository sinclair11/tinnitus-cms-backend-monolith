package com.tinnitussounds.cms.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.http.client.jersey3.Jersey3HttpProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.Bucket;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.model.PreauthenticatedRequest;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import com.tinnitussounds.cms.config.Constants;

@Service
@PropertySource("classpath:application.properties")
public class ObjectStorageService {

    private ConfigFile config;
    @Nonnull
    private AuthenticationDetailsProvider provider;
    private ObjectStorageClient client;

    public ObjectStorageService() {
        try {
            config = ConfigFileReader.parseDefault();
            provider = new ConfigFileAuthenticationDetailsProvider(config);
            client = ObjectStorageClient.builder().httpProvider(Jersey3HttpProvider.getInstance()).build(provider);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bucket getBucket(String name) {
        GetBucketRequest request = GetBucketRequest.builder()
                .namespaceName(Constants.objectStorageNamespace)
                .bucketName(name)
                .build();

        GetBucketResponse response = client.getBucket(request);

        return response.getBucket();
    }

    public int uploadObject(String bucketName, String objectDbId, InputStream object, String path) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .namespaceName(Constants.objectStorageNamespace)
                .bucketName(bucketName)
                .objectName(objectDbId)
                .contentLength(Long.valueOf(object.available()))
                .putObjectBody(object)
                .contentType("audio/wav")
                .build();

        PutObjectResponse response = client.putObject(putObjectRequest);

        return response.get__httpStatusCode__();
    }

    public String uploadObjectMultipart(String objectDbId, Object file, String path) {
        return "";
    }

    public Optional<PreauthenticatedRequest> createPreauthRequest(String bucket, String userId) {

        CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails = CreatePreauthenticatedRequestDetails
                .builder()
                .name(userId)
                .bucketListingAction(PreauthenticatedRequest.BucketListingAction.ListObjects)
                .accessType(CreatePreauthenticatedRequestDetails.AccessType.AnyObjectRead)
                .timeExpires(Date.from(Instant.now().plus(10 * 365, ChronoUnit.DAYS)))
                .build();

        CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest = CreatePreauthenticatedRequestRequest
                .builder()
                .namespaceName(Constants.objectStorageNamespace)
                .bucketName(bucket)
                .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails)
                .build();

        /* Send request to the Client */
        CreatePreauthenticatedRequestResponse response = client
                .createPreauthenticatedRequest(createPreauthenticatedRequestRequest);

        if(response.get__httpStatusCode__() == 200) {
            return Optional.of(response.getPreauthenticatedRequest());
        } else {
            return Optional.empty();
        }
    }
}
