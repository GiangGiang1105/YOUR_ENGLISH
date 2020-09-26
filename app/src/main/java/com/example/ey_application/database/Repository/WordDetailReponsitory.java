package com.example.ey_application.database.Repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.Word.DataWord;
import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.Model.Word.InforDetail;
import com.example.ey_application.Model.Word.InforWord;
import com.example.ey_application.Model.Word.Meaning;
import com.example.ey_application.Model.Word.Phonetics;
import com.example.ey_application.database.API.GetForWord;
import com.example.ey_application.database.API.GetInforWordAPI;
import com.example.ey_application.database.RetrofitService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordDetailReponsitory {
    public interface WordDetailResult{
        void onResult(DataWord dataWord);
    }
    private static WordDetailReponsitory wordReponsitory;
    private GetInforWordAPI getInforWordAPI;
    private GetForWord getForWord;
    private static  WordDetailResult wordDetailResult;
    public static WordDetailReponsitory getInstance(WordDetailResult onwordDetailResult){
        if (wordReponsitory == null){
            wordReponsitory = new WordDetailReponsitory();
            wordDetailResult =  onwordDetailResult;
        }
        return wordReponsitory;
    }

    public WordDetailReponsitory(){
        getInforWordAPI = RetrofitService.createServiceEnglish(GetInforWordAPI.class);
        getForWord = RetrofitService.createService(GetForWord.class);
    }
    public MutableLiveData<DataWord> getWord(String word){
        final  MutableLiveData<DataWord> listWord = new MutableLiveData<>();
        getInforWordAPI.getWord(word).enqueue(new Callback<List<InforWord>>() {
            @Override
            public void onResponse(Call<List<InforWord>> call, Response<List<InforWord>> response) {
                Log.i("haizzzz", "hiihhhhhhhhhhhhhhhhhhhhhhhhhhh");
                if ((response.body()) != null && (response.body().get(0) != null) && (response.body().get(0).getMeaning() != null) ){
                    List<Detail> listdefinition = listDefinition(response.body().get(0).getMeaning());
                    List<Detail> listExample = listExample(response.body().get(0).getMeaning());
                    List<Detail> listSynonyms= listSynonyms(response.body().get(0).getMeaning());
                    Phonetics phonetics = (Phonetics) response.body().get(0).getPhonetics().get(0);
                    DataWord dataWord  = new DataWord(listdefinition, listExample, phonetics, listSynonyms);
                    listWord.postValue(dataWord);
                }

            }

            @Override
            public void onFailure(Call<List<InforWord>> call, Throwable t) {
                Log.i("onFailure", t.getMessage());
            }
        });
        return listWord;
    }
    private List<Detail> listDefinition(Meaning meaning){
        List<Detail> resultDefinition = new ArrayList<>();
        if (meaning.getNoun() != null){
           List<String> definition = new ArrayList<>();
            if (meaning.getNoun() != null){
                for (InforDetail inforDetail : meaning.getNoun()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Noun", definition);
                resultDefinition.add(objectDefinition);
            }


        }
        if (meaning.getAdjective() != null){
            List<String> definition = new ArrayList<>();
            if (meaning.getAdjective() != null){
                for (InforDetail inforDetail : meaning.getAdjective()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Adjective", definition);
                resultDefinition.add(objectDefinition);
            }
        }
        if (meaning.getAdverb() !=  null){
            List<String> definition = new ArrayList<>();
            if (meaning.getAdverb() != null){
                for (InforDetail inforDetail : meaning.getAdverb()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail ("Adverb", definition);
                resultDefinition.add(objectDefinition);
            }
        }
        if (meaning.getVerb() !=  null){
            List<String> definition = new ArrayList<>();
            if (meaning.getVerb() != null){
                for (InforDetail inforDetail : meaning.getVerb()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Verb", definition);
                resultDefinition.add(objectDefinition);
            }
        }
        if (meaning.getExclamation() != null){
            List<String> definition = new ArrayList<>();
            if (meaning.getExclamation() != null){
                for (InforDetail inforDetail : meaning.getExclamation()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Exclamation", definition);
                resultDefinition.add(objectDefinition);
            }

        }
        if (meaning.getPhrase() != null){
            List<String> definition = new ArrayList<>();
            if (meaning.getPhrase() != null){
                for (InforDetail inforDetail : meaning.getPhrase()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Phrase", definition);
                resultDefinition.add(objectDefinition);
            }

        }
        if (meaning.getTransitive_verb() != null){
            List<String> definition = new ArrayList<>();
            if (meaning.getTransitive_verb() != null){
                for (InforDetail inforDetail : meaning.getTransitive_verb()){
                    definition.add(inforDetail.getDifinition());
                }
            }
            if (definition.size() != 0){
                Detail objectDefinition = new Detail("Transitive Verb", definition);
                resultDefinition.add(objectDefinition);
            }

        }

        return resultDefinition;
    }
    private List<Detail> listExample(Meaning meaning){
        List<Detail> resultExample = new ArrayList<>();
        if (meaning.getNoun() != null){
            List<String> Noun = new ArrayList<>();
            if (meaning.getNoun() != null){
                for (InforDetail inforDetail : meaning.getNoun()){
                    if (inforDetail.getExample() != null){
                        Noun.add(inforDetail.getExample());
                    }
                }
            }
            if (Noun.size() != 0){
                Detail objectExample = new Detail("Noun",Noun);
                resultExample.add(objectExample);
            }
        }
        if (meaning.getAdjective() != null){
            List<String> Adjective= new ArrayList<>();
            if (meaning.getAdjective() != null){
                for (InforDetail inforDetail : meaning.getAdjective()){
                    if (inforDetail.getExample() != null){
                        Adjective.add(inforDetail.getExample());
                    }

                }
            }
            if (Adjective.size() != 0){
                Detail objectExample = new Detail("Adjective", Adjective);
                resultExample.add(objectExample);
            }

        }
        if (meaning.getAdverb() !=  null){
            List<String> Adverb = new ArrayList<>();
            if (meaning.getAdverb() != null){
                for (InforDetail inforDetail : meaning.getAdverb()){

                    if (inforDetail.getExample() != null){
                        Adverb.add(inforDetail.getExample());
                    }
                }
            }
            if (Adverb.size() != 0){
                Detail objectDefinition = new Detail ("Adverb",Adverb);
                resultExample.add(objectDefinition);
            }
        }
        if (meaning.getVerb() !=  null){
            List<String> Verb = new ArrayList<>();
            if (meaning.getVerb() != null){
                for (InforDetail inforDetail : meaning.getVerb()){
                    if (inforDetail.getExample() != null){
                        Verb.add(inforDetail.getExample());
                    }
                }
            }
            if (Verb.size() != 0){
                Detail objectExample = new Detail("Verb", Verb);
                resultExample.add(objectExample);
            }

        }
        if (meaning.getExclamation() != null){
            List<String> Exclamation= new ArrayList<>();
            if (meaning.getExclamation() != null){
                for (InforDetail inforDetail : meaning.getExclamation()){
                    if (inforDetail.getExample() != null){
                        Exclamation.add(inforDetail.getExample());
                    }
                }
            }
            if (Exclamation.size() != 0){
                Detail objectExample = new Detail("Exclamation", Exclamation);
                resultExample.add(objectExample);
            }
        }
        if (meaning.getPhrase() != null){
            List<String> Phrase = new ArrayList<>();
            if (meaning.getPhrase() != null){
                for (InforDetail inforDetail : meaning.getPhrase()){
                    if (inforDetail.getExample() != null){
                        Phrase.add(inforDetail.getExample());
                    }
                }
            }
            if (Phrase.size() != 0){
                Detail objectExample = new Detail("Phrase", Phrase);
                resultExample.add(objectExample);
            }

        }
        if (meaning.getTransitive_verb() != null){
            List<String> Transitive_verb = new ArrayList<>();
            if (meaning.getTransitive_verb() != null){
                for (InforDetail inforDetail : meaning.getTransitive_verb()){
                    if (inforDetail.getExample() != null){
                        Transitive_verb.add(inforDetail.getExample());
                    }
                }
            }
            if (Transitive_verb.size() != 0){
                Detail objectExample = new Detail("Transitive Verb", Transitive_verb);
                resultExample.add(objectExample);
            }

        }

        return resultExample;
    }
    private List<Detail> listSynonyms(Meaning meaning){
        List<Detail> resultSynonyms = new ArrayList<>();
        if (meaning.getNoun() != null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getNoun()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }

            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Noun", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }

        }
        if (meaning.getAdjective() != null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getAdjective()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }
            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Adjective", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }

        }
        if (meaning.getAdverb() !=  null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getAdverb()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }
            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Adverb", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }
        }
        if (meaning.getVerb() !=  null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getVerb()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }
            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Verb", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }

        }
        if (meaning.getExclamation() != null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getExclamation()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }

            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Exclamation", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }
        }
        if (meaning.getPhrase() != null){
            List<String> Synonyms = new ArrayList<>();

            for (InforDetail inforDetail : meaning.getPhrase()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }
            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Phrase", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }
        }
        if (meaning.getTransitive_verb() != null){
            List<String> Synonyms = new ArrayList<>();
            for (InforDetail inforDetail : meaning.getTransitive_verb()){
                if (inforDetail.getSynonym() != null){
                    for (String synonyms : inforDetail.getSynonym()){
                        Synonyms.add(synonyms);
                    }
                }
            }
            if (Synonyms.size() != 0){
                Detail objectSynonyms = new Detail("Transitive Verb", Synonyms);
                resultSynonyms.add(objectSynonyms);
            }
        }

        return resultSynonyms;
    }
}
