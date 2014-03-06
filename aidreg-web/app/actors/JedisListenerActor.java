package actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.DAO;

/**
 * Created by lars on 3/1/14.
 */
public class JedisListenerActor extends UntypedActor {

    public static JedisPubSub subscriber = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
            if ("updated".equals(message)) {
                EventHandlerActor.instance.tell(new Msg.DataReloaded(), ActorRef.noSender());
            }

            //Some
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
    };

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Msg.StartListening) {

            Jedis jedis = DAO.getJpool().getResource();

            jedis.subscribe(subscriber, "contributions-updated");

        }
    }
}
