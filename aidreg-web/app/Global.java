import actors.JedisListenerActor;
import actors.Msg;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import utils.JedisListener;

/**
 * Created by lars on 3/1/14.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        super.onStart(application);

        ActorRef ref = Akka.system().actorOf(Props.create(JedisListenerActor.class));
        ref.tell(new Msg.StartListening(), ActorRef.noSender());
    }

    @Override
    public void onStop(Application application) {
        JedisListenerActor.subscriber.unsubscribe();

        super.onStop(application);
    }
}
