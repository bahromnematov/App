package uz.gita.exam1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import uz.gita.exam1.App.App;

public class Storage {
    private SharedPreferences pref;
    static Storage storage;


    private Storage(){
        pref= App.context.getSharedPreferences("Storage", Context.MODE_PRIVATE);
        pref.edit().putString("LOGIN1","user1").apply();
        pref.edit().putString("LOGIN2","user1").apply();
        pref.edit().putString("LOGIN3","user1").apply();
        pref.edit().putString("LOGIN4","user1").apply();
        pref.edit().putString("LOGIN5","user1").apply();
        pref.edit().putString("LOGIN6","user1").apply();
        pref.edit().putString("LOGIN7","user1").apply();
        pref.edit().putString("LOGIN8","user1").apply();
        pref.edit().putString("LOGIN9","user1").apply();
        pref.edit().putString("LOGIN10","user1").apply();

        pref.edit().putString("PASSWORD1","12345").apply();
        pref.edit().putString("PASSWORD2","12345").apply();
        pref.edit().putString("PASSWORD3","12345").apply();
        pref.edit().putString("PASSWORD4","12345").apply();
        pref.edit().putString("PASSWORD5","12345").apply();
        pref.edit().putString("PASSWORD6","12345").apply();
        pref.edit().putString("PASSWORD7","12345").apply();
        pref.edit().putString("PASSWORD8","12345").apply();
        pref.edit().putString("PASSWORD9","12345").apply();
        pref.edit().putString("PASSWORD10","12345").apply();
    }
    static Storage getInstance(){
        if (storage==null) storage=new Storage();
        return storage;
    }
    public void saveUser() {

    }

    public String getUsers(int i) {
        return new HashMap<String, String>().put(pref.getString("LOGIN"+i, "user1"),pref.getString("PASSWORD"+i,"12345"));
    }


}
