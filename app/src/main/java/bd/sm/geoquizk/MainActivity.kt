package bd.sm.geoquizk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.Question

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton

    private val questionBank = listOf(
        Question(R.string.question_bangladesh, true),
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        questionTextView = findViewById(R.id.question_text_view)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        trueButton.setOnClickListener { view: View ->
            //Toast.makeText(this,R.string.correct_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            //Toast.makeText(this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        }
        previousButton.setOnClickListener { view: View ->
            if (currentIndex == 0) {
                Toast.makeText(this, "No Previous Question", Toast.LENGTH_SHORT).show();
            } else {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }
        }
        nextButton.setOnClickListener { view: View ->
            //currentIndex=QuestionTextResId
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    /* private fun updateQuestion() {
         val questionTextResId = questionBank[currentIndex].textResId
         questionTextView.setText(questionTextResId)
     }*/
}