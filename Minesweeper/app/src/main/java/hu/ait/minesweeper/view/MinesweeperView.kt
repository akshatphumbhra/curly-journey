package hu.ait.minesweeper.view

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.Cell
import hu.ait.minesweeper.model.MinesweeperBoard
import android.R.attr.right

import android.R.attr.left

import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import hu.ait.minesweeper.GameActivity


class MinesweeperView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBg: Paint
    lateinit var paintRevealed: Paint
    lateinit var paintLine: Paint

    init {
        paintBg = Paint()
        paintBg.color = Color.BLACK
        paintBg.style = Paint.Style.FILL

        paintRevealed = Paint()
        paintRevealed.color = Color.rgb(124, 124, 124)
        paintRevealed.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.color = Color.BLACK
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        MinesweeperBoard.generateMinefield()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        MinesweeperBoard.width = width.toFloat()
        MinesweeperBoard.height = height.toFloat()
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBg)
        // Start game timer
        if (!MinesweeperBoard.isOver) {
            (context as GameActivity).startTimer()
        }

        drawGameArea(canvas!!)
        updateMinefield(canvas!!)

        if (MinesweeperBoard.isOver && !MinesweeperBoard.hasWon) {
            revealAllMines(canvas)
        }
    }

    private fun drawImage(i:Int, j:Int, imageId:Int, canvas:Canvas) {
        val bMap = BitmapFactory.decodeResource(
            context?.resources,
            imageId
        )
        val d: Drawable = BitmapDrawable(bMap)
        d.setBounds(
            MinesweeperBoard.getLeftCoord(i, j).toInt(),
            MinesweeperBoard.getTopCoord(i, j).toInt(),
            MinesweeperBoard.getRightCoord(i, j).toInt(),
            MinesweeperBoard.getBottomCoord(i, j).toInt()
        )
        d.draw(canvas)

    }

    private fun updateMinefield(canvas: Canvas){
        val s = MinesweeperBoard.size
        for (i in 0..(s-1)){
            for (j in 0..(s-1)){
                if (MinesweeperBoard.getIsClicked(i, j)) {
                    if (MinesweeperBoard.getType(i, j) == MinesweeperBoard.EMPTY) {
                        canvas.drawRect(
                            MinesweeperBoard.getLeftCoord(i, j),
                            MinesweeperBoard.getTopCoord(i,j),
                            MinesweeperBoard.getRightCoord(i, j),
                            MinesweeperBoard.getBottomCoord(i, j),
                            paintRevealed)
                    } else if (MinesweeperBoard.getType(i, j) == MinesweeperBoard.NUMBER) {
                        var num = MinesweeperBoard.getNumMines(i, j)
                        var imageId = 0
                        when (num) {
                            1 -> imageId = R.drawable.one
                            2 -> imageId = R.drawable.two
                            3 -> imageId = R.drawable.three
                            4 -> imageId = R.drawable.four
                            5 -> imageId = R.drawable.five
                            6 -> imageId = R.drawable.six
                            7 -> imageId = R.drawable.seven
                            8 -> imageId = R.drawable.eight

                            else -> {
                                imageId = R.drawable.hidden
                            }
                        }
                        drawImage(i, j, imageId, canvas)
                    } else if (MinesweeperBoard.getType(i, j) == MinesweeperBoard.MINE){
                        drawImage(i, j, R.drawable.mine_hit, canvas)
                    }
                } else if (MinesweeperBoard.getIsFlagged(i, j)){
                    drawImage(i, j, R.drawable.flag, canvas)
                } else {
                    drawImage(i, j, R.drawable.hidden, canvas)
                }
            }
        }
    }

    private fun drawGameArea(canvas: Canvas) {
        for (i in 1..(MinesweeperBoard.size-1)) {
            canvas.drawLine(
                0f, i*(height / MinesweeperBoard.size).toFloat(), width.toFloat(), i*(height / MinesweeperBoard.size).toFloat(),
                paintLine
            )
            canvas.drawLine(
                i*(width.toFloat()/MinesweeperBoard.size), 0f, i*(width.toFloat()/MinesweeperBoard.size), height.toFloat(),
                paintLine
            )

        }

        for (i in 0..(MinesweeperBoard.size-1)) {
            for (j in 0..(MinesweeperBoard.size - 1)) {
                drawImage(i, j, R.drawable.hidden, canvas)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val s = MinesweeperBoard.size
        if (event?.action == MotionEvent.ACTION_DOWN && !MinesweeperBoard.isOver) {

            val tX = event.x.toInt() / (width / s)
            val tY = event.y.toInt() / (height / s)

            if (MinesweeperBoard.isFlagMode) {
                if (tX < s && tY < s) {
                    if (MinesweeperBoard.getType(tX, tY) != MinesweeperBoard.MINE) {
                        MinesweeperBoard.toggleIsFlagged(tX, tY)
                        gameLost()
                        invalidate()
                    } else {
                        if (!MinesweeperBoard.getIsFlagged(tX, tY)) {
                            MinesweeperBoard.minesFound++
                            (context as GameActivity).showTextMessage((MinesweeperBoard.mines - MinesweeperBoard.minesFound).toString())
                            MinesweeperBoard.toggleIsFlagged(tX, tY)
                            if (MinesweeperBoard.minesFound == MinesweeperBoard.mines) {
                                gameWon()
                                //invalidate()
                            }
                            invalidate()
                        } else {
                            MinesweeperBoard.toggleIsFlagged(tX, tY)
                            MinesweeperBoard.minesFound--
                            (context as GameActivity).showTextMessage((MinesweeperBoard.mines - MinesweeperBoard.minesFound).toString())
                            invalidate()
                        }

                    }
                }
            } else {
                if (tX < s && tY < s && !MinesweeperBoard.getIsFlagged(tX, tY)) {
                    if (MinesweeperBoard.getType(tX, tY) == MinesweeperBoard.EMPTY){
                        MinesweeperBoard.revealAllEmpty(tX, tY)
                    } else if (MinesweeperBoard.getType(tX, tY) == MinesweeperBoard.MINE) {
                        MinesweeperBoard.click(tX, tY)
                        gameLost()
                    }
                    MinesweeperBoard.click(tX, tY)
                    invalidate()
                }
            }
        }

        return true
    }

    fun resetGame() {
        MinesweeperBoard.resetModel()
        MinesweeperBoard.generateMinefield()
        MinesweeperBoard.hasWon = false
        MinesweeperBoard.isOver = false
        (context as GameActivity).resetTimer()
        (context as GameActivity).showTextMessage(MinesweeperBoard.mines.toString())

        invalidate()
    }

    fun gameLost(){
        MinesweeperBoard.isOver = true
        (context as GameActivity).stopTimer()
        (context as GameActivity).showMessage(context.getString(R.string.lose_message))
    }

    fun gameWon() {
        MinesweeperBoard.isOver = true
        MinesweeperBoard.hasWon = true
        (context as GameActivity).stopTimer()
        (context as GameActivity).showMessage(context.getString(R.string.win_message))

    }

    fun revealAllMines(canvas:Canvas) {
        for (i in 0..(MinesweeperBoard.size -1)) {
            for (j in 0..(MinesweeperBoard.size - 1)) {
                if (MinesweeperBoard.getType(i, j) == MinesweeperBoard.MINE && !MinesweeperBoard.getIsClicked(i, j)){
                    drawImage(i, j, R.drawable.mine, canvas)
                }
            }
        }
    }
}