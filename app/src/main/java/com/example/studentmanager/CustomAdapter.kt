package com.example.studentmanager


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(private val context: Context, private val students: List<StudentModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.layout_student_item, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        val student = students[position]
        vh.textStudentName.text = student.studentName
        vh.textStudentId.text = student.studentId

        return view
    }

    private class ViewHolder(row: View?) {
        val textStudentName: TextView = row?.findViewById(R.id.text_student_name)!!
        val textStudentId: TextView = row?.findViewById(R.id.text_student_id)!!
    }
}