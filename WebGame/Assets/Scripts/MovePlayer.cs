using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovePlayer : MonoBehaviour {

	// Use this for initialization
	void Start () {
        gameObject.transform.position = GameObject.Find("Casilla0").transform.position;
	}
	
	// Update is called once per frame
	void Update () {
		
	}
    public void Move(int casilla)
    {
        gameObject.transform.position = GameObject.Find("Casilla"+casilla).transform.position;
    }
}
