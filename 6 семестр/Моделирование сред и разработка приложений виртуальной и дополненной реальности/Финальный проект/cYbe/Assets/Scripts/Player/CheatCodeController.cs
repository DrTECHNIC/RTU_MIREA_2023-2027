using UnityEngine;

public class CheatCodeController : MonoBehaviour
{
    [Header("Скорость полета")]
    public float flySpeed = 10f;

    string cheatCode = "HESOYAM";
    string currentInput = "";
    bool flyMode = false;

    Rigidbody rb;
    Collider col;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        col = GetComponent<Collider>();
    }

    void Update()
    {
        CheckInput();
        if (flyMode)
            FlyMovement();
    }

    void CheckInput()
    {
        foreach (char c in Input.inputString)
        {
            currentInput += char.ToUpper(c);
            if (currentInput.Length > cheatCode.Length)
                currentInput = currentInput.Substring(currentInput.Length - cheatCode.Length);
            if (currentInput == cheatCode)
            {
                ActivateCheat();
                currentInput = "";
            }
        }
    }

    void ActivateCheat()
    {
        flyMode = !flyMode;
        if (flyMode)
        {
            rb.useGravity = false;
            rb.linearVelocity = Vector3.zero;
            col.enabled = false;
        }
        else
        {
            rb.useGravity = true;
            col.enabled = true;
        }
    }

    void FlyMovement()
    {
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");
        float up = 0;
        if (Input.GetKey(KeyCode.Space))
            up = 1;
        if (Input.GetKey(KeyCode.LeftControl))
            up = -1;
        Vector3 move = transform.forward * v + transform.right * h + transform.up * up;
        transform.position += move * flySpeed * Time.deltaTime;
    }
}
