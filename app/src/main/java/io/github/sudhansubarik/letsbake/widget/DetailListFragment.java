package io.github.sudhansubarik.letsbake.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sudhansubarik.letsbake.FoodRepository;
import io.github.sudhansubarik.letsbake.R;
import io.github.sudhansubarik.letsbake.adapters.RecipeStepAdapter;
import io.github.sudhansubarik.letsbake.room.Ingredient;
import io.github.sudhansubarik.letsbake.room.RecipeResponse;
import io.github.sudhansubarik.letsbake.room.Step;
import io.github.sudhansubarik.letsbake.utils.Constant;
import io.github.sudhansubarik.letsbake.utils.InjectorUtil;
import io.github.sudhansubarik.letsbake.utils.StringUtils;

public class DetailListFragment extends Fragment {
    private RecipeResponse recipeResponse;
    @BindView(R.id.recipe_steps_recyclerView)
    RecyclerView stepListView;
    @BindView(R.id.recipe_ingredients_textView)
    TextView ingredientsView;
    private Context context;

    private OnDetailListListener mListener;

    public DetailListFragment() {
        // Required empty public constructor
    }

    public static DetailListFragment newInstance(RecipeResponse recipeResponse) {
        DetailListFragment fragment = new DetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_KEY, recipeResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeResponse = getArguments().getParcelable(Constant.EXTRA_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_step_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        context = getContext();
        // set ingredients
        createAndSetIngredientList(recipeResponse.getIngredients());
        // set recycler view for steps
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecipeStepAdapter adapter = new RecipeStepAdapter(context, new RecipeStepAdapter.StepClickListener() {
            @Override
            public void onItemClick(Step step) {
                mListener.onFragmentInteraction(step);
            }
        });
        stepListView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        stepListView.setLayoutManager(manager);
        stepListView.addItemDecoration(new DividerItemDecoration(context, manager.getOrientation()));
        adapter.setList(recipeResponse.getSteps());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailListListener) {
            mListener = (OnDetailListListener) context;
        } else {
            throw new RuntimeException(context.toString() + getString(R.string.implement_OnDetailListener_exception));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDetailListListener {
        void onFragmentInteraction(Step step);
    }

    private void createAndSetIngredientList(List<Ingredient> ingredients) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Ingredient ing : ingredients) {
            stringBuilder.append("\n");
            stringBuilder.append(StringUtils.formatIngredient(context, ing.getIngredient(), ing.getQuantity(), ing.getMeasure()));
        }
        ingredientsView.setText(stringBuilder.toString());
        updateWidgetMethod(stringBuilder.toString());
    }

    private void updateWidgetMethod(String stringBuilder) {
        FoodRepository repository = InjectorUtil.provideRepository(context);
        repository.setCurrentRecipeIngredient(stringBuilder);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        if (getActivity() != null) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.ingredients_widget);
            ComponentName componentName = new ComponentName(getActivity(), IngredientsWidgetProvider.class);
            remoteViews.setTextViewText(R.id.ingredient_widget_textView, stringBuilder);
            manager.updateAppWidget(componentName, remoteViews);
        }
    }
}
