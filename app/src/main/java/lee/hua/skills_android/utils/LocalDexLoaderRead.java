package lee.hua.skills_android.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * 首先在加载时需要两个ClassLoader，一个是运行时自带的PathClassLoader
 * 一个是加载补丁dex文件的DexClassLoader
 * <p>
 * Created by lijie on 2018/3/1.
 */

public class LocalDexLoaderRead {
    public static void fixBug(Context context, File file) {
        Object localPathList = getSystemPathList(context);
        Object patchPathList = getPatchDexPathList(file, context.getApplicationInfo().dataDir, context.getClassLoader());

        Object localElements = getElements(localPathList);
        Object patchElements = getElements(patchPathList);

        Object finalElements = mergeElements(localElements, patchElements);
        //用插入补丁后的新Elements[]替换系统Elements[]
        try {
            Field fElements = localPathList.getClass().getDeclaredField("dexElements");
            fElements.setAccessible(true);
            fElements.set(localPathList, finalElements);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DexLoaderField", "fixed failed....");
        }
    }

    /**
     * 获取系统classLoader中的DexPathList对象
     *
     * @param context 上下文对象
     * @return 系统自带PathList
     */
    private static Object getSystemPathList(Context context) {
        //获取系统PathClassLoader
        PathClassLoader pLoader = (PathClassLoader) context.getClassLoader();
        //获取PathClassLoader中的PathList
        Object pPathList = getPathList(pLoader);
        if (pPathList == null) {
            Log.d("DexLoaderField", "get PathClassLoader pathlist failed...");
            return null;
        }
        return pPathList;
    }

    /**
     * 根据补丁文件创建DexClassLoader对象，并获取其中的PathList对象
     *
     * @param file    补丁文件
     * @param optPath 优化路径
     * @return PathList对象
     */
    private static Object getPatchDexPathList(File file, String optPath, ClassLoader pLoader) {
        //加载补丁
        DexClassLoader dLoader = new DexClassLoader(file.getAbsolutePath(), optPath, null, pLoader);
        //获取DexClassLoader的pathLit，即BaseDexClassLoader中的pathList
        Object dPathList = getPathList(dLoader);
        if (dPathList == null) {
            Log.d("DexLoaderField", "get DexClassLoader pathList failed...");
            return null;
        }
        return dPathList;
    }

    /**
     * 获取DexElements集合对象
     *
     * @param object pathList对象
     * @return PathList对象中的
     */
    private static Object getElements(Object object) {
        try {
            Class<?> c = object.getClass();
            Field field = c.getDeclaredField("dexElements");
            field.setAccessible(true);
            Object dexElements = field.get(object);

            return dexElements;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将补丁DexElements 插入 系统DexElements 并放在最前
     *
     * @param localElements 系统DexElement集合
     * @param patchElements 补丁DexElement集合
     * @return 合并后的DexElement集合
     */
    private static Object mergeElements(Object localElements, Object patchElements) {
        //判断是否为数组
        if (localElements.getClass().isArray() && patchElements.getClass().isArray()) {
            //获取数组长度
            int pLen = Array.getLength(localElements);
            int dLen = Array.getLength(patchElements);
            //创建新数组
            Object newElements = Array.newInstance(localElements.getClass().getComponentType(), pLen + dLen);
            //循环插入
            for (int i = 0; i < pLen + dLen; i++) {
                if (i < dLen) {
                    Array.set(newElements, i, Array.get(patchElements, i));
                } else {
                    Array.set(newElements, i, Array.get(localElements, i - dLen));
                }
            }
            return newElements;
        }
        return null;
    }

    /**
     * 通过反射机制获取PathList
     *
     * @param loader
     * @return DexPathList对象
     */
    private static Object getPathList(BaseDexClassLoader loader) {
        try {
            Class<?> c = Class.forName("dalvik.system.BaseDexClassLoader");
            //获取成员变量pathList
            Field fPathList = c.getDeclaredField("pathList");
            //抑制jvm检测访问权限
            fPathList.setAccessible(true);
            //获取成员变量pathList的值
            Object obj = fPathList.get(loader);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
