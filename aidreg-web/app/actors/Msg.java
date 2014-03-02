package actors;

import models.DataContext;
import play.libs.EventSource;

/**
 * Created by lars on 3/1/14.
 */
public class Msg {

    public static class GetData {
        private EventSource eventSource;
        private DataContext dataContext;

        public EventSource getEventSource() {
            return eventSource;
        }

        public void setEventSource(EventSource eventSource) {
            this.eventSource = eventSource;
        }

        public DataContext getDataContext() {
            return dataContext;
        }

        public void setDataContext(DataContext dataContext) {
            this.dataContext = dataContext;
        }
    }
    public static class DataReloaded {}
    public static class ReloadAndGetData extends GetData{}

    public static class StartListening{}


}
