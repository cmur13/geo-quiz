package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // create a list of question objects in MainActivity
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia,true)
    )
    // index for the list
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener{view: View->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {view:View->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex=(currentIndex+1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }
    private fun updateQuestion(){
        val questionTextResID = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResID)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }
        Snackbar.make(findViewById(R.id.snack), messageResId, Snackbar.LENGTH_SHORT)
            .show()
    }
}
