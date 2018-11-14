package io.github.sudhansubarik.letsbake.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import io.github.sudhansubarik.letsbake.R;
import io.github.sudhansubarik.letsbake.adapters.RecipeListAdapter;
import io.github.sudhansubarik.letsbake.masterdetailflow.StepListActivity;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;
import io.github.sudhansubarik.letsbake.utils.Constant;

public class MainActivity extends AppCompatActivity {
    private RecipeListAdapter adapter;
    private boolean tabletSize;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
        initRecyclerView();
        viewModelSetUp();
    }

    private void initRecyclerView() {
        progressBar = findViewById(R.id.menu_list_progress_bar);
        RecyclerView recipeListView = findViewById(R.id.menu_list);
        adapter = new RecipeListAdapter(this, new RecipeListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(RecipeResponse recipeResponse) {
                startDetailActivity(recipeResponse);
            }
        });
        recipeListView.setAdapter(adapter);

        if (tabletSize) {
            GridLayoutManager manager = new GridLayoutManager(this, 2,
                    GridLayoutManager.VERTICAL, false);
            recipeListView.setLayoutManager(manager);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recipeListView.setLayoutManager(manager);
        }
    }

    private void startDetailActivity(RecipeResponse recipeResponse) {
        Intent intent = new Intent(MainActivity.this, StepListActivity.class);
        intent.putExtra(Constant.EXTRA_KEY, recipeResponse);
        startActivity(intent);
    }

    private void viewModelSetUp() {
        MainActivityViewModel viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getRecipeLiveData().observe(this, new Observer<List<RecipeResponse>>() {
            @Override
            public void onChanged(@Nullable List<RecipeResponse> recipeResponses) {
                if (recipeResponses != null) {
                    adapter.setList(recipeResponses);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
