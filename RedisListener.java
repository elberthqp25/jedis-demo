package com.my_package;

import android.util.Log;

import com.google.gson.Gson;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by equesada 
 */
public class RedisListener extends JedisPubSub {

    public static final String TAG = "RedisListener";

    private RedisClient.RedisClientListener mListener;

    public RedisListener(RedisClient.RedisClientListener listener) {
        super();
        mListener = listener;
    }

    public RedisListener() {
        super();
    }

    public void setListener(RedisClient.RedisClientListener listener) {
        mListener = listener;
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        Log.i(TAG, String.format("Message received: %s",message));
        if (mListener != null) {
            //parse json string into my custom object
            MyCustomObject myObject = new Gson().fromJson(message, MyCustomObject.class);
            mListener.onMessageReceived(myObject);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);
        Log.i(TAG, String.format("onPMessage received: %s", message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
        Log.i(TAG, String.format("Subscribed to channel %s", channel));
        if (mListener != null) {
            mListener.onSubscribed();
        }
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
        Log.i(TAG, String.format("UnSubscribed to channel %s", channel));
        if (mListener != null) {
            mListener.onUnSubscribed();
        }
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        super.onPUnsubscribe(pattern, subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        super.onPSubscribe(pattern, subscribedChannels);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    public void unsubscribe(String... channels) {
        super.unsubscribe(channels);
    }

    @Override
    public void subscribe(String... channels) {
        super.subscribe(channels);
    }

    @Override
    public void psubscribe(String... patterns) {
        super.psubscribe(patterns);
    }

    @Override
    public void punsubscribe() {
        super.punsubscribe();
    }

    @Override
    public void punsubscribe(String... patterns) {
        super.punsubscribe(patterns);
    }

    @Override
    public boolean isSubscribed() {
        return super.isSubscribed();
    }

    @Override
    public void proceedWithPatterns(Client client, String... patterns) {
        super.proceedWithPatterns(client, patterns);
    }

    @Override
    public void proceed(Client client, String... channels) {
        super.proceed(client, channels);
    }

    @Override
    public int getSubscribedChannels() {
        return super.getSubscribedChannels();
    }
}
