package com.kraigmcfadden.imab.persistence.service.s3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraigmcfadden.imab.persistence.model.PO;
import com.kraigmcfadden.imab.persistence.service.PersistenceService;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

public class S3PersistenceService implements PersistenceService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final S3Client s3;
    private final String bucket;

    public S3PersistenceService(S3Client s3, String bucket) {
        this.s3 = s3;
        this.bucket = bucket;
    }

    @Override
    public <T extends PO> T get(String id, Class<T> clazz) throws Exception {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(id)
                .build();
        ResponseBytes<GetObjectResponse> response = s3.getObjectAsBytes(request);
        return objectMapper.readValue(response.asUtf8String(), clazz);
//        try {
//
//        } catch (NoSuchKeyException e) {  // bad input, key doesn't exist
//
//        } catch (S3Exception e) {  // exception with S3 in general (bucket name for instance)
//
//        } catch (AwsServiceException e) {  // request made it to service but got an error back
//
//        } catch (SdkClientException e) {  // there was a client issue (network connectivity, or issue parsing response)
//
//        } catch (JsonMappingException e) {  // couldn't map fields to the object specified
//
//        } catch (JsonProcessingException e) {  // higher level object mapper exception
//
//        }
    }

    @Override
    public void put(PO po) throws Exception {
        String json = objectMapper.writeValueAsString(po);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(po.getId())
                .contentLength((long) json.length())
                .build();
        s3.putObject(request, RequestBody.fromString(json));
    }
}
