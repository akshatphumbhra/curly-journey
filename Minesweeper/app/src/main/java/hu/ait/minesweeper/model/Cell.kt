package hu.ait.minesweeper.model

class Cell constructor() {

    public val EMPTY: Short = 0
    public val MINE: Short = 1
    public val NUMBER: Short = 2

    public var isFlagged: Boolean = false
    public var isClicked: Boolean = false
    public var numMines: Int = 0
    public var type: Short = 0
    public var x = 0
    public var y = 0
}