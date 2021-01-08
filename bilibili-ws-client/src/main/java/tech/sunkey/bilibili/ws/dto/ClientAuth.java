package tech.sunkey.bilibili.ws.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-08 6:02 下午
 **/
@Getter
@Setter
@ToString
public class ClientAuth {
    private int uid;
    private int roomid;
    private int protover;
    private String platform;//web
    private String clientver;//2.6.0
    private int type;//2
    private String key;//_ZSlrFe8a_tttN61991VDsw8e6NyCbxWsroL88DwW_GuU7JrVvE_lCHM7h7aTEEtimtshHOhQ-ScTto6EcwYSEWPUGM0WFhqrXuNKw5m43zqXs6MBm2NkoGEIegcEicn-iqpGgXcCc0l-y0LrTO8Pw==

}
