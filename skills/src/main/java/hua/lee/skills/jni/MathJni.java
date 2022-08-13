package hua.lee.skills.jni;

public class MathJni {
    static {
        System.loadLibrary("native_demo");
    }

    public static native int add(int x, int y);
}
