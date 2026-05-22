using UnityEngine;
using UnityEngine.AI;

public class EnemyChaserNavMesh : MonoBehaviour
{
    Transform player;
    NavMeshAgent agent;

    public float pushForce = 10f;

    void Start()
    {
        agent = GetComponent<NavMeshAgent>();
        GameObject playerObj = GameObject.FindGameObjectWithTag("Player");
        if (playerObj != null)
            player = playerObj.transform;
    }

    void Update()
    {
        if (player == null) return;
        agent.SetDestination(player.position);
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            Rigidbody prb = collision.gameObject.GetComponent<Rigidbody>();
            Vector3 dir = (collision.transform.position - transform.position).normalized;
            prb.AddForce((dir + Vector3.up) * pushForce, ForceMode.Impulse);
        }
    }
}
