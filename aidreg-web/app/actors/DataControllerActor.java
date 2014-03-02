package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.DataContext;
import play.libs.Akka;
import utils.DAO;

/**
 * Created by lars on 3/1/14.
 */
public class DataControllerActor extends UntypedActor {

    public static ActorRef instance = Akka.system().actorOf(Props.create(DataControllerActor.class));

    private DataContext data = null;

    public DataControllerActor() {
        this.reload();
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Msg.ReloadAndGetData) {
            reload();

            sendDataBackToSender((Msg.GetData) message);
        } else if (message instanceof Msg.GetData) {
            sendDataBackToSender((Msg.GetData) message);
        }
        else {
            unhandled(message);
        }

    }

    private void reload() {
        this.data = DAO.getContext();
    }

    private void sendDataBackToSender(final Msg.GetData msg) {
        msg.setDataContext(data);
        sender().tell(msg, self());
    }
}
