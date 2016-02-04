package com.my_package;

import android.util.Log;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Created by equesada 
 */
public class RedisClient {

    private static final String TAG = "RedisClient";
    private static final String REDIS_URL = "XX.XXX.XXX.XX";
    private static final String REDIS_PASSWORD = "my_redis_password";
    private static final String CHANNEL_KEY = "my_channel_key";
    private static final int REDIS_PORT = 8080; // my redis port

    private static RedisClient sInstance;

    private RedisListener mRedisListener;
    private Jedis mJedis;
    private RedisClientListener mCallback;

    /**
     * Singleton implementacion
     * @return instance
     */
    public static RedisClient getInstance(){
        if(sInstance == null){
            sInstance = new RedisClient();
        }
        return sInstance;
    }

    public static void clearInstance() {
        sInstance = null;
    }

    public void setListener(RedisClientListener listener) {
        mCallback = listener;
        mRedisListener.setListener(mCallback);
    }

    public void subscribeChannel() {
        if (!mRedisListener.isSubscribed()) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(REDIS_URL, REDIS_PORT);
            jedisShardInfo.setPassword(REDIS_PASSWORD);
            subscribeChannel(jedisShardInfo);
        } else {
            Log.w(TAG, "The client is already subscribed");
        }
    }

    public void unSubscribeChannel() {
        mRedisListener.unsubscribe();
    }


    public boolean isSubscribed() {
        return mRedisListener.isSubscribed();
    }

    private RedisClient() {
        mRedisListener = new RedisListener(mCallback);
    }

    private void subscribeChannel(final JedisShardInfo jedisShardInfo) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    mJedis = new Jedis(jedisShardInfo);
                    mJedis.subscribe(mRedisListener, CHANNEL_KEY);
                } catch (Exception e) {
                    Log.w("error redis", e.getMessage());
                    if (mCallback != null) {
                        mCallback.errorSubscribing(e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }

    public interface RedisClientListener {
        void onMessageReceived(Object my_object);
        void onSubscribed();
        void onUnSubscribed();
        void errorSubscribing(String error);
    }
}
