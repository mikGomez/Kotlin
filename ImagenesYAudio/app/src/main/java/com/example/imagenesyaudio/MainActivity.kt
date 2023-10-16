package com.example.imagenesyaudio

import android.app.Activity
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imagenesyaudio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this,R.raw.pika)

        binding.imageButton.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas2)
            mediaPlayer.start()
        }
        binding.imageButton2.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas)
            mediaPlayer.stop()
        }
        binding.imageView2.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas2)
        }

    }
}