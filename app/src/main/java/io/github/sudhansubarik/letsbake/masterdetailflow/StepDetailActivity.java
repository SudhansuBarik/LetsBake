package io.github.sudhansubarik.letsbake.masterdetailflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.sudhansubarik.letsbake.R;
import io.github.sudhansubarik.letsbake.room.Step;
import io.github.sudhansubarik.letsbake.utils.Constant;
import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);

        if (savedInstanceState == null) {
            extractDataFromBundle();
        }
    }

    private void extractDataFromBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                // set values on text and images
                Step step = data.getParcelable(Constant.EXTRA_KEY);
                if (step != null) {
                    Timber.d(step.getShortDescription());
                    initFragment(step);
                }
            }
        }
    }

    private void initFragment(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_detail_activity,
                StepDetailFragment.newInstance(step)).commit();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
