package com.ullo.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import com.ullo.R

class CheckBox : LinearLayout {

    private lateinit var checkBox: CheckBox

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, android.R.attr.textViewStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.checkbox, this)
        checkBox = findViewById(R.id.checkbox)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBox, defStyleAttr, 0)

        try {
            if (typedArray.hasValue(R.styleable.CheckBox_android_text)) {
                val str = typedArray.getString(R.styleable.CheckBox_android_text)
                checkBox.text = str
            }
            if (typedArray.hasValue(R.styleable.CheckBox_android_textColor)) {
                val color = typedArray.getColor(R.styleable.CheckBox_android_textColor, 0)
                checkBox.setTextColor(color)
            }

            if (typedArray.hasValue(R.styleable.CheckBox_android_textSize)) {
                val textSize = typedArray.getDimension(R.styleable.CheckBox_android_textSize, 0f)
                val density = resources.displayMetrics.density
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize / density)
            }

            if (typedArray.hasValue(R.styleable.CheckBox_android_button)) {
                val button = typedArray.getDrawable(R.styleable.CheckBox_android_button)
                checkBox.buttonDrawable = button
            }

            if (typedArray.hasValue(R.styleable.CheckBox_paddingLeft)) {
                val paddingLeft = typedArray.getDimension(R.styleable.CheckBox_paddingLeft, 0f)
                checkBox.setPadding(paddingLeft.toInt(), 0, 0, 0)
            }

            if (typedArray.hasValue(R.styleable.CheckBox_paddingRight)) {
                val paddingRight = typedArray.getDimension(R.styleable.CheckBox_paddingRight, 0f)
                checkBox.setPadding(paddingLeft, 0, paddingRight.toInt(), 0)
            }

        } finally {
            typedArray.recycle()
        }

    }

    fun isChecked(): Boolean {
        return checkBox.isChecked
    }
}
