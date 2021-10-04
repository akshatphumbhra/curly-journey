package hu.ait.tictactoeandroid.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import hu.ait.tictactoeandroid.view.TicTacToeView

object TicTacToeModel {

    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2
    public val TIE: Short = 3
    public var result: Short = 0
    lateinit var paintFinish: Paint

    init{
        paintFinish = Paint()
        paintFinish.color = Color.GREEN
        paintFinish.style = Paint.Style.STROKE
        paintFinish.strokeWidth = 5f
    }

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY))

    private var nextPlayer = CROSS

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CROSS
        result = 0
    }

    fun drawFinishLine(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float) {
        canvas.drawLine(
            y2, x2, y1, x1,
            paintFinish
        )
    }

    fun gameOver(canvas: Canvas, height: Float, width: Float): Boolean {
        var isOver = false
        // Vertical Lines
        if (model[0][0] == model[0][1] && model[0][1] == model[0][2] && model[0][0] != EMPTY) {
            drawFinishLine(canvas,
                0f,
                height/6,
                width,
                height/6
            )
            result = model[0][0]
            isOver = true
            return true
        } else if (model[1][0] == model[1][1] && model[1][1] == model[1][2] && model[1][0] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                0f,
                height/2,
                width,
                height/2
            )
            result = model[1][0]
            return true
        } else if (model[2][0] == model[2][1] && model[2][1] == model[2][2] && model[2][0] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                0f,
                5*height/6,
                width,
                5*height/6
            )
            result = model[2][0]
            return true
        }

        // Horizontal Lines
        if (model[0][0] == model[1][0] && model[1][0] == model[2][0] && model[0][0] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                width/6,
                0f,
                width/6,
                height
            )
            result = model[0][0]
            return true
        } else if (model[0][1] == model[1][1] && model[1][1] == model[2][1] && model[0][1] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                width/2,
                0f,
                width/2,
                height
            )
            result = model[0][1]
            return true
        } else if (model[0][2] == model[1][2] && model[1][2] == model[2][2] && model[0][2] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                5*width/6,
                0f,
                5*width/6,
                height
            )
            result = model[0][2]
            return true
        }

        // Diagonals
        if (model[0][0] == model[1][1] && model[1][1] == model[2][2] && model[0][0] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                0f,
                0f,
                width,
                height
            )
            result = model[0][0]
            return true
        } else if (model[0][2] == model[1][1] && model[1][1] == model[2][0] && model[0][2] != EMPTY) {
            isOver = true
            drawFinishLine(canvas,
                width,
                0f,
                0f,
                height
            )
            result = model[0][2]
            return true
        }

        var tie = true
        for (i in 0..2) {
            for (j in 0..2) {
                if (model[i][j] == EMPTY) {
                    tie = false
                }
            }
        }

        if (tie) {
            result = 3
            isOver = true
            return true
        }
        return false
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short) {
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }
}