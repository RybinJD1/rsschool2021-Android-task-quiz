package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuestionsBinding

class QuestionsFragment : Fragment() {

    private var _binding: FragmentQuestionsBinding? = null
    private val binding: FragmentQuestionsBinding get() = requireNotNull(_binding)

    private var controller: FragmentController? = null
    private val listQuestion = listOfQuestions


    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = context as FragmentController
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setTheme(inflater)
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val answers = requireNotNull(arguments?.getIntArray(ANSWERS))
        val number = requireNotNull(arguments?.getInt(NUMBER))
        var result = requireNotNull(arguments?.getInt(RESULT))

        binding.toolbar.title = getString(R.string.question, number + 1)
        fillQuestionsData(number)
        setButtonState(answers, number)
        setRadioButtonState(answers, number)

        binding.previousButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.radioGroup.setOnCheckedChangeListener { radioG, i ->
            binding.nextButton.isEnabled = radioG.checkedRadioButtonId != -1
            when (i) {
                R.id.option_one -> answers[number] = 1
                R.id.option_two -> answers[number] = 2
                R.id.option_three -> answers[number] = 3
                R.id.option_four -> answers[number] = 4
                R.id.option_five -> answers[number] = 5
            }
        }

        binding.nextButton.setOnClickListener {
            if (number in 0..3)
                controller?.open(answers, (number + 1), result)
            else {
                var count = 0
                for (i in listQuestion) {
                    if (i.answer == answers[count]) {
                        result++
                        count++
                    } else count++
                }
                controller?.result(result, answers)
            }
        }

    }


    private fun setTheme(inflater: LayoutInflater) {
        when (requireNotNull(arguments?.getInt(NUMBER))) {
            0 -> inflater.context.setTheme(R.style.Theme_Quiz_First)
            1 -> inflater.context.setTheme(R.style.Theme_Quiz_Second)
            2 -> inflater.context.setTheme(R.style.Theme_Quiz_Three)
            3 -> inflater.context.setTheme(R.style.Theme_Quiz_Four)
            else -> inflater.context.setTheme(R.style.Theme_Quiz_Five)
        }
    }

    private fun setRadioButtonState(answers: IntArray, number: Int) {
        when (answers[number]) {
            1 -> binding.optionOne.isChecked = true
            2 -> binding.optionTwo.isChecked = true
            3 -> binding.optionThree.isChecked = true
            4 -> binding.optionFour.isChecked = true
            5 -> binding.optionFive.isChecked = true
        }
    }

    private fun fillQuestionsData(number: Int) {
        binding.optionOne.text = listQuestion[number].one
        binding.optionTwo.text = listQuestion[number].two
        binding.optionThree.text = listQuestion[number].three
        binding.optionFour.text = listQuestion[number].four
        binding.optionFive.text = listQuestion[number].five
        binding.question.text = listQuestion[number].question
    }

    private fun setButtonState(answers: IntArray, number: Int) {
        if (answers[number] != 0) {
            binding.nextButton.isEnabled = (true)
        } else {
            binding.nextButton.isEnabled = (false)
        }

        if (number == 0) {
            binding.previousButton.isEnabled = false
            binding.toolbar.navigationIcon = null
        } else {
            binding.previousButton.isEnabled = true
            binding.toolbar.navigationIcon
        }

        if (number == 4) {
            binding.nextButton.text = getString(R.string.submit)
        }
    }

    companion object {

        private const val NUMBER = "NUMBER"
        private const val RESULT = "RESULT"
        private const val ANSWERS = "ANSWERS"

        @JvmStatic
        fun newInstance(numberQuestion: Int, result: Int, answers: IntArray): QuestionsFragment {
            val args = bundleOf(NUMBER to numberQuestion, RESULT to result, ANSWERS to answers)
            return QuestionsFragment().apply { arguments = args }
        }

    }
}