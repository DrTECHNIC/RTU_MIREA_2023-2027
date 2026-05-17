using Unity.Netcode;
using UnityEngine;

public class PlayerController : NetworkBehaviour
{
    [Header("Movement")]
    public float speed = 5f;
    public float jumpForce = 5f;

    [Header("Components")]
    public Rigidbody rb;
    public MeshRenderer meshRenderer;

    private bool isGrounded;

    // Сетевой цвет
    public NetworkVariable<Color> playerColor =
        new NetworkVariable<Color>();

    void Start()
    {
        playerColor.OnValueChanged += OnColorChanged;

        ApplyColor(playerColor.Value);
    }

    public override void OnNetworkSpawn()
    {
        if (IsOwner)
        {
            Color randomColor = new Color(
                Random.value,
                Random.value,
                Random.value
            );

            SubmitColorServerRpc(randomColor);
        }
    }

    void Update()
    {
        if (!IsOwner)
            return;

        Move();
        Jump();
    }

    void Move()
    {
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");

        Vector3 move =
            new Vector3(h, 0, v);

        transform.position +=
            move * speed * Time.deltaTime;
    }

    void Jump()
    {
        if (Input.GetKeyDown(KeyCode.Space)
            && isGrounded)
        {
            rb.AddForce(
                Vector3.up * jumpForce,
                ForceMode.Impulse
            );
        }
    }

    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
        {
            isGrounded = true;
        }
    }

    private void OnCollisionExit(Collision collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
        {
            isGrounded = false;
        }
    }

    [ServerRpc]
    void SubmitColorServerRpc(Color color)
    {
        playerColor.Value = color;
    }

    void OnColorChanged(Color oldColor, Color newColor)
    {
        ApplyColor(newColor);
    }

    void ApplyColor(Color color)
    {
        meshRenderer.material.color = color;
    }
}
