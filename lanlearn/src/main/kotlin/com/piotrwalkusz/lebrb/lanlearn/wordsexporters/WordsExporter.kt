package com.piotrwalkusz.lebrb.lanlearn.wordsexporters

interface WordsExporter {

    val outputFormatName: String

    fun export(wordsAndTranslations: Map<String, String>): String
}