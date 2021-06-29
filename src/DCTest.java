import chat.delta.java.*;

public class DCTest {
    static {
        System.loadLibrary("deltajni");
    }

    static String eventId2Name(final int eventId) {
        switch (eventId) {
        case DcContext.DC_EVENT_INFO:
            return "INFO";
        case DcContext.DC_EVENT_WARNING:
            return "WARNING";
        case DcContext.DC_EVENT_ERROR:
            return "ERROR";
        case DcContext.DC_EVENT_ERROR_NETWORK:
            return "ERROR_NETWORK";
        case DcContext.DC_EVENT_ERROR_SELF_NOT_IN_GROUP:
            return "ERROR_SELF_NOT_IN_GROUP";
        case DcContext.DC_EVENT_MSGS_CHANGED:
            return "MSGS_CHANGED";
        case DcContext.DC_EVENT_INCOMING_MSG:
            return "INCOMING_MSG";
        case DcContext.DC_EVENT_MSGS_NOTICED:
            return "MSGS_NOTICED";
        case DcContext.DC_EVENT_MSG_DELIVERED:
            return "MSG_DELIVERED";
        case DcContext.DC_EVENT_MSG_FAILED:
            return "MSG_FAILED";
        case DcContext.DC_EVENT_MSG_READ:
            return "MSG_READ";
        case DcContext.DC_EVENT_CHAT_MODIFIED:
            return "CHAT_MODIFIED";
        case DcContext.DC_EVENT_CHAT_EPHEMERAL_TIMER_MODIFIED:
            return "CHAT_EPHEMERAL_TIMER_MODIFIE";
        case DcContext.DC_EVENT_CONTACTS_CHANGED:
            return "CONTACTS_CHANGED";
        case DcContext.DC_EVENT_LOCATION_CHANGED:
            return "LOCATION_CHANGED";
        case DcContext.DC_EVENT_CONFIGURE_PROGRESS:
            return "CONFIGURE_PROGRESS";
        case DcContext.DC_EVENT_IMEX_PROGRESS:
            return "IMEX_PROGRESS";
        case DcContext.DC_EVENT_IMEX_FILE_WRITTEN:
            return "IMEX_FILE_WRITTEN";
        case DcContext.DC_EVENT_SECUREJOIN_INVITER_PROGRESS:
            return "SECUREJOIN_INVITER_PROGRES";
        case DcContext.DC_EVENT_SECUREJOIN_JOINER_PROGRESS:
            return "SECUREJOIN_JOINER_PROGRESS";
        default:
            return "Unkown" + eventId;
        }

    }

    static String event2string(final DcEvent event) {
        final String eventName = "[" + eventId2Name(event.getId()) + "]";

        switch (event.getId()) {
        case DcContext.DC_EVENT_INFO:
        case DcContext.DC_EVENT_WARNING:
        case DcContext.DC_EVENT_ERROR:
        case DcContext.DC_EVENT_ERROR_NETWORK:
            return eventName + " " + event.getData2Str();

        default:
            return eventName + " (data to string is unimplemented for this event";
        }

    }

    static void handleMessage(DcContext context, int chat_id, int message_id) {
        DcChat chat = context.getChat(chat_id);
        DcMsg msg = context.getMsg(message_id);

        // only echo to DM
        if (chat.getType() == DcChat.DC_CHAT_TYPE_SINGLE) {
            DcMsg new_msg = new DcMsg(context.createMsgCPtr(DcMsg.DC_MSG_TEXT));
            new_msg.setText(msg.getText());
            context.sendMsg(chat_id, new_msg);
        }
    }

    static void handleEvent(DcContext context, final DcEvent event) {
        switch (event.getId()) {
        case DcContext.DC_EVENT_MSGS_CHANGED:
            int message_id = event.getData2Int();
            DcMsg msg = context.getMsg(message_id);
            if (msg.getChatId() == DcChat.DC_CHAT_ID_DEADDROP) {
                // check if contact is new / and accept its contact request
                System.out.println("[BOT] accepting new contact\n");
                int new_chat_id = context.decideOnContactRequest(message_id, DcContext.DC_DECISION_START_CHAT);
                handleMessage(context, new_chat_id, message_id);
            }
            break;
        case DcContext.DC_EVENT_INCOMING_MSG:
            handleMessage(context, event.getData1Int(), event.getData2Int());
            break;
        default:
            System.out.println(event2string(event));
            break;
        }
    }

    public static void main(final String args[]) {
        final DcContext my_context = new DcContext("", "/tmp/mydb.db");

        if (my_context.isConfigured() != 1) {
            my_context.setConfig("addr", "");
            my_context.setConfig("mail_pw", "");
            my_context.setConfig("bot", "true");
            my_context.setConfig("e2ee_enabled", "true");

            my_context.configure(); //do we need to somehow wait for this?
        }

        my_context.startIo();

        new Thread(() -> {
            final DcEventEmitter emitter = my_context.getEventEmitter();
            while (true) {
                final DcEvent event = emitter.getNextEvent();
                if (event == null) {
                    break;
                }
                handleEvent(my_context, event);
            }
            System.out.println("shutting down event handler");
        }, "eventThread").start();

        // new Thread(() -> {
        //     try {
        //         Thread.sleep(2000);
        //     } catch (final Exception e) {
        //         System.out.println(e);
        //     }
        //     my_context.stopIo();
        //     my_context.stopOngoingProcess();
        //     my_context.unref();
        // }, "shutdown").start();
    }
}
