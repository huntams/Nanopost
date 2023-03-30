package com.example.homework2

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.homework2.databinding.ProfileViewCardBinding

class ProfileViewCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrsSet: Int = 0
) : ConstraintLayout(context, attributeSet, defAttrsSet) {
    private lateinit var binding : ProfileViewCardBinding

    init {
        binding = ProfileViewCardBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        context.withStyledAttributes(
            attributeSet,
            R.styleable.ProfileViewCard, defAttrsSet, 0
        ) {
            val title = getString(R.styleable.ProfileViewCard_title)?.let {
                    title ->binding.name.text = title
            }
            val subtitle = getString(R.styleable.ProfileViewCard_date)?.let {
                    subtitle -> binding.date.text = subtitle
            }
            val image = getDrawable(R.styleable.ProfileViewCard_image)?.let {
                    image -> binding.image.setImageDrawable(image)
            }
            val imageNumber = getString(R.styleable.ProfileViewCard_imageNumber)?.let {
                    imageNumber -> binding.date.text = imageNumber
            }
            val subscriberNumber = getString(R.styleable.ProfileViewCard_subscriberNumber)?.let {
                    subscriberNumber -> binding.date.text = subscriberNumber
            }
            val postNumber = getString(R.styleable.ProfileViewCard_postNumber)?.let {
                    postNumber -> binding.date.text = postNumber
            }
            val button = getColor(R.styleable.ProfileViewCard_button, Color.BLACK)?.let{
                    button -> binding.button.setBackgroundColor(button)
            }
        }
    }

    fun setTitle(text: String) {
        binding.name.text = text
    }

    fun setDate(text: String) {
        binding.date.text = text
    }

    fun setImage(@DrawableRes imagesRes: Int) {
        binding.image.setImageDrawable(
            ContextCompat.getDrawable(context, imagesRes)
        )
    }
    fun setImageNumber(text: String) {
        binding.imageNumber.text = text
    }
    fun setSubsNumber(text: String) {
        binding.postNumber.text = text
    }
    fun setPostNumber(text: String) {
        binding.subscriberNumber.text = text
    }
    fun setButtonColor(@ColorInt imagesRes: Int){
        binding.button.setBackgroundColor(imagesRes)
    }
}