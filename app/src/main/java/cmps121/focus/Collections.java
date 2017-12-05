package cmps121.focus;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cmps121.focus.pokeapi.PokeapiService;
import cmps121.focus.pokeapi.PokemonAnswer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Collections extends AppCompatActivity implements PokemonInterface{

//    GridView gridView;
    RecyclerView recyclerView;
    PokemonAdapter adapter;
    Pokemon p;
    //public ArrayList<Pokemon> pokeList;

    public Collections() {
    }

    private Retrofit retrofit;


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
        fetchPokemon fetch = new fetchPokemon();
        fetch.delegate=this;
        try {
            fetch.execute("292").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        Log.i("POKEMON ON COLLECTIONS.JAVA", "Pokemon name: " + p.getName() + " Pokemon id: " + p.getID());
        if(p == null){
            Log.i("POKEMON", "Pokemon is null");
        }
        else{
            Log.i("POKEMON", "Pokemon name: " + p.getName() + " Pokemon id: " + p.getID());
        }

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
//                    for(int i=0; i<pokemonAnswer.getResults().size();i++){
//                        Log.i("POKEDEX", "pokemon name: " + pokeList.get(i));
//                    }
//                    if(pokemonAnswer.getResults() == null){
//                        Log.i("POKEDEX", "pokeList is fucking NULL");
//                    }
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

    @Override
    public void onResponseReceived(Pokemon result) {
        p = result;
    }

    public class fetchPokemon extends AsyncTask<String, Void, Pokemon> {
        String pokemonId;
        String data;

        public PokemonInterface delegate = null;

        @Override
        protected Pokemon doInBackground(String... voids) {
            pokemonId = voids[0];
            Log.i("POKEMON", "doInBackground at fetchPokemon");
            Pokemon p = new Pokemon();
            try {
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + voids[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line!=null){
                    line = bufferedReader.readLine();
                    data=data + line;
                }
                data = data.substring(4, data.length() -4);
                JSONObject JO = new JSONObject(data);
                p.setName((String)JO.get("name"));
                p.setNumber(String.valueOf(JO.get("id")));
                Log.i("POKEMON", "Pokemon name: " + JO.get("name") + " abilities: " + JO.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").get("name"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return p;
        }

        @Override
        protected void onPostExecute(Pokemon p) {
            delegate.onResponseReceived(p);
        }

    }
}