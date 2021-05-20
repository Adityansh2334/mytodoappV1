package com.note.indiatodo.awsConfig;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Component
public class BucketHandler {
    private final String key_Id=""; //put key_id aws IAM
    private final String secret_key=""; //secret_key aws IAM
    BasicAWSCredentials awsCredentials= new BasicAWSCredentials(key_Id,secret_key);
    final AmazonS3 s3= AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion("us-east-2")
            .build();
            TransferManager tm= TransferManagerBuilder
                    .standard()
                    .withS3Client(s3)
                    .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
                    .build();

        public URL uplaodImage(String name, MultipartFile mf){
            String bucketName="todoimageupload";
            int num=(int)(Math.random()*(5000-501)+500);
            final String[] split = mf.getContentType().split("/");
            String key_name=name+num+"."+split[1];
            try {
                ObjectMetadata om= new ObjectMetadata();
                om.setContentLength(mf.getSize());
                Upload up= tm.upload(bucketName,key_name,mf.getInputStream(),om);
                up.waitForCompletion();;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            final URL url = s3.getUrl(bucketName, key_name);
            return url;
        }
        public URL getCommonImageUrl(){
            String bkt_name="todoimageupload";
            return s3.getUrl(bkt_name,"common.png");
        }
}
