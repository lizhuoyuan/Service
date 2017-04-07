package li.zhuoyuan.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "li.zhuoyuan.service.action.FOO";
    public static final String ACTION_BAZ = "li.zhuoyuan.service.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "li.zhuoyuan.service.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "li.zhuoyuan.service.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
        Log.e(TAG, "MyIntentService: ", null);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        Log.e(TAG, "handleActionBaz: " + param1, null);
        try {
            Thread.sleep(1000);
            Intent i = new Intent(MainActivity.TAG);
            i.putExtra("a", true);
            i.putExtra("b", "hhhh");
            sendBroadcast(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
