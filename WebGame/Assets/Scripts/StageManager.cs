using System;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;
using UnityEngine.UI;

public class StageManager : MonoBehaviour
{
    public Camera LoadCamera;
    public Camera TableroCamera;

    void Awake()
    {
#if !UNITY_EDITOR && UNITY_WEBGL
    WebGLInput.captureAllKeyboardInput = false;
#endif
    }
    // Use this for initialization
    void Start()
    {
        changeCameraLoad();
    }

    // Update is called once per frame
    void Update()
    {

    }

    public void StartGame()
    {
        changeCameraTablero();
        ShowMsg("Bienvenido");
    }
    public void GirarDado()
    {
        GameObject.Find("Dado").GetComponent<RollingDice>().GirarDado();
    }

    public void PermitirDado()
    {
        GameObject.Find("Dado").GetComponent<RollingDice>().ChangeState(1);

    }
    public void MovePlayer(string data)
    {
        try
        {
            string[] words = data.Split('\n');
            GameObject.Find("Player" + words[0]).GetComponent<MovePlayer>().Move(Int32.Parse(words[1]));
        }
        catch (Exception e)
        {
            Debug.LogError(e.ToString());
        }

    }
    public void LanzarDadoPlayer(int i)
    {
        GameObject.Find("Dado").GetComponent<RollingDice>().RollDice(i);
    }
    public void MoveCasillaPlayer(string data)
    {
        try
        {
            string[] words = data.Split('\n');
            GameObject.Find("Player" + words[0]).GetComponent<MovePlayer>().MoveCasilla(Int32.Parse(words[1]));
        }
        catch (Exception e)
        {
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


    public void ShowMsg(string text)
    {
        GameObject.Find("PopUpText").GetComponent<Text>().text = text;
        StartCoroutine(WaitSec(2.5f));


    }

    IEnumerator WaitSec(float i)
    {
        GameObject.Find("PopUpText").GetComponent<Text>().enabled = true;
        yield return new WaitForSeconds(i);
        GameObject.Find("PopUpText").GetComponent<Text>().enabled = false;
    }

    private void changeCameraTablero()
    {
        TableroCamera.enabled = true;
        LoadCamera.enabled = false;
    }
    private void changeCameraLoad()
    {
        TableroCamera.enabled = false;
        LoadCamera.enabled = true;
    }
}