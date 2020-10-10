package org.digitalcampus.oppia.activity;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.digitalcampus.oppia.utils.mediaplayer.VideoPlayerActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VideoPlayerActivityTest {
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    }

    @Rule
    public ActivityTestRule<VideoPlayerActivity> videoPlayerActivityTestRule =
            new ActivityTestRule<>(VideoPlayerActivity.class, false, false);

    @Test
    public void testActivityOpenNoBundle() {
        videoPlayerActivityTestRule.launchActivity(null);
    }
}
