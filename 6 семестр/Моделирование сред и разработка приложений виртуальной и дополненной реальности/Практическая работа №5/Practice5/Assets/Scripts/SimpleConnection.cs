using Unity.Netcode;
using UnityEngine;

public class SimpleConnection : MonoBehaviour
{
    public GameObject connectionPanel;

    public void StartHost()
    {
        NetworkManager.Singleton.StartHost();

        connectionPanel.SetActive(false);

        Debug.Log("HOST STARTED");
    }

    public void StartClient()
    {
        NetworkManager.Singleton.StartClient();

        connectionPanel.SetActive(false);

        Debug.Log("CLIENT STARTED");
    }
}
