package cmps121.focus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alston-PC on 12/2/2017.
 */

public class PokemonAdapter extends BaseAdapter {

    ArrayList<Pokemon> pokeList;
    Context context;

    PokemonAdapter(Context context, ArrayList<Pokemon> pokeList){
        this.pokeList = pokeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pokeList.size();
    }

    @Override
    public Object getItem(int position) {
        return pokeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView pokeName;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.pokemon_item, viewGroup, false);
            holder.pokeName = (TextView) row.findViewById(R.id.pokeName);
            holder.imageView = (ImageView) row.findViewById(R.id.imgPokemon);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Pokemon pokemon = pokeList.get(position);
        holder.pokeName.setText(pokemon.getPokemonName());
        holder.imageView.setImageResource(pokemon.imageId);
        return row;
    }
}
