package tech.sunkey.bilibili.ws.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 4:15 下午
 **/
@Getter
@Setter
@ToString
public class DanmuMsg extends BaseEvent {

    private List<Object> info;

}
