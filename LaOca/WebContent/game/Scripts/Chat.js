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
                                                $("#div_chat").chatbox("option", "boxManager").addMsg(nick, msg);
                                                WSManager.send("CHAT",msg);
                                            }});
          }
}
function printChat(nick_arg,msg){
	try{
		$("#div_chat").chatbox("option", "boxManager").addMsg(nick_arg, msg);
	}catch (e) {
		// TODO: handle exception
	}
}
