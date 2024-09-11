package com.example.kaihatsu00

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 以下を追加
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()



        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        // TextViewの参照を取得
        val dateTextView: TextView = findViewById(R.id.dateTextView)

        // 現在の日付を取得してフォーマット
        val currentDate = getCurrentDate()

        // TextViewに日付を設定
        dateTextView.text = currentDate
    }
    // 現在の日付をフォーマットする関数
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd ", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }


}

    private fun getCurrentDate(): CharSequence? {

    }

 