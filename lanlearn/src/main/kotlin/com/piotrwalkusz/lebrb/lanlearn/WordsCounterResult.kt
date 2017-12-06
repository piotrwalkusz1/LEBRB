package com.piotrwalkusz.lebrb.lanlearn

class WordsCounterResult(val wordsFrequency: Map<String, Int>) {

    fun getWords(): Set<String> {
        return wordsFrequency.keys
    }
}