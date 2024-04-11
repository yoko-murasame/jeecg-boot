package org.jeecg.modules.supermap.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.methods.HttpPost;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.SupermapCondition;
import org.jeecg.modules.supermap.entity.SuperVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Conditional(SupermapCondition.class)
@RestController
@RequestMapping("supermap")
@Api(tags = "超图")
public class TokenController {

    @Value("${supermap.url:}")
    private String url;
    @Value("${supermap.userName:}")
    private String userName;
    @Value("${supermap.password:}")
    private String password;
    @Value("${supermap.clientType:}")
    private String clientType;
    @Value("${supermap.expiration:}")
    private Long expiration;

    @ApiOperation("鉴权")
    @GetMapping("getToken")
    public Result getToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept",MediaType.ALL_VALUE);
        headers.set("Accept-Encoding","gzip, deflate, br");
        headers.set("User-Agent","PostmanRuntime-ApipostRuntime/1.1.0");
        headers.set("Connection","keep-alive");

        SuperVO vo=new SuperVO();
        vo.setExpiration(expiration);
        vo.setPassword(password);
        vo.setClientType(clientType);
        vo.setUserName(userName);

        HttpEntity<SuperVO> requestEntity = new HttpEntity<>(vo, headers);
        System.out.println(requestEntity);
        ResponseEntity<String> responseEntity =restTemplate.exchange (url, HttpMethod.POST,requestEntity, String.class);
        String token = responseEntity.getBody();
        return Result.ok(token);

    }

}
