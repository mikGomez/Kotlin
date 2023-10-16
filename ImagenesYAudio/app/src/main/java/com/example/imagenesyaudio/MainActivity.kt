package com.example.imagenesyaudio

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imagenesyaudio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButton.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas2)
        }
        binding.imageButton2.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas)
        }
        binding.imageView2.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.ic_cositas2)
        }
    }
}