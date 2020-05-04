package com.xcl.fastdfsspringboot2.cofnig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * UploadProperties
 *
 * @author 徐长乐
 * @date 2020/5/4
 */
@ConfigurationProperties("upload")
@Data
public class UploadProperties {

    private String  baseUrl;

    private List<String> allowTypes;
}
