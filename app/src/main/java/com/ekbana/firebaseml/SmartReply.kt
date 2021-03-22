package com.ekbana.firebaseml

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_smart_reply.*

class SmartReply : AppCompatActivity() {

  /*  private var conversation = ArrayList<FirebaseTextMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_reply)

        btnSend.setOnClickListener {
            addMessage(edtMEssageTwo.text.toString())
        }

        btnHints.setOnClickListener {
            getHints()
        }

        btnClear.setOnClickListener {
            clearFields()
        }
    }

    private fun clearFields() {
        btnHintOne.visibility = View.GONE
        btnHintTwo.visibility = View.GONE
        btnHintThree.visibility = View.GONE

        txvErrorText.text = ""

        btnHintOne.setOnClickListener {
            addMessage(btnHintOne.text.toString())
        }
        btnHintTwo.setOnClickListener {
            addMessage(btnHintTwo.text.toString())
        }
        btnHintThree.setOnClickListener {
            addMessage(btnHintThree.text.toString())
        }
    }*/

/*    private fun addMessage(textMessage: String) {
        conversation.add(
            FirebaseTextMessage.createForRemoteUser(
                textMessage,
                System.currentTimeMillis(),
                edtMessageONE.text.toString()
            )
        )
    }

    private fun getHints() {
        val smartReply = FirebaseNaturalLanguage.getInstance().smartReply
        smartReply.suggestReplies(conversation)
            .addOnSuccessListener { result ->
                if (result.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                    txvErrorText.text = getString(R.string.language_not_supported)
                } else if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                    btnHintOne.text = result.suggestions[0].text
                    btnHintTwo.text = result.suggestions[1].text
                    btnHintThree.text = result.suggestions[2].text

                    btnHintOne.visibility = View.VISIBLE
                    btnHintTwo.visibility = View.VISIBLE
                    btnHintThree.visibility = View.VISIBLE

                    txvErrorText.text = ""
                }

            }.addOnFailureListener {
                txvErrorText.text = it.localizedMessage
            }

    }*/
}
