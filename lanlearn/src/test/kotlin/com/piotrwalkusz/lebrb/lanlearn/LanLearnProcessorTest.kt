package com.piotrwalkusz.lebrb.lanlearn

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LanLearnProcessorTest {

    private val dictionary = TranslationDictionary(Language.GERMAN, Language.ENGLISH, mapOf(
            "hund" to Pair("der Hund", "dog"),
            "essen" to Pair("essen / aß / gegessen", "eat"),
            "katze" to Pair("die Katze", "cat"),
            "werfen" to Pair("werfen / warf / geworfen", "throw"),
            "wordexistingonlyinthisdictionary" to Pair("lemmatizer will not recognize this word",
                    "because it is not correct german word")
    ))

    private val processor = LanLearnProcessor()

    @Test
    fun `find and translate words existing in dictionary`() {
        val text = "Hund Katze essen werfen wordexistingonlyinthisdictionary".byteInputStream()

        val wordsAndTranslations = processor.translateWordsInText(text, MediaType.PLAIN_TEXT, dictionary)

        assertEquals(5, wordsAndTranslations.size)
        assertEquals("dog", wordsAndTranslations["der Hund"])
        assertEquals("eat", wordsAndTranslations["essen / aß / gegessen"])
        assertEquals("cat", wordsAndTranslations["die Katze"])
        assertEquals("throw", wordsAndTranslations["werfen / warf / geworfen"])
        assertEquals("because it is not correct german word",
                wordsAndTranslations["lemmatizer will not recognize this word"])
    }

    @Test
    fun `return only words existing in dictionary`() {
        val text = "spielen Kopf regen".byteInputStream()

        val wordsAndTranslations = processor.translateWordsInText(text, MediaType.PLAIN_TEXT, dictionary)

        assertEquals(0, wordsAndTranslations.size)
    }

    @Test
    fun `return words if their lemma is in dictionary`() {
        val text = "gegessen Hunde warf".byteInputStream()

        val wordsAndTranslations = processor.translateWordsInText(text, MediaType.PLAIN_TEXT, dictionary)

        assertEquals(3, wordsAndTranslations.size)
        assertEquals("dog", wordsAndTranslations["der Hund"])
        assertEquals("eat", wordsAndTranslations["essen / aß / gegessen"])
        assertEquals("throw", wordsAndTranslations["werfen / warf / geworfen"])
    }
}