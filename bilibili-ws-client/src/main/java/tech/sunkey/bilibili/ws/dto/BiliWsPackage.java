package tech.sunkey.bilibili.ws.dto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-09 5:00 下午
 **/
@Getter
@Setter
@ToString
public class BiliWsPackage {

    private int packageLength;
    private int headerLength;
    private int version;
    private int operation;
    private int sequence;
    private Object data;


    public BiliWsPackage flip() {
        BiliWsPackage flip = new BiliWsPackage();
        flip.headerLength = 16;
        flip.version = 1;
        flip.operation = operation;
        flip.sequence = 1;
        if (data == null) {
            flip.data = new byte[0];
        } else {
            if (data instanceof String) {
                flip.data = ((String) data).getBytes();
            } else {
                flip.data = JSON.toJSONString(data).getBytes();
            }
        }
        flip.packageLength = 16 + ((byte[]) flip.data).length;
        return flip;
    }

}
