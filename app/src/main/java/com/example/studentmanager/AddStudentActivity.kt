package com.example.studentmanager


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val nameEditText = findViewById<EditText>(R.id.edit_text_name)
        val idEditText = findViewById<EditText>(R.id.edit_text_id)
        val addButton = findViewById<Button>(R.id.button_add)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("studentName", name)
                resultIntent.putExtra("studentId", id)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}