package io.garrit.android.demo.tododemo.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.garrit.android.demo.tododemo.R

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }
}