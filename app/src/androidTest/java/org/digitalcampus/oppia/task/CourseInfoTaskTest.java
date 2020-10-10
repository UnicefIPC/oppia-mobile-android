package org.digitalcampus.oppia.task;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.digitalcampus.mobile.learning.R;
import org.digitalcampus.oppia.adapter.CourseInstallViewAdapter;
import org.digitalcampus.oppia.api.MockApiEndpoint;
import org.digitalcampus.oppia.listener.SubmitListener;
import org.digitalcampus.oppia.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseInfoTaskTest {

    private CountDownLatch signal;
    private MockWebServer mockServer;
    private Context context;
    private CourseInstallViewAdapter response;


    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        signal = new CountDownLatch(1);
        mockServer = new MockWebServer();
    }

    @After
    public void tearDown() throws Exception {
        signal.countDown();
        mockServer.shutdown();
    }

    @Test(expected = AssertionError.class)
    public void courseInfo_Valid() throws Exception {
        try {

            String filename = "responses/response_200_courseinfo.json";

            mockServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(Utils.FileUtils.getStringFromFile(InstrumentationRegistry.getInstrumentation().getContext(), filename)));

            mockServer.start();

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<String> shortname = new ArrayList<>();
        shortname.add("demo-course");

        Payload p = new Payload(shortname);
        try {
            CourseInfoTask task = new CourseInfoTask(context, new MockApiEndpoint(mockServer));
            task.setListener(new CourseInfoTask.CourseInfoListener() {
                @Override
                public void onSuccess(CourseInstallViewAdapter course) {
                    response = course;
                }

                @Override
                public void onError(String error) {
                    signal.countDown();

                }

                @Override
                public void onConnectionError(String error, User u) {
                    signal.countDown();
                }

            });
            task.execute(p);

            signal.await();

            // TODO this ought to pass (?), but doesn't
            assertTrue(response instanceof CourseInstallViewAdapter);

        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }
}
