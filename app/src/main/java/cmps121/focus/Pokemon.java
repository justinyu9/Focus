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

    public int getID(){
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = Integer.valueOf(number);
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
