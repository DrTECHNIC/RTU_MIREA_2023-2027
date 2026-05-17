using UnityEngine;

public class ModeSwitcher : MonoBehaviour
{
    public GameObject desktopCamera;
    public GameObject XROrigin;

    void Start()
    {
        // По умолчанию обычный режим
        EnableDesktopMode();
    }

    public void EnableDesktopMode()
    {
        desktopCamera.SetActive(true);
        XROrigin.SetActive(false);

        Debug.Log("Desktop mode enabled");
    }

    public void EnableXRMode()
    {
        desktopCamera.SetActive(false);
        XROrigin.SetActive(true);

        Debug.Log("XR mode enabled");
    }
}
