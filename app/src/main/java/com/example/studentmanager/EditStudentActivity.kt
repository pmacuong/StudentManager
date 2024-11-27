package com.example.studentmanager


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val nameEditText = findViewById<EditText>(R.id.edit_text_name)
        val idEditText = findViewById<EditText>(R.id.edit_text_id)
        val saveButton = findViewById<Button>(R.id.button_save)

        val position = intent.getIntExtra("position", -1)
        val name = intent.getStringExtra("studentName")
        val id = intent.getStringExtra("studentId")

        nameEditText.setText(name)
        idEditText.setText(id)

        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newId = idEditText.text.toString()
            if (newName.isNotEmpty() && newId.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("position", position)
                resultIntent.putExtra("studentName", newName)
                resultIntent.putExtra("studentId", newId)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}