using UnityEngine;

public class EnemyBlocker : EnemyBase
{
    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player")) {}
    }
}
