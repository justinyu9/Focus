package cmps121.focus.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alston-PC on 12/2/2017.
 */

public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonAnswer> getListPokemon();
}
