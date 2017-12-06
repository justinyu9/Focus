package cmps121.focus;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
import cmps121.focus.pokeapi.PokemonDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Collections extends AppCompatActivity implements PokemonInterface{


//    GridView gridView;
    RecyclerView recyclerView;
    PokemonAdapter adapter;
    PokemonDatabase pokemonDatabase;
    Context context;
    public ArrayList<Pokemon> pokeList;
    Pokemon p;
    String url = "http://pokeapi.co/api/v2/pokemon/";
    public void notificationCaller() {
        Intent intent = new Intent(this, Start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        String channel00= "channel00";
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channel00, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        nManager.createNotificationChannel(channel);

        android.support.v4.app.NotificationCompat.Builder NB =new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Your egg is dying!")
                .setContentText("Click here to save it!")
                .setChannelId(channel00)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentIntent(pi);

        nManager.notify(1, NB.build());
//        mNH = new NotficationHelper(this);
//        android.support.v4.app.NotificationCompat.Builder nb = mNH.getChannelNotification("Your egg is dying!", "Click here to save it");
//        mNH.getNotificationManager().notify(1, nb.build());
    }
    public Collections() {
    }

    private Retrofit retrofit;

    public Pokemon getPokemon(String id) throws ExecutionException, InterruptedException {
//        url += id;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
        fetchPokemon fetch = new fetchPokemon();
        fetch.delegate=this;
        p = fetch.execute(id).get();
        return p;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "false");
        e2.apply();
        
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        pokeList = new ArrayList<Pokemon>();
        pokemonDatabase = new PokemonDatabase(this);
        Cursor res = pokemonDatabase.getAllData();
        if(res.getCount() == 0){
            Log.e("POKEMON", "Database is empty");
        }
        while(res.moveToNext()){
            Pokemon pok = new Pokemon();
            pok.setName(res.getString(0));
            pok.setNumber(res.getString(1));
            pokeList.add(pok);
        }
        adapter.addListOfPokemons(pokeList);
        //obtainData();
//        fetchPokemon fetchPokemon = new fetchPokemon();
//        fetchPokemon.delegate = this;
//        try {
//            p = fetchPokemon.execute("141").get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        if(p!=null){
//            Log.i("POKEMON", "Successful");
//            adapter.addPokemonToGrid(p);
//        }

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
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
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
    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "true");
        e2.apply();
        notificationCaller();
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "true");
        e2.apply();
        notificationCaller();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "false");
        e2.apply();

    }
}