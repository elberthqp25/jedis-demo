# Redis client for Android

Redis is an open source (BSD licensed), in-memory data structure store, used as database, cache and message broker. It supports data structures such as strings, hashes, lists, sets, sorted sets with range queries, bitmaps, hyperloglogs and geospatial indexes with radius queries. Redis has built-in replication, Lua scripting, LRU eviction, transactions and different levels of on-disk persistence, and provides high availability via Redis Sentinel and automatic partitioning with Redis Cluster.
Visite the oficial site of [redis] for more info.

Redis has many java libraries to be used as clients. Go to this [link] to see complete list. In this example we are using [jedis] as java library.

We are going to configure the redis client to subscribe to a channel and listen for messages.

### Installation
Jedis can't de compiled throght gradle, so we need to download and import manually the jedis.jar in our android project.

### Configuration

Create a JedisShardInfo object to store the url, port and password of the redis server. 
```sh
JedisShardInfo jedisShardInfo = new JedisShardInfo(MY_REDIS_URL, MY_REDIS_PORT);
jedisShardInfo.setPassword(MY_REDIS_PASSWORD);
```
Create a Jedis object passing as parameter the JedisShardInfo object.

```sh
Jedis jedis = new Jedis(jedisShardInfo);
```
We need a JedisPubSub object to be used as listener of our channel.
```sh
JedisPubSub JedisPubSub = new JedisPubSub() {
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        //messages will be received here
    }
};
```
And finally we just need to call the subscribe method of our Jedis object and send the listener and the channel key as parameters. Avoid do this in the main thread.
```sh
jedis.subscribe(jedisPubSub, "my_channel_key");
```


License
----


**Free Software, Hell Yeah!**

   [redis]: <http://gulpjs.com>
   [link]: <http://redis.io/clients#java> 
   [jedis]: https://github.com/xetorthio/jedis

