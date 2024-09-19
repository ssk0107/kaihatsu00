package com.example.kaihatsu00

import DBHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var tvUsers: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Snackbarの設定
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // エッジツーエッジの設定
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 現在の日付を取得して表示
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        val currentDate = getCurrentDate()
        dateTextView.text = currentDate






        // DBHelper インスタンスを作成
        dbHelper = DBHelper(this)

        // TextView や Button を取得
        tvUsers = findViewById(R.id.tvUsers)
        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val btnShowUsers = findViewById<Button>(R.id.btnShowUsers)

        // ユーザーを挿入する処理
        btnInsert.setOnClickListener {
            dbHelper.insertUser("John Doe", 25)
        }

        // ユーザー情報を表示する処理
        btnShowUsers.setOnClickListener {
            val cursor = dbHelper.getAllUsers()
            val users = StringBuilder()

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val age = cursor.getInt(2)

                    // ユーザー情報をログに表示
                    Log.d("MainActivity", "ID: $id, Name: $name, Age: $age")

                    // ユーザー情報を users に追加
                    users.append("ID: $id, Name: $name, Age: $age\n")
                } while (cursor.moveToNext())
            }
            cursor.close()

            // TextViewにユーザー情報を表示
            tvUsers.text = users.toString()
        }
    }

    // 現在の日付をフォーマットする関数
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
