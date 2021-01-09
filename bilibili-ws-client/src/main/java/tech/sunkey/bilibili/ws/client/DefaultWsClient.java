package tech.sunkey.bilibili.ws.client;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-08 1:44 下午
 **/
@Slf4j
public class DefaultWsClient extends WsClient {

    public static final Header<String> AcceptEncoding = Header.of("Accept-Encoding");
    public static final Header<String> AcceptLanguage = Header.of("Accept-Language");
    public static final Header<String> Host = Header.of("Host");
    public static final Header<String> Origin = Header.of("Origin");
    public static final Header<String> Pragma = Header.of("Pragma");
    public static final Header<String> SecureWebSocketExtensions = Header.of("Sec-WebSocket-Extensions");
    public static final Header<String> UserAgent = Header.of("User-Agent");

    static final String VALUE_MOCK_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36";

    protected List<HeaderValue> getHeaders(Config config) {
        List<HeaderValue> list = new ArrayList<>();
        list.add(AcceptEncoding.value("gzip, deflate, br"));
        list.add(AcceptLanguage.value("zh-CN,zh;q=0.9"));
        list.add(Host.value(config.getHost()));
        list.add(Origin.value("https://live.bilibili.com"));
        list.add(Pragma.value("no-cache"));
        list.add(SecureWebSocketExtensions.value("permessage-deflate; client_max_window_bits"));
        list.add(UserAgent.value(VALUE_MOCK_UA));
        return list;
    }

    @Override
    protected DefaultHttpHeaders createHttpHeaders(Config config) {
        DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
        for (HeaderValue header : getHeaders(config)) {
            httpHeaders.add(header.getHeader(), header.getValue());
        }
        return httpHeaders;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Header<T> {

        private final String header;

        public HeaderValue value(T value) {
            return new HeaderValue<T>(header, value);
        }

        public static <T> Header<T> of(String name) {
            return new Header<>(name);
        }

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HeaderValue<T> {
        private String header;
        private T value;
    }

}
