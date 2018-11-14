package io.github.sudhansubarik.letsbake.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.sudhansubarik.letsbake.R;
import io.github.sudhansubarik.letsbake.room.Step;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.MyViewHolder> {
    private List<Step> stepList;
    private Context context;
    private StepClickListener listener;

    public RecipeStepAdapter(Context context, StepClickListener listener) {
        this.context = context;
        this.listener = listener;
        stepList = new ArrayList<>();
    }

    public void setList(List<Step> steps) {
        stepList = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_step_list, viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.stepTextView.setText(stepList.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepList.size() > 0)
            return stepList.size();
        else return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTextView = itemView.findViewById(R.id.recipe_step_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(stepList.get(position));
        }
    }

    public interface StepClickListener {
        void onItemClick(Step step);
    }
}
