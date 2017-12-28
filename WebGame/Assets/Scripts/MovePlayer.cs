using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovePlayer : MonoBehaviour {

    private int _Casilla;
	// Use this for initialization
	void Start () {
        Move(0);
        _Casilla = 0;
	}
	
	// Update is called once per frame
	void Update () {
		
	}
    public void MoveCasilla(int n)
    {
        _Casilla = n;
        GameObject casilla = GameObject.Find("Casilla" + _Casilla);
        gameObject.transform.position = casilla.transform.position;
        Vector3 position = new Vector3();
        float ditanceSeparation = gameObject.transform.localScale.x / 1.8f;
        if (gameObject.name == "Player1")
        {
            position = gameObject.transform.position - (casilla.transform.forward.normalized * ditanceSeparation) + (casilla.transform.right.normalized * ditanceSeparation);
        }
        else if (gameObject.name == "Player2")
        {
            position = gameObject.transform.position - (casilla.transform.forward.normalized * ditanceSeparation) - (casilla.transform.right.normalized * ditanceSeparation); ;
        }
        else if (gameObject.name == "Player3")
        {
            position = gameObject.transform.position + (casilla.transform.forward.normalized * ditanceSeparation) + (casilla.transform.right.normalized * ditanceSeparation); ;
        }
        else if (gameObject.name == "Player4")
        {
            position = gameObject.transform.position + (casilla.transform.forward.normalized * ditanceSeparation) - (casilla.transform.right.normalized * ditanceSeparation); ;
        }

        gameObject.transform.position = position;
    }

        public void Move(int n)
    {
        _Casilla += n;
        GameObject casilla = GameObject.Find("Casilla" + _Casilla);
        gameObject.transform.position = casilla.transform.position;
        Vector3 position = new Vector3();
        float ditanceSeparation = gameObject.transform.localScale.x / 1.8f;
        if (gameObject.name == "Player1")
        {
            position = gameObject.transform.position - (casilla.transform.forward.normalized * ditanceSeparation) + (casilla.transform.right.normalized * ditanceSeparation); 
        }
        else if (gameObject.name == "Player2")
        {
            position = gameObject.transform.position - (casilla.transform.forward.normalized * ditanceSeparation) - (casilla.transform.right.normalized * ditanceSeparation); ;
        }
        else if (gameObject.name == "Player3")
        {
            position = gameObject.transform.position + (casilla.transform.forward.normalized * ditanceSeparation) + (casilla.transform.right.normalized * ditanceSeparation); ;
        }
        else if (gameObject.name == "Player4")
        {
            position = gameObject.transform.position + (casilla.transform.forward.normalized * ditanceSeparation) - (casilla.transform.right.normalized * ditanceSeparation); ;
        }

        gameObject.transform.position = position;
    }
}
