package com.piotrwalkusz.lebrb.lanlearn

import com.piotrwalkusz.lebrb.lanlearn.wordsexporters.WordsExporter
import java.io.InputStream

class LanLearnProcessor {

    private val wordsCounter: WordsCounter = WordsCounter()

    fun translateWordsInTextAndExport(text: InputStream, mimeType: MediaType, dictionary: TranslationDictionary,
                                      exporter: WordsExporter): String {

        val wordsAndTranslations = translateWordsInText(text, mimeType, dictionary)
        return exporter.export(wordsAndTranslations)
    }

    fun translateWordsInText(text: InputStream, mimeType: MediaType, dictionary: TranslationDictionary)
            : Map<String, String> {

        val wordsCounterResult = wordsCounter.countWords(text, mimeType, dictionary.sourceLanguage,
                dictionary.getTranslatableWords())

        return wordsCounterResult.getWords().associateBy { dictionary.translate(it)!! }
    }
}