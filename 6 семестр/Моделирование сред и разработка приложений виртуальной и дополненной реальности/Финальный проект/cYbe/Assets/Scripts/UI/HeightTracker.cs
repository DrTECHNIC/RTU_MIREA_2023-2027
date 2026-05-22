using UnityEngine;
using TMPro;

public class HeightTracker : MonoBehaviour
{
    public Transform player;

    public TextMeshProUGUI bestText;
    public TextMeshProUGUI currentText;

    float currentHeight;

    void Start()
    {
        GameData.bestHeight = PlayerPrefs.GetFloat("BestHeight", 0f);
    }

    void Update()
    {
        if (player == null) return;
        currentHeight = player.position.y;
        if (currentHeight > GameData.bestHeight)
        {
            GameData.bestHeight = currentHeight;
            PlayerPrefs.SetFloat("BestHeight", GameData.bestHeight);
        }
        UpdateUI();
    }

    void UpdateUI()
    {
        bestText.text = "Лучшая высота: " + Mathf.FloorToInt(GameData.bestHeight).ToString();
        currentText.text = "Текущая высота: " + Mathf.FloorToInt(currentHeight).ToString();
    }
}
