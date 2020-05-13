package com.ullo.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ullo.R
import com.ullo.extensions.gone
import com.ullo.extensions.visible

class SettingTextView : LinearLayout {

    private lateinit var textView: TextView
    private lateinit var leftIcon: ImageView
    private lateinit var rightIcon: ImageView

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, android.R.attr.textStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.setting_textview, this)
        textView = findViewById(R.id.txtView)
        leftIcon = findViewById(R.id.left_icon)
        rightIcon = findViewById(R.id.right_icon)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TextView, defStyleAttr, 0)

        try {
            if (typedArray.hasValue(R.styleable.TextView_android_text)) {
                val str = typedArray.getString(R.styleable.TextView_android_text)
                textView.text = str
            }
            if (typedArray.hasValue(R.styleable.TextView_android_textSize)) {
                val textSize = typedArray.getDimension(R.styleable.TextView_android_textSize, 0f)
                val dimensions = resources.displayMetrics.density
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize / dimensions)
            }
            if (typedArray.hasValue(R.styleable.TextView_android_textColor)) {
                val str = typedArray.getColor(R.styleable.TextView_android_textColor, 0)
                textView.setTextColor(str)
            }
            if (typedArray.hasValue(R.styleable.TextView_android_gravity)) {
                val gravity = typedArray.getInt(R.styleable.TextView_android_gravity, 0)
                textView.gravity = gravity
            }

            if (typedArray.hasValue(R.styleable.TextView_leftTDrawable)) {
                val leftDrawable = typedArray.getDrawable(R.styleable.TextView_leftTDrawable)
                leftIcon.setImageDrawable(leftDrawable)
                leftIcon.visible()
            } else {
                leftIcon.gone()
            }

            if (typedArray.hasValue(R.styleable.TextView_rightTDrawable)) {
                val leftDrawable = typedArray.getDrawable(R.styleable.TextView_rightTDrawable)
                rightIcon.setImageDrawable(leftDrawable)
                rightIcon.visible()
            } else {
                rightIcon.gone()
            }
        } finally {
            typedArray.recycle()
        }
    }
}
