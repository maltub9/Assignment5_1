package com.example.assignment5_1

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.collections.*

class MainActivity : AppCompatActivity() {
    var questions= mutableListOf<String>()
    var answers = mutableMapOf<Int,List<String>>()
    var correct_answers = mutableMapOf<Int,Int>()
    var submitedAnswers= mutableListOf<Int>()
    var selectedIndex:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fillData()
        fillQuestion(selectedIndex)
        var next = findViewById<Button>(R.id.nextButton)
        var submit = findViewById<Button>(R.id.submitButton)
        var reset = findViewById<Button>(R.id.resetButton)


        next.setOnClickListener{
            if(selectedIndex<4)
            selectedIndex++
            else
                selectedIndex=0
            fillQuestion(selectedIndex)

        }

        var previous = findViewById<Button>(R.id.previousButton)
        previous.setOnClickListener{
            if(selectedIndex>0)
                selectedIndex--
            else
                selectedIndex=4
            fillQuestion(selectedIndex)

        }

        reset.setOnClickListener{
            questions.clear()
            answers.clear()
            submitedAnswers.clear()
            correct_answers.clear()
            fillData()
            fillQuestion(0)
        }

        submit.setOnClickListener{
            var msg:String = "Congratulations!!!"
            var caption:String = "Your achievment is "
            var correct = 0

            for (i in 0..4)
            {
                if(submitedAnswers.get(i)==correct_answers.get(i+1))
                    correct++
            }

            var achive:Double = correct.toDouble()/5.0

            if(achive<0.5)
            {
                msg="Sorry, You failed!"
            }
            caption +=achive.toString()

            val builder = AlertDialog.Builder(this)
            builder.setTitle(msg)
            builder.setMessage(caption)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }

            builder.show()

        }
    }

    fun  fillQuestion(indx:Int)
    {
        var submitLayout = findViewById<LinearLayout>(R.id.buttonsContainer2)
        selectedIndex=indx
        if(selectedIndex==4)
        {
            //show submit/reset button
            submitLayout.visibility= View.VISIBLE
        }
        else
        {
            //hide submit/reset button
            submitLayout.visibility= View.INVISIBLE
        }
        var container = findViewById<TableLayout>(R.id.questionContainer)

        container.removeAllViews()
        //fill first question...
        var q:TextView=TextView(this)
        q.text=(indx+1).toString() + ". " + questions.get(indx)
        container.addView(q)
        var group:RadioGroup= RadioGroup(this)
        group.orientation=LinearLayout.VERTICAL
        var count:Int=0

        answers.get(indx+1)?.forEach{
            var ans:RadioButton= RadioButton(this)
            ans.text=it
            if(submitedAnswers.get(selectedIndex)!=-1){
                if(count==submitedAnswers.get(selectedIndex))
                    ans.isChecked=true
            }
            ans.tag=count
            ans.setOnCheckedChangeListener { buttonView, isChecked ->submitAnswer(buttonView.tag.toString().toInt())}
            group.addView(ans)
            count++
        }

        container.addView(group)

    }

    fun submitAnswer(indx:Int)
    {
        submitedAnswers.set(selectedIndex,indx)
    }

    fun fillData()
    {
        questions.add("You can create an emulator to simulate the configuration of a particular type of Android device using a tool like")
        questions.add("What parameter specifies the Android API level that Gradle should use to compile your app?")
        questions.add(" Scroll view can contain only one view, or view group, as a child.")
        questions.add("You can show or hide a view in your app by setting its visibility. Which of the following are valid visibility values?")
        questions.add("What phrase means that the compiler validates types while compiling?")

        answers.put(1, listOf("Theme Editor","Android SDK Manager","AVD Manager","Virtual Editor"))
        answers.put(2, listOf("minSdkVersion","compileSdkVersion","targetSdkVersion","testSdkVersion"))
        answers.put(3, listOf("True","False"))
        answers.put(4, listOf("VISIBLE","GONE","CLEAR","INVISIBLE","NONE"))
        answers.put(5, listOf("Type safety","Data binding","Type validation","Wrong text"))

        correct_answers.put(1,2)
        correct_answers.put(2,2)
        correct_answers.put(3,0)
        correct_answers.put(4,0)
        correct_answers.put(5,0)

        submitedAnswers.add(-1)
        submitedAnswers.add(-1)
        submitedAnswers.add(-1)
        submitedAnswers.add(-1)
        submitedAnswers.add(-1)
    }
}