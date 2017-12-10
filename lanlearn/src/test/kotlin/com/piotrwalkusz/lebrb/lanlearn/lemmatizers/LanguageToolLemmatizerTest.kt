package com.piotrwalkusz.lebrb.lanlearn.lemmatizers

import com.piotrwalkusz.lebrb.lanlearn.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.StringReader

internal class LanguageToolLemmatizerTest {

    val germanLemmatizer = LanguageToolLemmatizer(Language.GERMAN)
    val germanDictionary = setOf("hund", "katze", "ich", "essen", "fahren")
    val englishLemmatizer = LanguageToolLemmatizer(Language.ENGLISH)


    // -------------------- German --------------------

    @Test
    fun `german - lemmatize unique words`() {
        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("Hund Katze fahren"), germanDictionary)

        assertEquals(3, result.size)
        assertEquals(1, result["hund"])
        assertEquals(1, result["katze"])
        assertEquals(1, result["fahren"])
    }

    @Test
    fun `german - lemmatize duplicated words`() {
        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("Hund essen Hund Katze essen essen"), germanDictionary)

        assertEquals(3, result.size)
        assertEquals(2, result["hund"])
        assertEquals(1, result["katze"])
        assertEquals(3, result["essen"])
    }

    @Test
    fun `german - lemmatize words in non basic form`() {
        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("essen aß gegessen"), germanDictionary)

        assertEquals(1, result.size)
        assertEquals(3, result["essen"])
    }

    @Test
    fun `german - ignore letter case`() {
        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("hund Hund HUND"), germanDictionary)

        assertEquals(1, result.size)
        assertEquals(3, result["hund"])
    }

    // -------------------- Common --------------------

    @Test
    fun `always lemmatize words in dictionary`() {
        val dictionary = setOf("aaa", "abc")

        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("aaa AAA ABC aBc"), dictionary)

        assertEquals(2, result.size)
        assertEquals(2, result["aaa"])
        assertEquals(2, result["abc"])
    }

    @Test
    fun `do not include words nonexistent in dictionary`() {
        val dictionary = setOf("hund", "katze")

        val result: Map<String, Int> = germanLemmatizer.lemmatize(StringReader("katze abc hund essen"), dictionary)

        assertEquals(2, result.size)
        assertEquals(1, result["katze"])
        assertEquals(1, result["hund"])
    }
}