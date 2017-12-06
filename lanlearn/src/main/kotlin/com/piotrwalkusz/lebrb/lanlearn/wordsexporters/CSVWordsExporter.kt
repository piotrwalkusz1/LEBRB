package com.piotrwalkusz.lebrb.lanlearn.wordsexporters

class CSVWordsExporter : WordsExporter {

    override val outputFormatName = "CSV"

    override fun export(wordsAndTranslations: Map<String, String>): String {

        return wordsAndTranslations.map { "${it.key};${it.value}" }.joinToString("\n")
    }
}