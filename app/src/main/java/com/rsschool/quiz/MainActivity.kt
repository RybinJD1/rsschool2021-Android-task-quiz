package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentController {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        open(IntArray(5))
    }

    override fun open(answers: IntArray, number: Int, result: Int) {
        openFirstFragment(number, result, answers)
    }

    override fun result(result: Int, answers: IntArray) {
        submit(result, answers)
    }

    private fun openFirstFragment(number: Int, result: Int, answers: IntArray) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("$number")
            .replace(
                R.id.fragmentContainerView,
                QuestionsFragment.newInstance(number, result, answers)
            )
            .commit()
    }

    private fun submit(result: Int, answers: IntArray) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, ResultFragment.newInstance(result, answers))
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        super.onBackPressed()
    }
}


