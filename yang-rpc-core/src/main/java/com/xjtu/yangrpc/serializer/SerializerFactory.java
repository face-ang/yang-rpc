package com.xjtu.yangrpc.serializer;

import com.xjtu.yangrpc.spi.SpiLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 * 序列化器对象是可以复用的，没必要每次执行序列化操作前都创建一个新的对象。
 * 使用 工厂模式 + 单例模式 来简化创建和获取序列化器对象的操作。
 */
public class SerializerFactory {
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
