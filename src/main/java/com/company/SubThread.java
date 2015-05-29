package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by shihj on 5/14/15.
 */
public class SubThread extends Thread{
    public interface ICallback {
        void   messageCome(String message,String channel);
    }

    private volatile boolean kills;
    private Jedis jedis;
    private ICallback icallback;

    private Long caltime=-1l;
    private int num=0;


    public final static String connectChannel="sensorDB";

    SubThread()
    {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.200.46.245", 6379);
        jedis = pool.getResource();
    }



    public void kill(){
        kills=true;
    }

    public void run() {
            try {

                    jedis.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            super.onMessage(channel, message);


                            if (caltime <0) {
                                caltime=0l;
                                caltime = System.currentTimeMillis();
                            }
                            if (num >= 10004) {
                                caltime = System.currentTimeMillis() - caltime;
                                System.err.print("Time eclipse is about(10000) :\n" + caltime + "\n");
                                caltime = 0l;
                                num =0;
                            }
                            num++;
                            //icallback.messageCome(message, channel);
                        }
                    }, connectChannel);


            } catch (Exception e) {
                System.err.print(e.toString());
            }
    }
}
