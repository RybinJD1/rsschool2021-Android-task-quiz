package com.rsschool.quiz

data class Questions(
    val question: String,
    val one: String,
    val two: String,
    val three: String,
    val four: String,
    val five: String,
    val answer: Int
)

val listOfQuestions = listOf(
    Questions(
        "Самая маленькая птичка на земле?",
        "синица", "колибри",
        "воробей", "чиж",
        "королёк", 2
    ),

    Questions(
        "У какого животного все время растут зубы?",
        "рысь", "лось",
        "заяц", "корова",
        "бобер", 5
    ),

    Questions(
        "Из чего состоит горб у верблюда?",
        "из воды", "из костей",
        "из жира", "из мышц",
        "нет верного ответа", 3
    ),

    Questions(
        "Самая большая бабочка в мире?", "махаон",
        "крапивница", "тизания агриппина", "парусник маака",
        "Павлиноглазка Грушевая", 3
    ),

    Questions(
        "Сколько лап у паука?",
        "4", "6",
        "8", "10",
        "12", 3
    )
)