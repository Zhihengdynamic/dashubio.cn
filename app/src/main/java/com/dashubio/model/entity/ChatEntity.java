package com.dashubio.model.entity;

// 聊天消息实体类
public class ChatEntity {

	private int userImage;  
    private String content;  
    private String chatTime;  
    private boolean isComeMsg;  
  
    public int getUserImage() {  
        return userImage;  
    }  
    public void setUserImage(int userImage) {  
        this.userImage = userImage;  
    }  
    public String getContent() {  
        return content;  
    }  
    public void setContent(String content) {  
        this.content = content;  
    }  
    public String getChatTime() {  
        return chatTime;  
    }  
    public void setChatTime(String chatTime) {  
        this.chatTime = chatTime;  
    }  
    public boolean isComeMsg() {  
        return isComeMsg;  
    }  
    public void setComeMsg(boolean isComeMsg) {  
        this.isComeMsg = isComeMsg;  
    }
	@Override
	public String toString() {
		return "ChatEntity [userImage=" + userImage + ", content=" + content
				+ ", chatTime=" + chatTime + ", isComeMsg=" + isComeMsg + "]";
	} 
    
}
