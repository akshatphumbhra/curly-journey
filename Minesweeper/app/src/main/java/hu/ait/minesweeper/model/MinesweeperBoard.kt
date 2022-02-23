package hu.ait.minesweeper.model

import kotlin.collections.List
import kotlin.random.Random

object MinesweeperBoard {

    public val EMPTY: Short = 0
    public val MINE: Short = 1
    public val NUMBER: Short = 2

    public var minefield = arrayOf<Array<Cell>>()
    public var size = 0
    public var mines = 0
    public var isFlagMode = false
    public var width = 0f
    public var height = 0f
    public var minesFound = 0
    public var isOver = false
    public var hasWon = false

    fun getIsFlagged(x:Int, y:Int) = minefield[x][y].isFlagged

    fun getIsClicked(x:Int, y:Int) = minefield[x][y].isClicked

    fun toggleIsFlagged(x:Int, y:Int) {
        minefield[x][y].isFlagged = !minefield[x][y].isFlagged
    }

    fun getNumMines(x:Int, y:Int) = minefield[x][y].numMines

    fun click(x:Int, y:Int) {
        minefield[x][y].isClicked = true
    }

    fun setDifficulty(gridSize: Int, numMines: Int) {
        size = gridSize
        mines = numMines
        minefield = Array(size) { row ->
            Array(size) { col ->
                Cell()
            }
        }

    }

    fun revealAllEmpty(x:Int, y:Int) {
        var neighbours = getNeighbours(x, y)
        for (neighbour in neighbours) {
            neighbour.isClicked = true
        }
    }

    fun getType(x:Int, y:Int) = minefield[x][y].type

    fun getLeftCoord(i:Int, j:Int): Float {
        return ((i * width / size).toFloat() + 2.5f)
    }

    fun getTopCoord(i:Int, j:Int): Float {
        return ((j * height / size).toFloat() + 2.5f)
    }

    fun getRightCoord(i:Int, j:Int): Float {
        return ((i * width / size).toFloat() + width.toFloat()/size - 2.5f)
    }

    fun getBottomCoord(i:Int, j:Int): Float {
        return ((j * height / size).toFloat() + height.toFloat()/size - 2.5f)
    }

    private fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    private fun placeMines() {
        var numMines = 0
        while (numMines < mines){
            var mineRow = rand(0, size-1)
            var mineCol = rand(0, size-1)
            if (minefield[mineRow][mineCol].type == EMPTY) {
                minefield[mineRow][mineCol].type = MINE
                numMines++
            }
        }
    }

    private fun getNeighbours(x: Int, y: Int): Array<Cell> {
        //var neighbours = arrayOf<Cell>()
        var neighbours = Array(8) {Cell()}
        var index = 0
        for (i in -1..1){
            for (j in -1..1){
                if (i == 0 && j == 0){
                    continue
                }
                var newX = x + i
                var newY = y + j
                if (newX < 0 || newY < 0 || newX >= size || newY >= size){
                    continue
                } else {
                    neighbours[index] = minefield[newX][newY]
                    index++
                }
            }
        }
        return neighbours
    }

    private fun generateClues() {
        for (i in 0..(size-1)){
            for (j in 0..(size-1)){
                if (minefield[i][j].type == MINE){
                    var neighbours = getNeighbours(i, j)
                    for (neighbour in neighbours) {
                        if (neighbour.type != MINE) {
                            neighbour.numMines++
                            neighbour.type = NUMBER
                        }
                    }
                }
            }
        }
    }

    fun generateMinefield() {
        placeMines()
        generateClues()
    }

    fun resetModel() {
        minefield = Array(size) { row ->
            Array(size) { col ->
                Cell()
            }
        }
        isOver = false
        minesFound = 0
        hasWon = false
    }
}