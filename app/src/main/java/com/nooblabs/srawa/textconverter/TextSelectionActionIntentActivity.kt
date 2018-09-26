package com.nooblabs.srawa.textconverter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class TextSelectionActionIntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent()
    }

    private fun handleIntent(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(intent != null ) {
                val text: CharSequence? = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
                text?.toString()?.let { data ->
                    val operation = getDefaultOperation()
                    val newData = operation.transform(data)
                    Toast.makeText(this, newData, Toast.LENGTH_LONG).show()
                    if (!intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)) {
                        Log.d("debug", "Found editable source")
                        Log.d("debug", "Editing source to $newData from $data")
                        Intent().let { resultIntent ->
                            resultIntent.putExtra(Intent.EXTRA_PROCESS_TEXT, newData)
                            setResult(Activity.RESULT_OK, resultIntent)
                        }
                    }
                }
            }
        } else {
            Log.d("debug","VERSION.SDK_INT < M")
        }
        finish()
    }

}
