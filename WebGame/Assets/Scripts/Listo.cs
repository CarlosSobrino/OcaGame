using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;
using UnityEngine.UI;

public class Listo : MonoBehaviour {
    [DllImport("__Internal")]
    private static extern void SendListo();
    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    public void OnClickListo()
    {
        SendListo();
        GameObject.Find("ListoButton").GetComponent<Button>().enabled=false;
    }
    
}
