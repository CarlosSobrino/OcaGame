"use strict";
function openChat(){
    var box = null;
          if(box) {
              box.chatbox("option", "boxManager").toggleBox();
          }
          else {
              box = $("#div_chat").chatbox({id:"User", 
                                            user:{key : "value"},
                                            title : "Chat",
                                            messageSent : function(id, user, msg) {
                                                $("#div_chat").chatbox("option", "boxManager").addMsg(id, msg);
                                                WSManager.send("CHAT",msg);
                                            }});
          }
}
function printChat(nick,msg){
	try{
		$("#div_chat").chatbox("option", "boxManager").addMsg(nick, msg);
	}catch (e) {
		// TODO: handle exception
	}
}
