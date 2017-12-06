package cmps121.focus;

import android.util.Log;

/**
 * Created by Alston-PC on 12/2/2017.
 */

public class Pokemon{
    int number;
    String name;
    String url;
    String hp;
    String attack;
    String speed;
    String height;
    String weight;
    String Description;
    String moves;
    String baseExperience;
    String defense;

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        Log.i("POKEMON", "height: " + height);
        this.height = height;
        Log.i("POKEMON", "this.height: " + this.height);
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(String baseExperience) {
        this.baseExperience = baseExperience;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

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
