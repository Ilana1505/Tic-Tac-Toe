package com.example.tictactoeclassproject

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentTurn = "X"  // שחקן בתור הנוכחי
    private var board = Array(3) { Array(3) { "" } }  // הלוח של המשחק
    private lateinit var gameStatusText: TextView  // טקסט הסטטוס של המשחק
    private lateinit var restartGameButton: Button  // כפתור התחלת משחק מחדש

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStatusText = findViewById(R.id.statusText)
        restartGameButton = findViewById(R.id.playAgainButton)

        // פעולה עבור כפתור "שחק שוב"
        restartGameButton.setOnClickListener { onRestartGameClick() }

        // תחילת משחק עם שחקן X
        gameStatusText.text = "Player X's turn"
        restartGameButton.visibility = View.GONE
    }

    fun onCellClick(view: View) {
        val button = view as Button
        val tag = button.tag.toString().toInt()

        val row = tag / 3
        val col = tag % 3

        // אם התאים ריקים והמשחק לא נגמר
        if (board[row][col] == "" && !isGameOver()) {
            // עדכון לוח המשחק
            board[row][col] = currentTurn
            button.text = currentTurn

            // בדיקת מנצח
            if (checkWinner()) {
                gameStatusText.text = "Player $currentTurn wins!"
                restartGameButton.visibility = View.VISIBLE
            }
            // בדיקת תיקו
            else if (isBoardFull()) {
                gameStatusText.text = "It's a draw!"
                restartGameButton.visibility = View.VISIBLE
            } else {
                // שינוי תור השחקן
                currentTurn = if (currentTurn == "X") "O" else "X"
                gameStatusText.text = "Player $currentTurn's turn"
            }
        }
    }

    private fun checkWinner(): Boolean {
        // בדיקת מנצח (שורות, עמודות, אלכסונים)
        for (i in 0..2) {
            if (board[i][0] == currentTurn && board[i][1] == currentTurn && board[i][2] == currentTurn) return true
            if (board[0][i] == currentTurn && board[1][i] == currentTurn && board[2][i] == currentTurn) return true
        }
        if (board[0][0] == currentTurn && board[1][1] == currentTurn && board[2][2] == currentTurn) return true
        if (board[0][2] == currentTurn && board[1][1] == currentTurn && board[2][0] == currentTurn) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        // בדיקת תיקו
        for (row in board) {
            for (cell in row) {
                if (cell == "") return false
            }
        }
        return true
    }

    private fun onRestartGameClick() {
        // איפוס המשחק
        board = Array(3) { Array(3) { "" } }
        currentTurn = "X"
        gameStatusText.text = "Player $currentTurn's turn"
        restartGameButton.visibility = View.GONE

        // ניקוי כל הכפתורים בלוח
        for (i in 0..8) {
            val button = findViewById<Button>(resources.getIdentifier("button$i", "id", packageName))
            button.text = ""
        }
    }

    private fun isGameOver(): Boolean {
        // בדיקת מצב סיום המשחק
        return checkWinner() || isBoardFull()
    }
}
