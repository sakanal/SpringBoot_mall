package com.example.mall.controller;

import com.example.mall.service.OssService;
import com.example.mall.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(tags = "上传管理")
@Slf4j
@RestController
@RequestMapping("/oss")
public class OssController {

    @Resource
    private OssService ossService;

    @ApiOperation("上传图片")
    @PostMapping("/product")
    public R uploadOssAvatar(MultipartFile multipartFile){
        log.info("开始上传图片");
        String url=ossService.uploadFileAvatar(multipartFile);
        if(StringUtils.hasText(url)){
            return R.ok().setMessage(url);
        }else {
            return R.error();
        }
    }

}
