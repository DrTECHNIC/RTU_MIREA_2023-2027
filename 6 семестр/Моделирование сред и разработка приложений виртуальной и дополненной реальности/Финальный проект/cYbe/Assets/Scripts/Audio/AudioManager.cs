using UnityEngine;

public class AudioManager : MonoBehaviour
{
    [Header("Источник звука")]
    public AudioSource source;

    [Header("Звуки")]
    public AudioClip jumpSound;
    public AudioClip ambientSound;

    void Start()
    {
        source.loop = true;
        source.clip = ambientSound;
        source.Play();
    }

    public void PlayJump()
    {
        source.PlayOneShot(jumpSound);
    }
}
