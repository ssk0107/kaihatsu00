package com.example.kaihatsu00

import DBHelper
import User
import UserAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var recyclerViewUsers: RecyclerView
    lateinit var btnShowUsers: Button

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

        // RecyclerView を取得
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)

        // ボタンを取得
        btnShowUsers = findViewById(R.id.btnShowUsers)

        // ユーザー情報を表示する処理
        btnShowUsers.setOnClickListener {
            val cursor = dbHelper.getAllUsers()
            val users = mutableListOf<User>()

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val age = cursor.getInt(2)

                    // ユーザー情報をリストに追加
                    users.add(User(id, name, age))
                } while (cursor.moveToNext())
            }
            cursor.close()

            // RecyclerViewにユーザー情報を表示
            val adapter = UserAdapter(users) { user ->
                // ユーザーの削除処理
                dbHelper.deleteUser(user.id)
                // 更新後のユーザーリストを再取得
                btnShowUsers.performClick()
            }
            recyclerViewUsers.layoutManager = LinearLayoutManager(this)
            recyclerViewUsers.adapter = adapter
        }
    }

    // 現在の日付をフォーマットする関数
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
