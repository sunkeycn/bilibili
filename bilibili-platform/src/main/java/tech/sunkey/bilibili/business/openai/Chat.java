package tech.sunkey.bilibili.business.openai;


import com.unfbx.chatgpt.entity.chat.Message;
import lombok.Getter;

import java.util.ArrayList;

public class Chat {

    @Getter
    private final ArrayList<Message> history = new ArrayList<>();

    private StringBuilder assistantBuffer;

    public void userSend(String msg) {
        history.add(Message.builder().role(Message.Role.USER).content(msg).build());
    }


    public void assistantAppend(String msg) {
        if (assistantBuffer == null) {
            assistantBuffer = new StringBuilder();
        }
        assistantBuffer.append(msg);
        System.out.print(msg);
    }

    public void assistantFinish() {
        System.out.println();
        if (assistantBuffer == null) {
            return;
        }
        String str = assistantBuffer.toString();
        assistantBuffer = null;
        history.add(Message.builder().role(Message.Role.ASSISTANT).content(str).build());
    }

}
