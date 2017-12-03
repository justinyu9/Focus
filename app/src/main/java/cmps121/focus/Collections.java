package cmps121.focus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

import cmps121.focus.pokeapi.PokeapiService;
import cmps121.focus.pokeapi.PokemonAnswer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Collections extends AppCompatActivity {

//    GridView gridView;
    RecyclerView recyclerView;
    PokemonAdapter adapter;
    //public ArrayList<Pokemon> pokeList;

    public Collections() {
    }

    private Retrofit retrofit;

//    public void addPokemon(Pokemon pokemon){
//        pokeList.add(pokemon);
//        adapter.notifyDataSetChanged();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        obtainData();

//        Pokemon poke1 = new Pokemon(R.drawable.pokemon01, "Charmander");
//        Pokemon poke2 = new Pokemon(R.drawable.pokemon02, "Squirtle");
//        Pokemon poke3 = new Pokemon(R.drawable.pokemon03, "Bulbasaur");

//        pokeList = new ArrayList<Pokemon>();
//        pokeList.add(poke1);
//        pokeList.add(poke2);
//        pokeList.add(poke2);
//        pokeList.add(poke3);


//        gridView = (GridView) findViewById(R.id.pokemonGrid);
//        adapter = new PokemonAdapter(this);
//        gridView.setAdapter(adapter);
    }

    public void obtainData(){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonAnswer> pokemonRequestCall = service.getListPokemon();
        pokemonRequestCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                if(response.isSuccessful()){
                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokeList = pokemonAnswer.getResults();
                    adapter.addListOfPokemons(pokeList);
                }
                else
                    Log.e("Pokedex", "onResponse: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                Log.e("Pokedex", "onFailure: " + t.getMessage());
            }
        });
    }
}