using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;
using UnityEngine.UI;

public class RollingDice : MonoBehaviour {
    [DllImport("__Internal")]
    private static extern void LanzarDado();

    private int State;
    //State 0 : Cuando el dado esta en la mesa y el boton desactivado    
    //State 1 : Cuando el dado esta en la mesa y el boton activado
    //State 2 : Cuando el dado esta girando, esperando la respuesta del servidor y el boton desactivado
    void Start()
    {
        State = 0;
    }
     void Update()
    {
        if(State == 2)
        {
            transform.Rotate(7, 4, 0.5f);
           
        }
    }

    public void OnClick() //Solo disponible en State 1
    {
        if(State == 1)
        {
            gameObject.transform.position += (new Vector3(0, 2, 0));
            ChangeState(2);
            gameObject.GetComponent<Collider>().attachedRigidbody.useGravity = false;
            LanzarDado(); //Send Request with JavaScript
        }

    }

    public void RollDice(int i)
    {
        if(State == 2)
        {
            ChangeState(0);
            gameObject.transform.localRotation = HitNumber(i);
        }
    }

    private Quaternion HitNumber(int side)
    {
        Quaternion rotation;
        switch (side)
        {
            case 1: return rotation = Quaternion.Euler(new Vector3(270, 0, 0));
            case 2: return rotation = Quaternion.Euler(new Vector3(180, 0, 0));
            case 3: return rotation = Quaternion.Euler(new Vector3(0, 0, 270));
            case 4: return rotation = Quaternion.Euler(new Vector3(0, 0, 90));
            case 5: return rotation = Quaternion.Euler(new Vector3(0, 0, 0));
            case 6: return rotation = Quaternion.Euler(new Vector3(90, 0, 0));
        }
        return rotation = Quaternion.Euler(new Vector3(0, 0, 0));
    }

    public void ChangeState(int state)
    {
        this.State = state;
        if (state == 0)
        {
            gameObject.GetComponent<Collider>().attachedRigidbody.useGravity = true;
            GameObject.Find("TirarDado").GetComponent<Button>().interactable = false;
        }
        else if(state == 1)
        {

            Debug.Log("Funciona?");
            GameObject.Find("TirarDado").GetComponent<Button>().interactable = true;
        }
        else if (state == 2)
        {

        }
    }

}

