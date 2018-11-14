package io.github.sudhansubarik.letsbake.masterdetailflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Objects;

import io.github.sudhansubarik.letsbake.R;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;
import io.github.sudhansubarik.letsbake.room.Step;
import io.github.sudhansubarik.letsbake.utils.Constant;
import io.github.sudhansubarik.letsbake.widget.DetailListFragment;
import timber.log.Timber;

public class StepListActivity extends AppCompatActivity implements DetailListFragment.OnDetailListListener {

    private RecipeResponse recipeResponse;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_list);

        isTwoPane = getResources().getBoolean(R.bool.isTablet);

        // getting value from recipe list
        extractDataFromBundle();
    }

    private void extractDataFromBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                // set values on text
                recipeResponse = data.getParcelable(Constant.EXTRA_KEY);
                if (recipeResponse != null) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(recipeResponse.getName());
                    setUpUIForDifferentScreenSize();
                }
            }
        }
    }

    private void setUpUIForDifferentScreenSize() {
        if (isTwoPane) {
            Timber.d("tablet screen");
            setStepListFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.step_detail_activity,
                    StepDetailFragment.newInstance(recipeResponse.getSteps().get(0))).commit();
        } else {
            setStepListFragment();
        }
    }

    // start stepListFragment in activity for phone screen
    private void setStepListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_list_activity,
                DetailListFragment.newInstance(recipeResponse), "list fragment")
                .commit();
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Step step) {
        if (isTwoPane) {
            Timber.d(step.getShortDescription());
            // instantly update step fragment with step, create new fragment and set correct values
            // replace existing fragment
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.step_detail_activity,
                    StepDetailFragment.newInstance(step)).commit();
        } else {
            Intent intent = new Intent(StepListActivity.this, StepDetailActivity.class);
            intent.putExtra(Constant.EXTRA_KEY, step);
            startActivity(intent);
        }
    }
}
