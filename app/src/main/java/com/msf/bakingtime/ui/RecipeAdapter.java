package com.msf.bakingtime.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipes;
    private OnRecipeListener mListener;

    RecipeAdapter(Context context, List<Recipe> setRecipe, OnRecipeListener listener){
        this.mContext = context;
        this.mRecipes = setRecipe;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = getRecipeByPosition(position);
        holder.textViewNome.setText(recipe.getName());
    }

    private Recipe getRecipeByPosition(int position) {
        return mRecipes.get(position);
    }


    public interface OnRecipeListener {
        void onItemClick(Recipe recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.id_nome)
        TextView textViewNome;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = getRecipeByPosition(getAdapterPosition());
            mListener.onItemClick(recipe);
        }
    }

}