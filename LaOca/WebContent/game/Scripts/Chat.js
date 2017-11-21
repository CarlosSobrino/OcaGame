"use strict";
$(document).ready(function(){
    var box = null;
      $("#chat").click(function(event, ui) {
          if(box) {
              box.chatbox("option", "boxManager").toggleBox();
          }
          else {
              box = $("#chat_div").chatbox({id:"chat_div", 
                                            user:{key : "value"},
                                            title : "C",
                                            messageSent : function(id, user, msg) {
                                                $("#log").append(id + " said: " + msg + "<br/>");
                                                $("#chat_div").chatbox("option", "boxManager").addMsg(id, msg);
                                            }});
          }
      });
  });
