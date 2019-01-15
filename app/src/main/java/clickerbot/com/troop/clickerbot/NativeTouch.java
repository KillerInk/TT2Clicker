package clickerbot.com.troop.clickerbot;

import java.nio.ByteBuffer;

public class NativeTouch
{
    static
    {
        System.loadLibrary("touch");
    }

    private native ByteBuffer init();
    private native void open(ByteBuffer byteBuffer, String path);
    private native void close(ByteBuffer byteBuffer);
    private native void sendEvent(ByteBuffer byteBuffer,int type,int code,int value);

    private ByteBuffer byteBuffer;
    public NativeTouch()
    {
        this.byteBuffer = init();
    }

    public void open(String path)
    {
        if (byteBuffer != null)
            open(byteBuffer,path);
    }

    public void sendEvent(int type, int code, int value)
    {
        if (byteBuffer != null)
            sendEvent(byteBuffer,type,code,value);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (byteBuffer == null)
            return;
        close(byteBuffer);
        byteBuffer = null;
    }
}
