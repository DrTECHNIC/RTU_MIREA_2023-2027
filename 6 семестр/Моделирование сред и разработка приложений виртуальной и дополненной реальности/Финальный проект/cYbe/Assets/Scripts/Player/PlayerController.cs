using UnityEngine;

public class PlayerController : MonoBehaviour
{
    [Header("Движение")]
    public float moveSpeed = 8f;
    public float acceleration = 10f;

    [Header("Прыжок")]
    public float jumpForce = 8f;
    public float jumpCutMultiplier = 0.5f;
    public ParticleSystem jumpEffect;
    public Animator animator;

    [Header("Механики платформера")]
    public float coyoteTime = 0.15f;
    public float jumpBufferTime = 0.15f;

    Rigidbody rb;
    float coyoteTimer;
    float jumpBufferTimer;
    bool isGrounded;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    void Update()
    {
        HandleJumpInput();
        HandleJumpLogic();
        animator.SetBool("isGrounded", isGrounded);
        animator.SetFloat("verticalVelocity", rb.linearVelocity.y);
    }

    void FixedUpdate()
    {
        Move();
    }

    void Move()
    {
        float x = Input.GetAxis("Horizontal");
        float z = Input.GetAxis("Vertical");
        Vector3 move = transform.right * x + transform.forward * z;
        rb.linearVelocity = new Vector3(move.x * moveSpeed, rb.linearVelocity.y, move.z * moveSpeed);
    }

    void HandleJumpInput()
    {
        if (Input.GetButtonDown("Jump"))
            jumpBufferTimer = jumpBufferTime;
        else
            jumpBufferTimer -= Time.deltaTime;
        if (Input.GetButtonUp("Jump") && rb.linearVelocity.y > 0)
            rb.linearVelocity = new Vector3(rb.linearVelocity.x, rb.linearVelocity.y * jumpCutMultiplier, rb.linearVelocity.z);
    }

    void HandleJumpLogic()
    {
        if (isGrounded)
            coyoteTimer = coyoteTime;
        else
            coyoteTimer -= Time.deltaTime;
        if (jumpBufferTimer > 0 && coyoteTimer > 0)
        {
            jumpEffect.Play();
            rb.linearVelocity = new Vector3(rb.linearVelocity.x, jumpForce, rb.linearVelocity.z);
            jumpBufferTimer = 0;
        }
    }

    void OnCollisionStay(Collision collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
            isGrounded = true;
    }

    void OnCollisionExit(Collision collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
            isGrounded = false;
    }
}
