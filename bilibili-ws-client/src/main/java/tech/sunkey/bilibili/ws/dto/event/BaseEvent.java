package tech.sunkey.bilibili.ws.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-11 3:07 下午
 **/
@Getter
@Setter
@ToString
public class BaseEvent {

    private String cmd;

}
