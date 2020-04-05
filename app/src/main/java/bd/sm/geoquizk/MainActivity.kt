package bd.sm.geoquizk

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.geoquiz.Question

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button


    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        /*val provider: ViewModelProvider= ViewModelProviders.of(this)
        val quizViewModel=provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")*/

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        questionTextView = findViewById(R.id.question_text_view)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        trueButton.setOnClickListener { view: View ->
            //Toast.makeText(this,R.string.correct_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            //Toast.makeText(this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        }
        previousButton.setOnClickListener { view: View ->
            /*if (currentIndex == 0) {
                Toast.makeText(this, "No Previous Question", Toast.LENGTH_SHORT).show();
            } else {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }*/
            quizViewModel.movToPrev()
            updateQuestion()
        }
        nextButton.setOnClickListener { view: View ->
            //currentIndex=QuestionTextResId
            quizViewModel.movToNext()
            //currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        cheatButton.setOnClickListener{view:View->
            //val intent=Intent(this,CheatActivity::class.java)
            val answerIsTrue=quizViewModel.currentQuestionAnswer
            val intent=CheatActivity.newIntent(this@MainActivity,answerIsTrue)
           // startActivity(intent)
            val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
        }
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        //val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        /*val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()*/
    }

    private fun updateQuestion() {
       // val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    /*companion object {
        private const val TAG = "MainActivity"
    }*/
    /*companion object {
        private const val TAG="MainActivity"
    }*/

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(Bundle?) called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(Bundle?) called")
    }

    /*override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(TAG,"onSaveInstanceState called")
        saveInstanceState.putInt(KEY_INDEX,mCurrentIndex)
    }*/

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(Bundle?) called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(Bundle?) called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(Bundle?) called")
    }

}
