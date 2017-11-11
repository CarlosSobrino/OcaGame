using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LayautCasilla : MonoBehaviour {

	// Use this for initialization
	void Start () {
        for (int i=0;i<63;i++)
        {
            Debug.Log("loop " + i);
            GameObject casilla = GameObject.Find("Casilla" + i);
            Text myText = casilla.AddComponent<Text>();
            myText.text = ""+i;
        }
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
