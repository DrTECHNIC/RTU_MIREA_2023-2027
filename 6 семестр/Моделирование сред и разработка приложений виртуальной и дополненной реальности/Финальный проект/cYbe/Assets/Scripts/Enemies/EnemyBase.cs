using UnityEngine;

public class EnemyBase : MonoBehaviour
{
    protected Rigidbody rb;

    protected virtual void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            Rigidbody prb = collision.gameObject.GetComponent<Rigidbody>();
        }
    }
}
