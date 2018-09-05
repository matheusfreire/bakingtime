package com.msf.bakingtime.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {
    private static final String FREE_SPACE = " ";
    private List<Ingredient> mListIngredients;
    private int mLayout;

    IngredientsAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        this.mListIngredients = objects;
        this.mLayout = resource;
    }

    @Override
    public int getCount() {
        return mListIngredients.size();
    }

    @Nullable
    @Override
    public Ingredient getItem(int position) {
        return mListIngredients.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredient ingredient = getItem(position);
        IngredientViewHolder ingredientViewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(mLayout, parent, false);
            ingredientViewHolder = new IngredientViewHolder(convertView);
            convertView.setTag(ingredientViewHolder);
        } else {
            ingredientViewHolder = (IngredientViewHolder) convertView.getTag();
        }
        ingredientViewHolder.mContent.setText(buildIngredientText(ingredient));
        return convertView;
    }

    private String buildIngredientText(Ingredient ingredient) {
        return new StringBuilder().append(ingredient.getQuantity()).append(FREE_SPACE)
                .append(ingredient.getMeasure()).append(FREE_SPACE)
                .append(ingredient.getIngredient()) .toString();
    }

    class IngredientViewHolder {
        @BindView(R.id.ingredient_txt)
        TextView mContent;

        IngredientViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
