package com.rsschool.quiz

interface FragmentController {

    fun open(answers: IntArray, number: Int = 0, result: Int = 0)

    fun result(result: Int, answers: IntArray)

}