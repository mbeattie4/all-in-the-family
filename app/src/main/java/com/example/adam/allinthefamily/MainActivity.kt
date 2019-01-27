package com.example.adam.allinthefamily

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameLogo: ImageView
    private lateinit var enterNameEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var playButton: Button

    private val names: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameLogo = findViewById(R.id.game_logo)
        enterNameEditText = findViewById(R.id.name_edit_text)
        submitButton = findViewById(R.id.submit_button)
        playButton = findViewById(R.id.play_button)

        setupActions()
    }

    override fun onResume() {
        super.onResume()
        rotateLogo()
    }

    private fun setupActions() {
        enterNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                submitButton.isEnabled = (text.length > 1)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        with(submitButton) {
            isEnabled = false

            setOnClickListener { view ->
                names.add(enterNameEditText.text.toString())
                enterNameEditText.setText("")

                rotateLogo()
                updatePlayButton()

                Toast.makeText(
                        view.context,
                        "Great job! Please pass to the next person.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }

        playButton.setOnClickListener { view ->
            Intent(view.context, GameActivity::class.java).apply {
                putStringArrayListExtra(GameActivity.NAMES_INTENT_EXTRA_KEY, ArrayList(names))
            }.also {
                startActivity(it)
            }

            names.clear()
            updatePlayButton()
        }

        updatePlayButton()
    }

    private fun updatePlayButton() {
        playButton.isEnabled = (names.size >= MINIMUM_NUM_NAMES)
    }

    private fun rotateLogo() {
        val animation = RotateAnimation(
                0f,
                -720f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 2500L
            repeatCount = 0
            interpolator = AccelerateDecelerateInterpolator()
        }
        gameLogo.startAnimation(animation)
    }

    private companion object {
        const val MINIMUM_NUM_NAMES = 3
    }
}
