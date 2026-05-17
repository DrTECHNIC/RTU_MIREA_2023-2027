using Unity.Netcode;

public struct PlayerData : INetworkSerializable
{
    public float x;
    public float y;
    public float z;

    public void NetworkSerialize<T>(BufferSerializer<T> serializer)
        where T : IReaderWriter
    {
        serializer.SerializeValue(ref x);
        serializer.SerializeValue(ref y);
        serializer.SerializeValue(ref z);
    }
}
