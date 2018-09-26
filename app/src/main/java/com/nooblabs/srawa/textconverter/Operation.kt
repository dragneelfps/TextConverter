package com.nooblabs.srawa.textconverter

sealed class Operation {
    abstract fun transform(text: String): String
}

class UpperCaseOperation: Operation() {
    override fun transform(text: String): String {
        return text.toUpperCase()
    }
}

class LowerCaseOperation: Operation() {
    override fun transform(text: String): String {
        return text.toLowerCase()
    }
}

