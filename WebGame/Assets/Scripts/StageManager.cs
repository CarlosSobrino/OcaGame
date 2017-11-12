using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class StageManager : MonoBehaviour {

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
    public void InformacionTablero(string player1, string player2,string player3,string player4)
    {
        GameObject.Find("NamePlayer1").GetComponent<Text>().text = player1;
        GameObject.Find("NamePlayer2").GetComponent<Text>().text = player2;
        GameObject.Find("NamePlayer3").GetComponent<Text>().text = player3;
        GameObject.Find("NamePlayer4").GetComponent<Text>().text = player4;
    }
}
