package baking.amr.www.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amr on 11/15/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    Context mContext;

    public RecipeAdapter(ArrayList<Recipe> recipes,Context context)
    {
        this.recipes=recipes;
        mContext=context;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recipe_item,parent,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if(recipes==null) return 0;
        else return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder


    {
        TextView recipeName;
        TextView recipeServings;
        ImageView recipeImage;
        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName= itemView.findViewById(R.id.recipeNameTextView);
            recipeServings=itemView.findViewById(R.id.recipeServingsTextView);
            recipeImage=itemView.findViewById(R.id.recipeIconImageView);

        }

        public void bind(int position){
            String name=recipes.get(position).getName();
            int servings=recipes.get(position).getServings();
            String imageUrl=recipes.get(position).getImage();
            recipeName.setText(name);
            recipeServings.setText("Servings: "+String.valueOf(servings));
            if(imageUrl!=""){
                Uri uri=Uri.parse(imageUrl).buildUpon().build();
                Picasso.with(mContext).load(uri).into(recipeImage);

            }









        }
    }
}