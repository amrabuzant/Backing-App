package baking.amr.www.bakingapp;

/**
 * Created by amr on 11/21/17.
 */

import android.app.Activity;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UpdateService extends IntentService {
    public final static String UPDATE_THE_WIDGET = "update";
    private static Bundle bundle;
    private static ArrayList<Recipe> recipes;
    private static final String TAG = UpdateService.class.getSimpleName();
    static int position;
    static String  recipeIngredient;


    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

            if (UPDATE_THE_WIDGET.equals(intent.getAction())) {
                SharedPreferences sp = getSharedPreferences("myPref", Activity.MODE_PRIVATE);
                position = sp.getInt("desiredPos", 0);
                bundle=new Bundle();
                bundle.putInt("pos",position);

                fetchRecipe(position);
                updateTheWiget();




            }

        }


    }

    public static void startUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(UPDATE_THE_WIDGET);

        context.startService(intent);



    }
    public static void fetchRecipe( final int position){
        RecipeApi recipeApi= RetrofitBuiler.retriveRecipeApi();
        final Call<ArrayList<Recipe>> recipe = recipeApi.getRecipe();


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipes=response.body();
                //bundle.putParcelableArrayList("recipes",recipes);




                recipeIngredient="";

                ArrayList<Ingredient> ingredients = recipes.get(position).getIngredients();
                for (int i=0;i<ingredients.size();i++){
                    recipeIngredient+="Ingredients list: \n\n"+(i+1)+":   "+ingredients.get(i).getIngredient()+
                            "("+ RecipeDetailFragment.print(ingredients.get(i).getQuantity())+"   "+
                            ingredients.get(i).getMeasure()+")\n";
                }





            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG,t.getMessage());

            }
        });



    }


    public void updateTheWiget() {


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, widgetProvider.class));
        widgetProvider.updatePlantWidgets(this,appWidgetManager,recipeIngredient,appWidgetIds,null);

    }


}