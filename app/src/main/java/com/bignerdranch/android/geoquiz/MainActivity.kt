package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar

// add a TAG constant
private const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //add instance of QuizViewModel by invoking the viewModels() property delegate
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result->
        //Handle the result
        if(result.resultCode == Activity.RESULT_OK){
            quizViewModel.isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // add a log statement, d stands for debug
        Log.d(TAG, "OnCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener{view: View->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {view:View->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        // add previous button
        binding.prevButton.setOnClickListener {
            quizViewModel.moveBack()
            updateQuestion()
        }
        binding.cheatButton.setOnClickListener {
            // start cheat activity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
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
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when{
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Snackbar.make(findViewById(R.id.snack), messageResId, Snackbar.LENGTH_SHORT)
            .show()
    }
}
