package bd.sm.geoquizk

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.geoquiz.Question

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    /*init {
        Log.d(TAG, "ViewModel instance created")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }*/
    var currentIndex = 0
    var isCheater = false
    private val questionBank = listOf(
        Question(R.string.question_bangladesh, true),
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer:Boolean
    get()=questionBank[currentIndex].answer

    val  currentQuestionText:Int
    get() = questionBank[currentIndex].textResId

    fun movToNext(){
        currentIndex=(currentIndex+1)%questionBank.size
    }

    fun movToPrev(){
        if (currentIndex == 0) {
           // Toast.makeText(MainActivity, "No Previous Question", Toast.LENGTH_SHORT).show();
        } else {
            currentIndex=(currentIndex-1)%questionBank.size
        }
    }

}
