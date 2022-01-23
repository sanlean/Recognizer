package com.example.sdk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class LoadingButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {

    val button: Button
    val loading: ProgressBar

    var text: String?

    init{
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_loading_button, this, true)
        button = findViewById(R.id.btnLoading)
        loading = findViewById(R.id.pbLoading)
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.LoadingButton, 0, 0)
        text = typedArray.getString(R.styleable.LoadingButton_android_text)
        button.text = text
        loading.isVisible = false
    }

    fun setClickListener(clickListener: () -> Unit) {
        button.setOnClickListener {
            clickListener()
        }
    }

    fun showLoading() {
        button.isEnabled = false
        button.text = null
        loading.isVisible = true
    }

    fun hideLoading() {
        button.isEnabled = true
        button.text = text
        loading.isVisible = false
    }

}