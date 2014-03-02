package controllers;

import actors.EventHandlerActor;
import akka.actor.ActorRef;
import models.DataContext;
import play.libs.EventSource;
import play.mvc.*;

import utils.DAO;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result liveData() {
        return ok(new EventSource() {
            @Override
            public void onConnected() {
                EventHandlerActor.instance.tell(this, ActorRef.noSender());
            }
        });
    }

    public static Result data() {
        DataContext data = DAO.getContext();

        return ok(DAO.getContextAsJson());
    }
}
