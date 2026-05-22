using UnityEngine;

public class KillZone : MonoBehaviour
{
    [Header("Точка появления")]
    public Transform respawnPoint;

    void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Player"))
            other.transform.position = respawnPoint.position;
    }
}
