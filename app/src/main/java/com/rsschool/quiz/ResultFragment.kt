package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = requireNotNull(_binding)

    private var controller: FragmentController? = null
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
        super.onAttach(context)
        controller = context as FragmentController
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = requireNotNull(arguments?.getInt(RESULT))
        val answers = requireNotNull(arguments?.getIntArray(LIST_ANSWER))
        binding.result.text = getString(R.string.result, result)

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
            controller?.open(IntArray(5))
        }

        binding.exit.setOnClickListener {
            activity?.finishAffinity()
            exitProcess(0)
        }

    }

    private fun returnResult(answers: IntArray): String {
        val result = requireNotNull(arguments?.getInt(RESULT))
        var send = getString(R.string.result, result)
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
            send += getString(R.string.send, listQuestion[count].question, answer)
//            send += "Вопрос №${listQuestion[count].question} \n " +
//                    "Ваш ответ: $answer\n\n"
            count++
        }

        return send
    }

    companion object {

        private const val RESULT = "RESULT"
        private const val LIST_ANSWER = "LIST_ANSWER"

        @JvmStatic
        fun newInstance(result: Int, answers: IntArray): ResultFragment {
            val args = bundleOf(RESULT to result, LIST_ANSWER to answers)
            return ResultFragment().apply { arguments = args }
        }

    }
}