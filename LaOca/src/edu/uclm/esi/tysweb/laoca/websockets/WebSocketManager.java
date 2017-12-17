package edu.uclm.esi.tysweb.laoca.websockets;

import org.json.JSONObject;

public class WebSocketManager {
	private WebSocketManager(){

	}

	private static class WebSocketManagerHolder{
		static WebSocketManager singleton = new WebSocketManager();
	}

	public static WebSocketManager get() {
		return WebSocketManagerHolder.singleton;
	}
	
	public void ProcessMsg(String type, JSONObject data) {
		
	}
}

