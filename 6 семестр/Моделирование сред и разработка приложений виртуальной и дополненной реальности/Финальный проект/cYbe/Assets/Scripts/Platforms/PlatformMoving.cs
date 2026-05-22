using UnityEngine;

public class MovingPlatform : MonoBehaviour
{
    [Header("Характеристики")]
    public Vector3 moveDirection = Vector3.right;
    public float moveDistance = 3f;
    public float speed = 2f;

    Vector3 startPos;

    void Start()
    {
        startPos = transform.position;
    }

    void Update()
    {
        float offset = Mathf.Sin(Time.time * speed) * moveDistance;
        transform.position = startPos + moveDirection * offset;
    }
}
