package tech.sunkey.bilibili.api.utils;

import com.alibaba.fastjson.JSON;
import tech.sunkey.bilibili.api.Config;

/**
 * @author Sunkey
 * @since 2021-01-10 7:00 下午
 **/
public class BaseAPI {

    protected <T> T get(String methodPath, Class<T> rType, Object... params) {
        String url = url(methodPath, params);
        String repsonseBody = HttpUtils.get(url);
        return JSON.parseObject(repsonseBody, rType);
    }

    protected String url(String pathTpl, Object... params) {
        return Config.rootPath + String.format(pathTpl, params);
    }

}
