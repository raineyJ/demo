package com.bdcs.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author jiangyu
 * @version 1.0
 * @date: created in 14:35 2021/4/17
 */
@RestController
public class TestController {

    DateTimeFormatter SDF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private JSONObject mockResp() {

        JSONObject o = new JSONObject();
        JSONPath.set(o, "message", "success");
        JSONPath.set(o, "timestamp", SDF.format(LocalDateTime.now(ZoneOffset.UTC)));
        return o;
    }

    private JSONObject mockSuccess() {
        JSONObject o = mockResp();
        JSONPath.set(o, "code", "00000");
        JSONPath.set(o, "data.sstWaybillNumber", "success");
        JSONPath.set(o, "data.trackingDetails[0].infoDatetime", "2021-04-06T15:40:18Z");
        JSONPath.set(o, "data.trackingDetails[0].infoType", "SST已揽收入库");
        JSONPath.set(o, "data.trackingDetails[0].infoContent", "SST已揽收入库");
        JSONPath.set(o, "data.trackingDetails[0].location", "韩国PAJU仓");
        JSONPath.set(o, "data.trackingDetails[0].deliveryName", null);
        JSONPath.set(o, "data.trackingDetails[0].contactInfo", null);
        JSONPath.set(o, "data.trackingDetails[0].signedName", null);
        JSONPath.set(o, "data.trackingDetails[0].remark", null);

        JSONPath.set(o, "data.trackingDetails[1].infoDatetime", "2021-04-06T15:40:18Z");
        JSONPath.set(o, "data.trackingDetails[1].infoType", "包裹已测量");
        JSONPath.set(o, "data.trackingDetails[1].infoContent", "包裹已测量");
        JSONPath.set(o, "data.trackingDetails[1].location", "韩国PAJU仓");
        JSONPath.set(o, "data.trackingDetails[1].deliveryName", null);
        JSONPath.set(o, "data.trackingDetails[1].contactInfo", null);
        JSONPath.set(o, "data.trackingDetails[1].signedName", null);
        JSONPath.set(o, "data.trackingDetails[1].remark", null);

        return o;
    }

    private JSONObject mockEmpty() {
        JSONObject o = mockResp();
        JSONPath.set(o, "code", "00000");
        JSONPath.set(o, "data.sstWaybillNumber", "empty");
        JSONPath.set(o, "data.trackingDetails", new JSONArray());
        return o;
    }

    private JSONObject mockError(String msg) {
        JSONObject o = new JSONObject();
        JSONPath.set(o, "code", "50000");
        JSONPath.set(o, "message", msg);
        JSONPath.set(o, "timestamp", SDF.format(LocalDateTime.now()));
        return o;
    }

    @GetMapping("/openapi/shipment/trackinfo")
    public Object trackinfo(@RequestParam("bizParams") String bizParams) {
        JSONObject biz;
        try {
            biz = JSON.parseObject(bizParams);
        } catch (Exception e) {
            return mockError("json error");
        }
        String no = biz.getString("sstWaybillNumber");
        if (Objects.equals("success", no)) {
            return mockSuccess();
        }
        if (Objects.equals("error", no)) {
            return mockError("server error");
        }
        return mockEmpty();
    }


}
