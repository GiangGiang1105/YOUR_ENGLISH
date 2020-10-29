package com.example.ey_application.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.database.Repository.Translate;

import java.util.List;

public class TranslateViewModel extends ViewModel  {
    public MutableLiveData<String> resultTranslate;
    private Translate translate;
    public TranslateViewModel() {
    }
    public void init(){
        try{
            translate = new Translate();
            resultTranslate = new MutableLiveData<>();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public MutableLiveData<String> translate(String text){
        resultTranslate = translate.translate(text);
        return  resultTranslate;
    }

}
