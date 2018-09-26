package com.nooblabs.srawa.textconverter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapters()
        initListeners()
    }

    private fun initAdapters() {
        ArrayAdapter.createFromResource(
                this,
                R.array.operations_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            outputOptions.adapter = adapter
        }
    }

    private fun initListeners(){
        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.let { newText ->
                    val transformedText = transformText(newText)
                    outputText.text = transformedText
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })
        outputOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val inputTextString = inputText.text.toString()
                val outputTextString = transformText(inputTextString)
                outputText.setText(outputTextString)
            }
        }
    }

    private fun transformText(text: String): String {
        val operation = getOperation()
        return operation.transform(text)
    }

    private fun getOperation(): Operation {
        val selectedOperationIndex = outputOptions.selectedItemPosition
        return when(selectedOperationIndex) {
            0 -> UpperCaseOperation()
            1 -> LowerCaseOperation()
            else -> throw IllegalArgumentException("invalid operation selected: $selectedOperationIndex")
        }
    }
}
