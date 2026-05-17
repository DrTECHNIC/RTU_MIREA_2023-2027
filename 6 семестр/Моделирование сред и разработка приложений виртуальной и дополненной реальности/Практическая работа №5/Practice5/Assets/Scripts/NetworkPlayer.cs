using Unity.Netcode;
using UnityEngine;

public class NetworkPlayer : NetworkBehaviour
{
    void Update()
    {
        if (IsOwner)
        {
            PlayerData data = new PlayerData
            {
                x = transform.position.x,
                y = transform.position.y,
                z = transform.position.z
            };

            SendDataServerRpc(data);
        }
    }

    [ServerRpc]
    void SendDataServerRpc(PlayerData data)
    {
        Debug.Log(
            $"DATA: {data.x} {data.y} {data.z}"
        );
    }
}
