package com.piotrwalkusz.lebrb.lanlearn.lemmatizers

import com.piotrwalkusz.lebrb.lanlearn.Language
import org.languagetool.tagging.Tagger
import org.languagetool.tagging.de.GermanTagger
import org.languagetool.tagging.en.EnglishTagger
import java.io.Reader

class LanguageToolLemmatizer(language: Language) : Lemmatizer {

    private val tagger: Tagger = when (language) {
        Language.GERMAN -> GermanTagger()
        Language.ENGLISH -> EnglishTagger()
        else -> throw IllegalArgumentException("LanguageToolLemmatizer does not support the $language language")
    }

    override fun lemmatize(source: Reader, dictionary: Set<String>): Map<String, Int> {

        val words = splitTextToWords(source)
        val wordsFrequency = mutableMapOf<String, Int>()

        for (word in words) {

            val lowerCaseWord = word.toLowerCase()
            if (dictionary.contains(lowerCaseWord)) {
                wordsFrequency.merge(lowerCaseWord, 1, Int::plus)
                continue
            }

            val lemma = lemmatizeWord(word).find { dictionary.contains(it) }

            if (lemma != null) {
                wordsFrequency.merge(lemma, 1, Int::plus)
            }
        }

        return wordsFrequency
    }

    private fun splitTextToWords(source: Reader): List<String> {
        return source.readText().split(Regex("\\P{L}"))
    }

    private fun lemmatizeWord(word: String): List<String> {
        return tagger.tag(listOf(word))[0].mapNotNull { it.lemma?.toLowerCase() }.distinct()
    }
}