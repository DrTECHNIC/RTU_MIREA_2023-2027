using UnityEngine;

public class EnemyPatrol : MonoBehaviour
{
    [Header("Характеристики")]
    public float speed = 3f;
    public float patrolRange = 2.5f;
    public float pushForce = 8f;

    Rigidbody rb;
    Vector3 centerPoint;
    Vector3 targetPoint;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        centerPoint = transform.position;
        ChooseNewTarget();
    }

    void FixedUpdate()
    {
        Move();
    }

    void Move()
    {
        Vector3 dir = (targetPoint - transform.position).normalized;
        rb.MovePosition(transform.position + dir * speed * Time.fixedDeltaTime);
        if (Vector3.Distance(transform.position, targetPoint) < 0.3f)
            ChooseNewTarget();
    }

    void ChooseNewTarget()
    {
        float randomX = Random.Range(-patrolRange, patrolRange);
        float randomZ = Random.Range(-patrolRange, patrolRange);
        targetPoint = new Vector3(centerPoint.x + randomX, centerPoint.y, centerPoint.z + randomZ);
    }

    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            Rigidbody prb = collision.gameObject.GetComponent<Rigidbody>();
            Vector3 dir = (collision.transform.position - transform.position).normalized;
            prb.AddForce((dir + Vector3.up * 0.5f) * pushForce, ForceMode.Impulse);
        }
    }
}
