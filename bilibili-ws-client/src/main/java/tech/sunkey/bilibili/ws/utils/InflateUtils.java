package tech.sunkey.bilibili.ws.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

/**
 * @author Sunkey
 * @since 2021-01-09 5:12 下午
 **/
@Slf4j
public class InflateUtils {

    public static byte[] inflate(byte[] data) {
        int len;
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        byte[] outByte = new byte[1024];
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            while (!inflater.finished()) {
                len = inflater.inflate(outByte);
                if (len == 0) {
                    break;
                }
                bos.write(outByte, 0, len);
            }
            inflater.end();
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("inflate failed: {}", e.getMessage(), e);
            return null;
        }
    }

}
