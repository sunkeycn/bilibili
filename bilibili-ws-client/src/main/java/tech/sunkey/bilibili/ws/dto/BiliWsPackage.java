package tech.sunkey.bilibili.ws.dto;

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

}
