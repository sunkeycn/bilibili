package tech.sunkey.bilibili.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-10 7:15 下午
 **/
@Getter
@Setter
@ToString
public class BaseResult {

    private int code;
    private String message;
    private int ttl;

}
