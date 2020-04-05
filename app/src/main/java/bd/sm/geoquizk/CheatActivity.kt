package bd.sm.geoquizk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ChangedPackages
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_cheat.*

const val EXTRA_ANSWER_SHOWN = "bd.sm.geoquizk.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "bd.sm.geoquizk.answer_is_true"
private lateinit var answerTextView: TextView
private lateinit var showAnswerButton: Button

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue=intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)

        answerTextView=findViewById(R.id.answer_text_view)
        showAnswerButton=findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener{view: View ->
            val  answerText=when{
                answerIsTrue->R.string.true_button
                else->R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)

        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK,data)
    }

    companion object{
        fun newIntent(packagesContext: Context,answerIsTrue:Boolean): Intent {
            return Intent(packagesContext,CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)
            }
        }

    }
}

