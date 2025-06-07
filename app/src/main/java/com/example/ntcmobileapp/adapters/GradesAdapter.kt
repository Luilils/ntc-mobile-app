package com.example.ntcmobileapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ntcmobileapp.R
import com.example.ntcmobileapp.models.GradeEntry

class GradesAdapter : RecyclerView.Adapter<GradesAdapter.GradeViewHolder>() {
    
    private var gradesList = listOf<GradeEntry>()

    fun updateGrades(newGrades: List<GradeEntry>) {
        gradesList = newGrades
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grade, parent, false)
        return GradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val grade = gradesList[position]
        holder.bind(grade)
    }

    override fun getItemCount(): Int = gradesList.size

    class GradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val courseCodeText: TextView = itemView.findViewById(R.id.courseCodeText)
        private val courseNameText: TextView = itemView.findViewById(R.id.courseNameText)
        private val unitsText: TextView = itemView.findViewById(R.id.unitsText)
        private val gradeText: TextView = itemView.findViewById(R.id.gradeText)
        private val remarksText: TextView = itemView.findViewById(R.id.remarksText)

        fun bind(grade: GradeEntry) {
            courseCodeText.text = grade.courseCode
            courseNameText.text = grade.courseName
            unitsText.text = grade.units.toString()
            gradeText.text = String.format("%.2f", grade.grade)
            remarksText.text = grade.remarks
        }
    }
} 