package info.xiaomo.gameCore.network.pool;

import com.google.protobuf.AbstractMessage;
import info.xiaomo.gameCore.network.AbstractHandler;
import info.xiaomo.gameCore.network.IMessageAndHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * いま 最高の表現 として 明日最新の始発．．～
 * Today the best performance  as tomorrow newest starter!
 * Created by IntelliJ IDEA.
 * <p>
 * author: xiaomo
 * github: https://github.com/xiaomoinfo
 * email : xiaomo@xiaomo.info
 * QQ    : 83387856
 * Date  : 2017/9/11 15:51
 * desc  :
 * Copyright(©) 2017 by xiaomo.
 */
public class MessageAndHandlerPool implements IMessageAndHandler {

    // 消息类字典
    private final Map<Integer, AbstractMessage> messages = new HashMap<>();

    /**
     * 类和
     */
    private final Map<String, Integer> ids = new HashMap<>();

    private final Map<String, Class<? extends AbstractHandler>> handlers = new HashMap<>();

    @Override
    public AbstractMessage getMessage(int messageId) {
        return messages.get(messageId);
    }

    @Override
    public int getMessageId(AbstractMessage message) {
        return ids.get(message.getClass().getName());
    }


    @Override
    public AbstractHandler getHandler(String handlerName) {
        Class<? extends AbstractHandler> clazz = handlers.get(handlerName);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void register(int messageId, AbstractMessage messageClazz, Class<? extends AbstractHandler> handler) {
        messages.put(messageId, messageClazz);
        handlers.put(messageClazz.getClass().getName(), handler);
        ids.put(messageClazz.getClass().getName(), messageId);
    }

    @Override
    public void register(int messageId, AbstractMessage messageClazz) {
        messages.put(messageId, messageClazz);
        ids.put(messageClazz.getClass().getName(), messageId);
    }

}
