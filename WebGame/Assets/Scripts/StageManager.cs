using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class StageManager : MonoBehaviour {

    void Awake()
    {
    #if !UNITY_EDITOR && UNITY_WEBGL
    WebGLInput.captureAllKeyboardInput = false;
    #endif
    }
    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void PermitirDado()
    {
        GameObject.Find("Dado").GetComponent<RollingDice>().ChangeState(1);
    }
    public void MovePlayer(int player, int ndado)
    {
        GameObject.Find("Player" + player).GetComponent<MovePlayer>().Move(ndado);
    }
    public void MovePlayer(string data)
    {
        try
        {
            string[] words = data.Split('\n');
            GameObject.Find("Player" + words[0]).GetComponent<MovePlayer>().Move(Int32.Parse(words[1]));
        }
        catch(Exception e){
            Debug.LogError(e.ToString());
        }
      
    }

    public void InformacionTablero(string data)
    {
        try
        {
            string[] words = data.Split('\n');
            GameObject.Find("NamePlayer1").GetComponent<Text>().text = words[0];
            GameObject.Find("NamePlayer2").GetComponent<Text>().text = words[1];
            GameObject.Find("NamePlayer3").GetComponent<Text>().text = words[2];
            GameObject.Find("NamePlayer4").GetComponent<Text>().text = words[3];
        }
        catch (Exception e)
        {
            Debug.LogError(e.ToString());
        }

    }
}
