package com.xcl.fastdfsspringboot.util;

import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * UploadService
 *
 * @author 徐长乐
 * @date 2020/5/4
 */
@Component
public class UploadService {

    @Value("${fastdfs.tracker_servers}")
    private String tracker_servers;

    @Value("${fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout;

    @Value("${fastdfs.network_timeout_in_seconds}")
    private int network_timeout;

    @Value("${fastdfs.charset}")
    private String charset;

    public Map<String,Object> upload(MultipartFile multipartFile){
        if (multipartFile==null){
            throw  new RuntimeException("文件不能为空");
        }

        String filed = this.fdfsUpload(multipartFile);
        if (StringUtils.isEmpty(filed)){
            System.out.println("上传失败");
            throw  new RuntimeException("上传失败");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","上传成功");
        map.put("filed",filed);
        return map;
    }

    private String fdfsUpload(MultipartFile multipartFile) {
        initFdfsConfig();
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            StorageClient storageClient = new StorageClient(trackerServer,storeStorage);

            String originalFilename = multipartFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            System.out.println(extName);
            String[] filed = storageClient.upload_file(multipartFile.getBytes(),extName,null);
            System.out.println(filed[0]+filed[1]);
            return filed[0]+"/"+filed[1];

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout);
            ClientGlobal.setG_network_timeout(network_timeout);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
