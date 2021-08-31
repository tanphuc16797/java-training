package com.jamesye.starter.realtimeserver.consummer;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatModule implements ConnectListener, DisconnectListener, DataListener<ChatMessage>{

    private static final Logger log = LoggerFactory.getLogger(ChatModule.class);

    private final SocketIONamespace namespace;

    @Autowired
    public ChatModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/chat");
        this.namespace.addConnectListener(this);
        this.namespace.addDisconnectListener(this);
        this.namespace.addEventListener("chat", ChatMessage.class, this);
    }

    @Override
    public void onConnect(SocketIOClient client) {
        HandshakeData handshakeData = client.getHandshakeData();
        String token = handshakeData.getSingleUrlParam("token");
        if (StringUtil.isNotEmpty(token)){
            if (token.equals("aaa")){
                client.set("username", "username1@gmail.com");
                log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
            } else if(token.equals("bbb")){
                client.set("username", "username2@gmail.com");
                log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
            } else {
                log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
                client.disconnect();
            }
        } else {
            log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
            client.disconnect();
        }
    }

    @Override
    public void onData(SocketIOClient client, ChatMessage chatMessage, AckRequest ackRequest) throws Exception {
        log.debug("Client[{}] - Received chat message '{}'", client.getSessionId().toString(), client.get("username"));
        chatMessage.setUserName(client.get("username"));
        namespace.getBroadcastOperations().sendEvent("chat", chatMessage);
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
    }
}
