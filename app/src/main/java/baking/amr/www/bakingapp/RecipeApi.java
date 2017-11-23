

/**
 * Created by amr on 11/15/17.
 */

package baking.amr.www.bakingapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();

}