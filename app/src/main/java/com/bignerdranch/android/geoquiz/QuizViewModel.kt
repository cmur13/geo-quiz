package com.bignerdranch.android.geoquiz

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    // create a list of question objects
    private val questionBank = listOf(
        Question(R.string.question_brazil, true),
        Question(R.string.question_copa, true),
        Question(R.string.question_uruguay,true),
        Question(R.string.question_goalkeeper, false),
        Question(R.string.question_soccer, true),
        Question(R.string.question_usa,false)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private val questionAnswered = BooleanArray(questionBank.size)

    // index for the list
    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val questionBankSize: Int
        get() = questionBank.size

    private var score: Int = 0

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveBack(){
        currentIndex = if(currentIndex == 0){
            questionBank.size -1
        }else{
            currentIndex -1
        }
    }

    fun isCurrentQuestionAnswered(): Boolean{
        return questionAnswered[currentIndex]
    }

    fun questionAnswered(){
        questionAnswered[currentIndex] = true
    }

    fun increaseScore(): Int{
        return score ++
    }
    fun showScore(context: Context) {
        if (currentIndex == questionBankSize - 1 && isCurrentQuestionAnswered()) {
            Toast.makeText(context, "Your Score: $score/6", Toast.LENGTH_LONG).show()
        }
    }

}