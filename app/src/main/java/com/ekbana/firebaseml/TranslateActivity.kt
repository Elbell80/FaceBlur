package com.ekbana.firebaseml

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_translate.*

class TranslateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

       // initSpinner()

      //  btnTranslate.setOnClickListener { translate(edtInputText.text.toString()) }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }
/*

    private fun translate(inputText: String) {

        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.HI)
            .build()


        val translator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translateTo(translator, inputText)


            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }

    }

    private fun initSpinner() {
        val modelManager = FirebaseTranslateModelManager.getInstance()

        modelManager.getAvailableModels(FirebaseApp.getInstance())
            .addOnSuccessListener { models ->
                var modelsList = models as MutableList<FirebaseTranslateRemoteModel>
                Log.d("modelSize", modelsList.size.toString())
                val adapter = ArrayAdapter<FirebaseTranslateRemoteModel>(this, android.R.layout.simple_spinner_item, modelsList)

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnLanguages?.adapter = adapter
                spnLanguages?.onItemSelectedListener = this

            }.addOnFailureListener {
                Log.d("error", it.localizedMessage)
            }

    }

    private fun translateTo(translator: FirebaseTranslator, inputText: String) {

        translator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                edtTranslatedText.setText(translatedText)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
*/


}
