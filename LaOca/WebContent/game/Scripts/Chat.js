"use strict";
$(document).ready(function(){
    var box = null;
      $("#chat").click(function(event, ui) {
          if(box) {
              box.chatbox("option", "boxManager").toggleBox();
          }
          else {
              box = $("#div_chat").chatbox({id:"User", 
                                            user:{key : "value"},
                                            title : "Chat",
                                            messageSent : function(id, user, msg) {
                                                $("#chat_div").chatbox("option", "boxManager").addMsg(id, msg);
                                                //TODO Enviar por websocket  el mensaje
                                            }});
          }
      });
});
