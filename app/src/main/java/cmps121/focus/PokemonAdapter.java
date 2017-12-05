package cmps121.focus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Alston-PC on 12/2/2017.
 */

//public class PokemonAdapter extends BaseAdapter {

//    ArrayList<Pokemon> pokeList;
//    Context context;
//
//    PokemonAdapter(Context context){
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return pokeList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return pokeList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    private class ViewHolder{
//        ImageView imageView;
//        TextView pokeName;
//    }
//
//    public void addListOfPokemons(ArrayList<Pokemon> pokeList){
//        pokeList.addAll(pokeList);
//        notifyDataSetChanged();
//    }
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        View row = view;
//        ViewHolder holder = new ViewHolder();
//        if(row == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = inflater.inflate(R.layout.pokemon_item, viewGroup, false);
//            holder.pokeName = (TextView) row.findViewById(R.id.pokeName);
//            holder.imageView = (ImageView) row.findViewById(R.id.imgPokemon);
//            row.setTag(holder);
//        }
//        else{
//            holder = (ViewHolder) row.getTag();
//        }
//
//        Pokemon pokemon = pokeList.get(position);
//        holder.pokeName.setText(pokemon.getName());
//        holder.imageView.setImageResource(R.drawable.pokemon01);
//        return row;
//    }

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder>{
    private ArrayList<Pokemon> pokeList;

    public PokemonAdapter(Context context){
        this.context = context;
        pokeList = new ArrayList<>();
    }
    private Context context;

    public void addListOfPokemons(ArrayList<Pokemon> pokeList){
        this.pokeList.addAll(pokeList);
        notifyDataSetChanged();
    }

    public void addPokemonToGrid(Pokemon p){
        this.pokeList.add(p);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = pokeList.get(position);
        Log.i("POKENUMBER", String.valueOf(p.getNumber()));
        holder.numberTextView.setText(p.getName());
        Glide.with(context).load("http://pokeapi.co/media/sprites/pokemon/" + p.getID() + ".png").centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView photoImageView;
        private TextView numberTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.imgPokemon);
            numberTextView = itemView.findViewById(R.id.pokeName);
        }
    }
}
