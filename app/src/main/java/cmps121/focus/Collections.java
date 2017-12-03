package cmps121.focus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class Collections extends AppCompatActivity {

    public ArrayList<Pokemon> pokeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        Pokemon poke1 = new Pokemon(R.drawable.pokemon01, "Charmander");
        Pokemon poke2 = new Pokemon(R.drawable.pokemon02, "Squirtle");
        Pokemon poke3 = new Pokemon(R.drawable.pokemon03, "Bulbasaur");

        pokeList = new ArrayList<Pokemon>();
        pokeList.add(poke1);
        pokeList.add(poke2);
        pokeList.add(poke2);
        pokeList.add(poke3);

        GridView gridView = (GridView) findViewById(R.id.pokemonGrid);
        PokemonAdapter adapter = new PokemonAdapter(this, pokeList);
        gridView.setAdapter(adapter);
    }
}