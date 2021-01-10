package tech.sunkey.bilibili.api.utils;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Sunkey
 * @since 2021-01-10 7:00 下午
 **/
public class HttpUtils {

    private static OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    public static String get(String url) {
        Response response = client.newCall(new Request.Builder()
                .url(url)
                .get()
                .build()).execute();
        return response.body().string();
    }

}
