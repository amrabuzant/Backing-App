package baking.amr.www.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amr on 11/15/17.
 */

public class RecipeDetailFragment extends Fragment {
    private TextView ingredientsText;
    private RecyclerView stepRecyclerView;
    private LinearLayoutManager layoutManager;
    private StepAdapter stepAdapter;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String recipeName;
    OnStepClickListener listener;

    public RecipeDetailFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            listener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ingredientsText=(TextView)rootView.findViewById(R.id.ingredientsTextView);
        stepRecyclerView=(RecyclerView)rootView.findViewById(R.id.stepsRecylerView);
        if (savedInstanceState!=null){
            ingredients=savedInstanceState.getParcelableArrayList("ingredientsTextView");
            steps=savedInstanceState.getParcelableArrayList("steps");

        }

        ingredientsText.append("Ingredients list: \n\n");
        for (int i=0;i<ingredients.size();i++){
            ingredientsText.append((i+1)+":   "+ingredients.get(i).getIngredient()+
                    "("+print(ingredients.get(i).getQuantity())+"   "+
                    ingredients.get(i).getMeasure()+")\n");

        }
        layoutManager=new LinearLayoutManager(getContext());
        stepAdapter=new StepAdapter(getContext(),steps);
        stepRecyclerView.setLayoutManager(layoutManager);
        stepRecyclerView.setAdapter(stepAdapter);
        itemclick.addTo(stepRecyclerView).setOnItemClickListener(new itemclick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                listener.onStepSelected(position,steps);
            }
        });




        return rootView;


    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }



    public static String print(double x){
        int y=(int)x;
        return (y==x)? String.valueOf(y):String.valueOf(x);
    }

    public interface OnStepClickListener {
        void onStepSelected(int position,ArrayList<Step> steps);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ingredientsTextView",ingredients);
        outState.putParcelableArrayList("steps",steps);
    }
}
