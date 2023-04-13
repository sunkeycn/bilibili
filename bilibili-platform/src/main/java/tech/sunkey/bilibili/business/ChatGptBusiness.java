package tech.sunkey.bilibili.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.stereotype.Component;
import tech.sunkey.bilibili.business.openai.Chat;
import tech.sunkey.bilibili.business.openai.CompletionRes;
import tech.sunkey.bilibili.vo.DanmuVO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ChatGptBusiness {

    private OpenAiStreamClient client;
    private Chat chat = new Chat();
    private final EventSourceListener listener = new ConsoleEventSourceListener() {
        @Override
        public void onEvent(EventSource eventSource, String id, String type, String data) {
            //System.out.println(data);
            if ("[DONE]".equals(data)) {
                chat.assistantFinish();
                return;
            }
            CompletionRes response = JSON.parseObject(data, CompletionRes.class);
            CompletionRes.Choice choice = CollectionUtil.get(response.getChoices(), 0);
            if (choice != null) {
                 if (choice.getDelta() != null) {
                    String content = choice.getDelta().getContent();
                    if (content != null) {
                        chat.assistantAppend(content);
                    }
                }
            }
        }
    };

    @PostConstruct
    public void init() {
        client = OpenAiStreamClient.builder()
                .connectTimeout(50)
                .readTimeout(50)
                .writeTimeout(50)
                .apiKey("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")
                .build();
    }

    public void handleMsg(DanmuVO danmuVO) {
        chat.userSend(danmuVO.getMsg());
        client.streamChatCompletion(chat.getHistory(), listener);
    }

}
