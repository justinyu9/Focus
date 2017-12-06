package cmps121.focus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.util.ArrayList;
import java.util.Collection;

import cmps121.focus.pokeapi.PokemonDatabase;

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

    public void deletePokemon(Pokemon p){
        for(int i=0; i<pokeList.size(); i++){
            Pokemon delete = pokeList.get(i);
            if((delete.getName()).equals(p.getName())){
                pokeList.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        //View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = pokeList.get(position);
        Log.i("POKENUMBER", String.valueOf(p.getID()));
        holder.numberTextView.setText(p.getName());
        holder.bind(p, pokeList);
        Glide.with(context).load("http://pokeapi.co/media/sprites/pokemon/" + p.getID() + ".png").centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.photoImageView);
    }


    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Pokemon p;
        private ArrayList<Pokemon> pokeList;
        private ImageView photoImageView;
        private TextView numberTextView;

        public void bind(Pokemon p, ArrayList<Pokemon> pokeList){
            this.p = p;
            this.pokeList = pokeList;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = itemView.findViewById(R.id.imgPokemon);
            numberTextView = itemView.findViewById(R.id.pokeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater factory = LayoutInflater.from(context);
                    View pokeView = factory.inflate(R.layout.pokemon_info, null);
                    ImageView pokeImageView = pokeView.findViewById(R.id.pokeImageView);
                    Button releasePokemon = pokeView.findViewById(R.id.releasePokemon);
                    TextView hp = pokeView.findViewById(R.id.hp);
                    TextView attack = pokeView.findViewById(R.id.attack);
                    TextView height = pokeView.findViewById(R.id.height);
                    TextView weight = pokeView.findViewById(R.id.weight);
                    TextView defense = pokeView.findViewById(R.id.defense);
                    TextView pokeInfoName = pokeView.findViewById(R.id.pokeInfoName);

                    releasePokemon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(context,Collections.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            context.startActivity(intent);
                            deletePokemon(p);
//                            PokemonDatabase db = new PokemonDatabase(context);
//                            db.deleteData(p.getName());
                        }
                    });

                    pokeImageView.setImageDrawable(photoImageView.getDrawable());
                    attack.setText("Attack: " + p.getAttack());
                    pokeInfoName.setText(p.getName());
                    pokeInfoName.setAllCaps(true);
                    hp.setText("HP: " + p.getHp());
                    height.setText("Height: " + p.getHeight());
                    weight.setText("Weight: " + p.getWeight());
                    defense.setText("Defense: " + p.getDefense());
                    new MaterialDialog.Builder(context)
                            .customView(pokeView, true)
                            .show();
//                    new LovelyCustomDialog(context)
//                            //.setView(R.layout.pokemon_info)
//                            .setTopColor(R.drawable.pokemon_gradient)
//                            .setIcon(photoImageView.getDrawable())
//                            .setTitle(p.getName())
//                            .setMessage("ID: " + p.getID() + "\n" + p.getID())
//                            .show();
                }
            });
        }
    }
}
