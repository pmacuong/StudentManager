package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: CustomAdapter
    private var students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view_students)
        studentAdapter = CustomAdapter(this, students)
        listView.adapter = studentAdapter

        registerForContextMenu(listView)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("studentName", students[position].studentName)
            intent.putExtra("studentId", students[position].studentId)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.edit -> {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("position", info.position)
                intent.putExtra("studentName", students[info.position].studentName)
                intent.putExtra("studentId", students[info.position].studentId)
                startActivityForResult(intent, EDIT_REQUEST_CODE)
                true
            }
            R.id.remove -> {
                val student = students.removeAt(info.position)
                studentAdapter.notifyDataSetChanged()
                Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(info.position, student)
                        studentAdapter.notifyDataSetChanged()
                    }
                    .show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, ADD_REQUEST_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_REQUEST_CODE -> {
                    val name = data?.getStringExtra("studentName")
                    val id = data?.getStringExtra("studentId")
                    if (name != null && id != null) {
                        students.add(StudentModel(name, id))
                        studentAdapter.notifyDataSetChanged()
                    }
                }
                EDIT_REQUEST_CODE -> {
                    val position = data?.getIntExtra("position", -1)
                    val name = data?.getStringExtra("studentName")
                    val id = data?.getStringExtra("studentId")
                    if (position != null && position != -1 && name != null && id != null) {
                        students[position] = StudentModel(name, id)
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    companion object {
        const val ADD_REQUEST_CODE = 1
        const val EDIT_REQUEST_CODE = 2
    }
}