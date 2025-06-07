package com.example.ntcmobileapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ntcmobileapp.adapters.GradesAdapter
import com.example.ntcmobileapp.models.GradeEntry

class StudentEvaluationActivity : AppCompatActivity() {
    private lateinit var gradesAdapter: GradesAdapter
    private lateinit var semesterSpinner: Spinner
    private lateinit var schoolYearSpinner: Spinner
    private lateinit var studentNameText: TextView
    private lateinit var courseText: TextView
    private lateinit var totalUnitsText: TextView
    private lateinit var gwaText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_evaluation)

        // Initialize views
        initializeViews()
        setupSpinners()
        setupRecyclerView()
        
        // Load initial data
        loadStudentInfo()
        loadGrades()
    }

    private fun initializeViews() {
        semesterSpinner = findViewById(R.id.semesterSpinner)
        schoolYearSpinner = findViewById(R.id.schoolYearSpinner)
        studentNameText = findViewById(R.id.studentNameText)
        courseText = findViewById(R.id.courseText)
        totalUnitsText = findViewById(R.id.totalUnitsText)
        gwaText = findViewById(R.id.gwaText)
    }

    private fun setupSpinners() {
        // Setup semester spinner
        val semesters = arrayOf("1st Semester", "2nd Semester", "Summer")
        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, semesters)
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        semesterSpinner.adapter = semesterAdapter

        // Setup school year spinner
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val schoolYears = (0..4).map { "${currentYear - it}-${currentYear - it + 1}" }
        val schoolYearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schoolYears)
        schoolYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        schoolYearSpinner.adapter = schoolYearAdapter

        // Add listeners
        semesterSpinner.setOnItemSelectedListener { loadGrades() }
        schoolYearSpinner.setOnItemSelectedListener { loadGrades() }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.gradesRecyclerView)
        gradesAdapter = GradesAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@StudentEvaluationActivity)
            adapter = gradesAdapter
        }
    }

    private fun loadStudentInfo() {
        // TODO: Load actual student info from database/API
        studentNameText.text = "Juan Dela Cruz"
        courseText.text = "BS Information Technology - 3rd Year"
    }

    private fun loadGrades() {
        // TODO: Load actual grades from database/API based on selected semester and school year
        val sampleGrades = listOf(
            GradeEntry("IT101", "Introduction to Computing", 3, 1.75f, "Passed"),
            GradeEntry("IT102", "Computer Programming 1", 3, 2.0f, "Passed"),
            GradeEntry("GE101", "Understanding the Self", 3, 1.5f, "Passed"),
            GradeEntry("PE101", "Physical Fitness", 2, 1.0f, "Passed")
        )

        gradesAdapter.updateGrades(sampleGrades)

        // Update summary
        updateSummary(sampleGrades)
    }

    private fun updateSummary(grades: List<GradeEntry>) {
        val totalUnits = grades.sumOf { it.units }
        val weightedSum = grades.sumOf { it.units * it.grade }
        val gwa = if (totalUnits > 0) weightedSum / totalUnits else 0.0

        totalUnitsText.text = totalUnits.toString()
        gwaText.text = String.format("%.2f", gwa)
    }

    private fun Spinner.setOnItemSelectedListener(action: () -> Unit) {
        this.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                action()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        })
    }
} 