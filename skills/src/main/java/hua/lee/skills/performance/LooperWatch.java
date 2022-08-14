package hua.lee.skills.performance;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LooperWatch {

    private static final String TAG = "LooperWatch";

    public static void startWatch(final Looper looper,
                                  final int costWarningTime,
                                  final int delayWarningTime) {
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                // 消息队列
                MessageQueue queue = null;
                // next() 方法
                Method nextMethod = null;
                // recycleUnchecked() 方法
                Method recycleUnchecked = null;

                try {
                    // 反射 Looper 类的 消息队列 成员信息
                    Field queueField = Looper.class.getDeclaredField("mQueue");
                    // 反射 MessageQueue 类的 next() 方法信息
                    nextMethod = MessageQueue.class.getDeclaredMethod("next");
                    // 反射 Message 类的 recycleUnchecked() 方法信息
                    recycleUnchecked = Message.class.getDeclaredMethod("recycleUnchecked");

                    // 关闭访问检查
                    queueField.setAccessible(true);
                    nextMethod.setAccessible(true);
                    recycleUnchecked.setAccessible(true);

                    queue = (MessageQueue) queueField.get(looper);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (queue == null) {
                    Log.d(TAG, "message queue is null");
                    return;
                }

                // Make sure the identity of this thread is that of the local process,
                // and keep track of what that identity token actually is.
                Binder.clearCallingIdentity();
                final long ident = Binder.clearCallingIdentity();

                for (; ; ) {
                    Message msg = null;
                    try {
                        // next() 取消息
                        msg = (Message) nextMethod.invoke(queue); // might block
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (msg == null) { // exit watch loop
                        Log.d(TAG, "message is null, return");
                        return;
                    }

                    final long dispatchStart = SystemClock.uptimeMillis();
                    long when = msg.getWhen();

                    long delay = 0;
                    if (when > 0) {
                        delay = dispatchStart - when;
                    }
                    if (delay >= delayWarningTime) {
                        Log.d(TAG, "msg delay dispatch=====");
                        Log.d(TAG, msg.getTarget().toString() + " delay:" + delay);
                        Log.d(TAG, "msg delay dispatch=====");
                    }

                    msg.getTarget().dispatchMessage(msg);

                    final long dispatchEnd = SystemClock.uptimeMillis();
                    long cost = dispatchEnd - dispatchStart;

                    if (cost >= costWarningTime) {
                        Log.d(TAG, "msg cost more warning=====");
                        Log.d(TAG, msg.getTarget().toString() + " cost:" + cost);
                        Log.d(TAG, "msg cost more warning=====");
                    }

                    // Make sure that during the course of dispatching the
                    // identity of the thread wasn't corrupted.
                    final long newIdent = Binder.clearCallingIdentity();
                    if (ident != newIdent) {
                        Log.wtf(TAG, "Thread identity changed from 0x"
                                + Long.toHexString(ident) + " to 0x"
                                + Long.toHexString(newIdent) + " while dispatching to "
                                + msg.getTarget().getClass().getName() + " "
                                + msg.getCallback() + " what=" + msg.what);
                    }

                    try {
                        recycleUnchecked.invoke(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
