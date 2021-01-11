package tech.sunkey.bilibili.ws.client;

import io.netty.channel.Channel;
import io.netty.handler.logging.LogLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import tech.sunkey.bilibili.ws.dto.UserAuth;

import java.net.URI;

@ToString
public class Config {

    @Getter
    private String url;
    private String host;
    private int port;
    private URI uri;
    @Getter
    private LogLevel logLevel;
    @Getter
    private UserAuth userAuth;
    @Getter
    private ClientHandler handler;

    private final WsClient client;

    public static Config config() {
        return config(new DefaultWsClient());
    }

    public static Config config(WsClient client) {
        return new Config(client);
    }

    public Channel connect() {
        return client.connect(this);
    }

    private Config(WsClient client) {
        this.client = client;
    }

    public Config userAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
        return this;
    }

    public Config handler(ClientHandler handler) {
        this.handler = handler;
        return this;
    }

    public Config logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Config url(String url) {
        this.url = url;
        return this;
    }

    public Config host(String host) {
        this.host = host;
        return this;
    }

    public Config port(int port) {
        this.port = port;
        return this;
    }

    @SneakyThrows
    public URI getURI() {
        if (uri == null) {
            uri = new URI(url);
        }
        return uri;
    }

    public String getHost() {
        if (host == null) {
            host = getURI().getHost();
        }
        return host;
    }

    public int getPort() {
        if (port == 0) {
            port = _getPort();
        }
        return port;
    }

    private int _getPort() {
        URI uri = getURI();
        int port = uri.getPort();
        if (port == -1) {
            if ("wss".equals(uri.getScheme()) || "https".equals(uri.getScheme())) {
                return 443;
            }
            return 80;
        }
        return port;
    }

}