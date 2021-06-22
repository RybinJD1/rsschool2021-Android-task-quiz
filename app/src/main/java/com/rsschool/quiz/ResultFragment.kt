package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: FragmentController
    private val listQuestion = listOfQuestions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context);
        controller = context as FragmentController
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = requireNotNull(arguments?.getInt(RESULT))
        val answers = requireNotNull(arguments?.getIntArray(LIST_ANSWER))
        val res = getString(R.string.result)
        val from = getString(R.string.from)
        binding.result.text = "$res $result $from"

        binding.share.setOnClickListener {
            val resultIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, returnResult(answers))
                putExtra(Intent.EXTRA_SUBJECT, "result")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(resultIntent, null)
            startActivity(shareIntent)
        }

        binding.anew.setOnClickListener {
            controller.open(IntArray(5))
        }

        binding.exit.setOnClickListener {
            activity?.finishAffinity()
            exitProcess(0)
        }

    }

    private fun returnResult(answers: IntArray): String {
        val result = requireNotNull(arguments?.getInt(RESULT))
        var send = "Результат: $result из 5 "
        var count = 0
        var answer = ""
        while (count < 5) {
            when (answers[count]) {
                1 -> answer = listQuestion[count].one
                2 -> answer = listQuestion[count].two
                3 -> answer = listQuestion[count].three
                4 -> answer = listQuestion[count].four
                5 -> answer = listQuestion[count].five
            }
            send += "Вопрос №${listQuestion[count].question} \n " +
                    "Ваш ответ: $answer\n\n"
            count++
        }

        return send
    }

    companion object {
        @JvmStatic
        fun newInstance(result: Int, answers: IntArray): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(RESULT, result)
            args.putIntArray(LIST_ANSWER, answers)
            fragment.arguments = args
            return fragment
        }

        private const val RESULT = "RESULT"
        private const val LIST_ANSWER = "LIST_ANSWER"

    }
}