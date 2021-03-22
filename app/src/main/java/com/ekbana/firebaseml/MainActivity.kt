package com.ekbana.firebaseml

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*  btnBottomNav.setOnNavigationItemSelectedListener(object: BottomNavigationView.OnNavigationItemSelectedListener{
              override fun onNavigationItemSelected(item: MenuItem): Boolean {
                  when (item.itemId) {
                      R.id.translate -> {
                          startActivity(Intent(this@MainActivity, TranslateActivity::class.java))
                      }
                      R.id.smartReply -> {
                          startActivity(Intent(this@MainActivity, SmartReply::class.java))
                      }
                      R.id.identifyLanguage -> {
                          //identifyLanguage(edtInput.text.toString())
                      }
                  }
                  return true
              }
          })*/
    }


    /* private fun identifyLanguage(inputText: String) {
         val languageIdentifier = FirebaseNaturalLanguage.getInstance().languageIdentification
         languageIdentifier.identifyLanguage(inputText)
             .addOnSuccessListener { language ->
                 txvLanguage?.append(
                     String.format(
                         Locale.US, "\n%s - %s",
                         inputText, language
                     )
                 )

             }.addOnFailureListener {
                 Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
             }

     }

     private fun identifyPossibleLanguage(inputText: String) {
         val languageIdentifier = FirebaseNaturalLanguage.getInstance().languageIdentification
         languageIdentifier.identifyPossibleLanguages(inputText)
             .addOnSuccessListener { identifiedLanguages ->
                 val possibleLanguages = ArrayList<String>(identifiedLanguages.size)
                 for (identifiedLanguage in identifiedLanguages) {
                     possibleLanguages.add(
                         String.format(
                             Locale.US,
                             "%s (%3f)",
                             identifiedLanguage.languageCode,
                             identifiedLanguage.confidence
                         )
                     )
                 }
                 txvDetails?.append(
                     String.format(
                         Locale.US,
                         "\n%s - [%s]",
                         inputText,
                         TextUtils.join(", ", possibleLanguages)
                     )
                 )


             }.addOnFailureListener {
                 Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
             }
     }*/
}
