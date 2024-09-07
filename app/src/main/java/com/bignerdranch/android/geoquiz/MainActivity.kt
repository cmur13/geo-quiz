package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

// add a TAG constant
private const val  TAG = "MainActivity"

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
        // add a log statement, d stands for debug
        Log.d(TAG, "OnCreate(Bundle?) called")
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

        // add previous button
        binding.prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }
    // override five more lifecycle funcions
    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
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
