package tech.sunkey.bilibili.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Sunkey
 * @since 2021-01-11 4:38 下午
 **/
@Getter
@Setter
@ToString
public class DanmuVO {

    private String msg;
    private int uid;
    private String uname;
    private Date time;

}
