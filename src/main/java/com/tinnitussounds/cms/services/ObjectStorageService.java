package com.tinnitussounds.cms.services;

import java.io.IOException;
import java.io.InputStream;

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
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import com.tinnitussounds.cms.config.Constants;

@Service
@PropertySource("classpath:application.properties")
public class ObjectStorageService {

    private static ObjectStorageService singleton;
    private ConfigFile config;
    @Nonnull
    private AuthenticationDetailsProvider provider;
    private ObjectStorageClient client;

    private ObjectStorageService() {
        try {
            config = ConfigFileReader.parseDefault();
            provider = new ConfigFileAuthenticationDetailsProvider(config);
            client = ObjectStorageClient.builder().httpProvider(Jersey3HttpProvider.getInstance()).build(provider);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectStorageService getInstance() {
        if (singleton == null)
            singleton = new ObjectStorageService();

        return singleton;
    }

    public Bucket getBucket(String name) {
        GetBucketRequest request = GetBucketRequest.builder()
            .namespaceName(Constants.objectStorageNamespace)
            .bucketName(name)
            .build();

        GetBucketResponse response = client.getBucket(request);

        return response.getBucket();
    }

    public int uploadObject(String bucketName,String objectDbId, InputStream object, String path) throws IOException {
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
}
