package cmps121.focus;

/**
 * Created by Alston-PC on 12/2/2017.
 */

public class Pokemon{
    int imageId;
    String pokemonName;


    public int getImageId() {
        return imageId;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    Pokemon(int imageId, String pokemonName){
        this.imageId = imageId;
        this.pokemonName = pokemonName;

    }
}
