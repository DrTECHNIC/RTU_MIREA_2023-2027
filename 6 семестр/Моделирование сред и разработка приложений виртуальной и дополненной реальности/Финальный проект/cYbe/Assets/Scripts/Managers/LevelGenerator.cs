using UnityEngine;

public class LevelGenerator : MonoBehaviour
{
    [Header("Платформы")]
    public GameObject normalPlatform;
    public GameObject smallPlatform;
    public GameObject movingPlatform;

    [Header("Мосты")]
    public GameObject bridgeX;
    public GameObject bridgeZ;

    [Header("Противники")]
    public GameObject enemyRed;
    public GameObject enemyBlue;
    public GameObject enemyBlack;

    [Header("Настройки башни")]
    public int platformCount = 100;

    public float minHeightStep = 2f;
    public float maxHeightStep = 3f;

    public float minHorizontal = 1f;
    public float maxHorizontal = 5f;

    [Header("Настройки мостов")]
    public float bridgeChance = 0.2f;
    public float minBridgeLength = 10f;
    public float maxBridgeLength = 20f;

    [Header("Земные противники")]
    public int groundEnemies = 10;
    public float groundArea = 15f;

    Vector3 lastPos;

    void Start()
    {
        SpawnGroundEnemies();
        lastPos = new Vector3(0, 3f, 0);
        Instantiate(normalPlatform, lastPos, Quaternion.identity);
        GenerateLevel();
    }

    void GenerateLevel()
    {
        for (int i = 0; i < platformCount; i++)
        {
            if (Random.value < bridgeChance)
                CreateBridgeSegment();
            else
                CreateNormalSegment();
        }
    }

    void CreateNormalSegment()
    {
        float height = Random.Range(minHeightStep, maxHeightStep);
        float horizontal = Random.Range(minHorizontal, maxHorizontal);
        Vector3 dir = RandomHorizontalDirection();
        Vector3 newPos = lastPos + dir * horizontal;
        newPos.y += height;
        GameObject prefab = ChoosePlatform();
        Instantiate(prefab, newPos, Quaternion.identity);
        SpawnPlatformEnemy(newPos);
        lastPos = newPos;
    }

    void CreateBridgeSegment()
    {
        float height = Random.Range(minHeightStep, maxHeightStep);
        float distance = Random.Range(minBridgeLength, maxBridgeLength);
        Vector3 dir = RandomHorizontalDirection();
        Vector3 newPos = lastPos + dir * distance;
        newPos.y += height;
        Instantiate(normalPlatform, newPos, Quaternion.identity);
        SpawnPlatformEnemy(newPos);
        CreateBridge(lastPos, newPos, dir);
        lastPos = newPos;
    }

    void CreateBridge(Vector3 pointA, Vector3 pointB, Vector3 dir)
    {
        Vector3 center = (pointA + pointB) / 2f;
        float distance = Vector3.Distance(pointA, pointB);
        GameObject bridge;
        if (Mathf.Abs(dir.x) > 0)
        {
            bridge = Instantiate(bridgeX, center, Quaternion.identity);
            Vector3 scale = bridge.transform.localScale;
            scale.x = distance;
            bridge.transform.localScale = scale;
        }
        else
        {
            bridge = Instantiate(bridgeZ, center, Quaternion.identity);
            Vector3 scale = bridge.transform.localScale;
            scale.z = distance;
            bridge.transform.localScale = scale;
        }
    }

    Vector3 RandomHorizontalDirection()
    {
        int dir = Random.Range(0, 4);
        if (dir == 0) return Vector3.right;
        if (dir == 1) return Vector3.left;
        if (dir == 2) return Vector3.forward;
        return Vector3.back;
    }

    GameObject ChoosePlatform()
    {
        float r = Random.value;
        if (r < 0.5f)
            return normalPlatform;
        if (r < 0.75f)
            return smallPlatform;
        return movingPlatform;
    }

    void SpawnPlatformEnemy(Vector3 platformPos)
    {
        float r = Random.value;
        Vector3 enemyPos = platformPos + Vector3.up * 1.2f;
        if (r < 0.2f)
            Instantiate(enemyBlack, enemyPos, Quaternion.identity);
        else if (r < 0.4f)
            Instantiate(enemyBlue, enemyPos, Quaternion.identity);
    }

    void SpawnGroundEnemies()
    {
        for (int i = 0; i < groundEnemies; i++)
        {
            float x = Random.Range(-groundArea, groundArea);
            float z = Random.Range(-groundArea, groundArea);
            Vector3 pos = new Vector3(x, 1f, z);
            Instantiate(enemyRed, pos, Quaternion.identity);
        }
    }
}
