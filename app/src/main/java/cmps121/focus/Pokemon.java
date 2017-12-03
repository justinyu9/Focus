package cmps121.focus;

/**
 * Created by Alston-PC on 12/2/2017.
 */

public class Pokemon{
    int number;
    String name;
    String url;

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public String getName() {
        return name;
    }

    public void setnumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

//    Pokemon(int number, String pokemonName){
//        this.number = number;
//        this.pokemonName = pokemonName;
//
//    }
}
