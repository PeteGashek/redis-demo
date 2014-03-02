package utils;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by lars on 3/1/14.
 */
public class JedisListener extends JedisPubSub {
    @Override
    public void onMessage(String s, String s2) {

    }

    @Override
    public void onPMessage(String s, String s2, String s3) {

    }

    @Override
    public void onSubscribe(String s, int i) {

    }

    @Override
    public void onUnsubscribe(String s, int i) {

    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }
}
