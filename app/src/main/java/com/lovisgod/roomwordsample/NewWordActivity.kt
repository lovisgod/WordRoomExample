package com.lovisgod.roomwordsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)
    }
}
