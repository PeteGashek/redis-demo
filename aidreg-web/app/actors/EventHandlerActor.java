package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.DataContext;
import play.Logger;
import play.libs.Akka;
import play.libs.EventSource;
import play.libs.F;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lars on 2/28/14.
 */
public class EventHandlerActor extends UntypedActor {

    public static ActorRef instance = Akka.system().actorOf(Props.create(EventHandlerActor.class));

    Set<EventSource> sockets = new HashSet<>();

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof EventSource) {
            final EventSource e = (EventSource) message;
            regOrUnregEventSource(e);

            Msg.GetData getDataMsg = new Msg.GetData();
            getDataMsg.setEventSource(e);
            DataControllerActor.instance.tell(getDataMsg, self());

        } else if (message instanceof Msg.GetData) {
            Msg.GetData getDataMsg = (Msg.GetData) message;

            if (getDataMsg.getEventSource() == null) {
                sendDataToClients(getDataMsg.getDataContext());
            } else {
                sendDataToClient(getDataMsg.getDataContext(), getDataMsg.getEventSource());
            }

        } else if (message instanceof Msg.DataReloaded) {
            DataControllerActor.instance.tell(new Msg.ReloadAndGetData(), self());
        } else {
            unhandled(message);
        }
    }

    private void regOrUnregEventSource(final EventSource eventSource) {
        if (sockets.contains(eventSource)) {
            // Browser is disconnected
            sockets.remove(eventSource);
            Logger.info("Browser is disconnected ({} browsers currently connected)", sockets.size());
        } else {
            // Browser is connected
            sockets.add(eventSource);
            Logger.info("Browser is connected ({} browsers currently connected)", sockets.size());

            // register disconnect callback
            eventSource.onDisconnected(new F.Callback0() {
                @Override
                public void invoke() throws Throwable {
                    getContext().self().tell(eventSource, getContext().self());
                }
            });
        }
    }

    ObjectMapper mapper = new ObjectMapper();
    private void sendDataToClients(final DataContext data) throws JsonProcessingException {

        String jsonStr = mapper.writeValueAsString(data);
        for (EventSource e : sockets) {
            e.sendData(jsonStr);
        }
    }

    private void sendDataToClient(final DataContext data, EventSource eventSource) throws JsonProcessingException, UnsupportedEncodingException {
        String jsonStr = mapper.writeValueAsString(data);
        jsonStr.replace(' ', ' ');
        eventSource.sendData(new String(jsonStr.getBytes(), "UTF8"));
    }
}
