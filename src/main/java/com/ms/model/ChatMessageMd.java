package com.ms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageMd {
    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    //보낸 시간 변수 만들기
}
