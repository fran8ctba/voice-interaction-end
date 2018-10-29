package com.example.android.voicecamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TakePictureActivity extends Activity {
    private static String TAG = "TakePictureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else if (CameraActivity.needPermissions(this)) {
            startActivity(new Intent(this, CameraActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return;
        } else if (!isVoiceInteraction()) {
            Log.e(TAG, "Not voice interaction");
            if (intent != null) {
                intent.setComponent(null);
                intent.setPackage("com.google.android.GoogleCamera");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            finish();
            return;
        }

        setContentView(R.layout.activity_camera);
        CameraFragment fragment = CameraFragment.newInstance();
        fragment.setArguments(getIntent().getExtras());

        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}