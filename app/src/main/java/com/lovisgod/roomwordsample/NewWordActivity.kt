package com.lovisgod.roomwordsample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            // MainActivity is sending an intent
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                // set the result for the intent and send back to main activity
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                // get the text from the edit text and parse to string
                val word = editWordView.text.toString()
                // add a string extra to the intent
                replyIntent.putExtra(EXTRA_REPLY, word)
                // set the result for the intent and send back to main activity
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
