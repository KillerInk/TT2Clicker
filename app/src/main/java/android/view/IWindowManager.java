package android.view;

import android.os.IBinder;

public interface IWindowManager {
    public static class Stub {
        public static IWindowManager asInterface( IBinder binder ) {
            return null;
        }
    }

    // These can only be called when injecting events to your own window,
    // or by holding the INJECT_EVENTS permission.  These methods may block
    // until pending input events are finished being dispatched even when 'sync' is false.
    // Avoid calling these methods on your UI thread or use the 'NoWait' version instead.
    public boolean injectKeyEvent(KeyEvent ev, boolean sync);
    public boolean injectPointerEvent(MotionEvent ev, boolean sync);
    public boolean injectTrackballEvent(MotionEvent ev, boolean sync);
    //public boolean injectInputEventNoWait(InputEvent ev);
}
