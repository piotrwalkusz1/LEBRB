package com.piotrwalkusz.lebrb.lanlearn

import java.io.Reader

/*
    Basic terms:
    - lemma form - the lowercase lemma of word consisting of only letters, a user doesn't see this form,
                   only this form can be translated (e.g. hund; eat)
    - representation form - the form of a foreign language word that user sees (eg. der Hund; essen / aß / gegessen)
    - translated form - possible translation of words (e.g. dog; to eat, to dine)
    
    A Translation Dictionary file consists of the header line describing source and destination language:
    <source language>;<destination language>

    followed by lines in format:
    <lemma form>;<representation form>;<translated form>

    for example:
    german;polish
    hund;der Hund;dog
    essen;essen / aß / gegessen;to eat, to dine
*/

class TranslationDictionary(val sourceLanguage: Language,
                  val destinationLanguage: Language,
                  private val translations: Map<String, Pair<String, String>>) {

    companion object {

        fun createFromReader(reader: Reader): TranslationDictionary {

            val lines = reader.readLines()
            if (lines.isEmpty())
                throw IllegalArgumentException("Translation Dictionary file is empty")

            val headerLine = lines[0].split(';')
            if (headerLine.size != 2)
                throw IllegalArgumentException("Translation Dictionary has to have the header line in format " +
                        "'<source language>;<destination language>'")

            val languages = headerLine.map { it to Language.valueOfIgnoreCase(it) }
            val nonExistingLanguage = languages.find { it.second == null }
            if (nonExistingLanguage != null)
                throw IllegalArgumentException("Language ${nonExistingLanguage.first} in Translation Dictionary file " +
                        "does not exist")

            val translations = reader.readLines().drop(1).map {
                val forms = it.split(';')
                if (forms.size != 3) {
                    throw IllegalArgumentException("Lines in Translation Dictionary has to be in format " +
                            "<lemma form>;<representation form>;<translated form>. Invalid line '$it' was founded.")
                }
                Pair(forms[0], Pair(forms[1], forms[2]))
            }.toMap()

            return TranslationDictionary(languages[0].second!!, languages[1].second!!, translations)
        }
    }

    fun getTranslatableWords(): Set<String> {
        return translations.keys
    }

    fun getRepresentation(word: String): String? {
        return translations[word]?.first
    }

    fun translate(word: String): String? {
        return translations[word]?.second
    }
}