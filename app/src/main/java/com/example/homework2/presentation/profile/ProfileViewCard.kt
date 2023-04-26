package com.example.homework2.presentation.profile

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.example.homework2.R
import com.example.homework2.databinding.ProfileViewCardBinding


class ProfileViewCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrsSet: Int = 0
) : ConstraintLayout(context, attributeSet, defAttrsSet) {
    private lateinit var binding: ProfileViewCardBinding

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
            val title = getString(R.styleable.ProfileViewCard_title)?.let { title ->
                binding.textViewName.text = title
            }
            val subtitle = getString(R.styleable.ProfileViewCard_date)?.let { subtitle ->
                binding.textViewDate.text = subtitle
            }
            val image = getDrawable(R.styleable.ProfileViewCard_image)?.let { image ->
                binding.imageViewRounded.setImageDrawable(image)
            }
            val imageNumber =
                getString(R.styleable.ProfileViewCard_imageNumber)?.let { imageNumber ->
                    binding.textViewImageNumber.text = imageNumber
                }
            val subscriberNumber =
                getString(R.styleable.ProfileViewCard_subscriberNumber)?.let { subscriberNumber ->
                    binding.textViewSubscriberNumber.text = subscriberNumber
                }
            val postNumber = getString(R.styleable.ProfileViewCard_postNumber)?.let { postNumber ->
                binding.textViewPostNumber.text = postNumber
            }
            val button = getColor(R.styleable.ProfileViewCard_button, Color.BLACK)?.let { button ->
                binding.buttonSubscribe.setBackgroundColor(button)
            }
        }
    }

    fun setTitle(text: String) {
        binding.textViewName.text = text
    }

    fun setDate(text: String) {
        binding.textViewDate.text = text
    }

    fun setImage(@DrawableRes imagesRes: Int) {
        binding.imageViewRounded.setImageDrawable(
            ContextCompat.getDrawable(context, imagesRes)
        )
    }

    fun setImageNumber(text: String) {
        binding.textViewImageNumber.text = text
    }

    fun setSubsNumber(text: String) {
        binding.textViewPostNumber.text = text
    }

    fun setPostNumber(text: String) {
        binding.textViewSubscriberNumber.text = text
    }

    fun setButtonColor(@ColorInt imagesRes: Int) {
        binding.buttonSubscribe.setBackgroundColor(imagesRes)
    }

}
