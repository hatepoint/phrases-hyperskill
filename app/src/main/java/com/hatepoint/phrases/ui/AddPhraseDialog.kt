package com.hatepoint.phrases.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.hatepoint.phrases.R

class AddPhraseDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_phrase, null)
        return AlertDialog.Builder(activity)
            .setTitle("Add phrase")
            .setView(R.layout.dialog_add_phrase)
            .setPositiveButton("Add") { dialog, which ->
                parentFragmentManager.setFragmentResult(REQUEST_KEY, Bundle().apply { putString("phrase", (dialog as AlertDialog).findViewById<EditText>(
                    R.id.editText
                ).text.toString()) })
            }
            .setNegativeButton("Cancel") { dialog, which ->
            }
            .create()
    }

    companion object {
        const val REQUEST_KEY = "addPhrase"
    }
}