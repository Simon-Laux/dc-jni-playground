package chat.delta.java;

public class DcEvent {

    public DcEvent(long eventCPtr) {
        this.eventCPtr = eventCPtr;
    }

    @Override protected void finalize() throws Throwable {
        super.finalize();
        unrefEventCPtr();
        eventCPtr = 0;
    }

    public native int    getId       ();
    public native int    getData1Int ();
    public native int    getData2Int ();
    public native String getData2Str ();

    // working with raw c-data
    private long        eventCPtr;    // CAVE: the name is referenced in the JNI
    private native void unrefEventCPtr();
}
