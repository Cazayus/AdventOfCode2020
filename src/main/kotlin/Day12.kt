import kotlin.math.absoluteValue

enum class Direction { N, S, E, W }
enum class InstructionPart { N, S, E, W, F, L, R }
typealias Instruction = Pair<InstructionPart, Int>
typealias Position = Pair<Int, Int>

class Ship(var position: Position = Position(0, 0), var direction: Direction = Direction.E, var wayPoint: Position = Position(10, 1)) {
    fun moveToWaypoint(instruction: Instruction) {
        if (instruction.first == InstructionPart.F) {
            this.position = this.position.first + instruction.second * this.wayPoint.first to this.position.second + instruction.second * this.wayPoint.second
        }
    }

    fun moveWaypointIfNecessary(instruction: Instruction) {
        wayPoint = when (instruction.first) {
            InstructionPart.L -> when (instruction.second) {
                90 -> -wayPoint.second to wayPoint.first
                180 -> -wayPoint.first to -wayPoint.second
                270 -> wayPoint.second to -wayPoint.first
                else -> throw UnsupportedOperationException()
            }
            InstructionPart.R -> when (instruction.second) {
                90 -> wayPoint.second to -wayPoint.first
                180 -> -wayPoint.first to -wayPoint.second
                270 -> -wayPoint.second to wayPoint.first
                else -> throw UnsupportedOperationException()
            }
            InstructionPart.N -> wayPoint.first to wayPoint.second + instruction.second
            InstructionPart.S -> wayPoint.first to wayPoint.second - instruction.second
            InstructionPart.E -> wayPoint.first + instruction.second to wayPoint.second
            InstructionPart.W -> wayPoint.first - instruction.second to wayPoint.second
            else -> wayPoint
        }
    }
}

private val regex = Regex("^(N|S|E|W|F|L|R)((\\d)+)\$", RegexOption.MULTILINE)

private fun Direction.turnLeft90(): Direction {
    return when (this) {
        Direction.N -> Direction.W
        Direction.W -> Direction.S
        Direction.S -> Direction.E
        Direction.E -> Direction.N
    }
}

private fun Direction.turnRight90(): Direction {
    return when (this) {
        Direction.N -> Direction.E
        Direction.W -> Direction.N
        Direction.S -> Direction.W
        Direction.E -> Direction.S
    }
}

private fun Direction.turnIfNecessary(instruction: Instruction): Direction {
    return when (instruction.first) {
        InstructionPart.L -> when (instruction.second) {
            90 -> this.turnLeft90()
            180 -> this.turnLeft90().turnLeft90()
            270 -> this.turnRight90()
            else -> throw UnsupportedOperationException()
        }
        InstructionPart.R -> when (instruction.second) {
            90 -> this.turnRight90()
            180 -> this.turnRight90().turnRight90()
            270 -> this.turnLeft90()
            else -> throw UnsupportedOperationException()
        }
        else -> this
    }
}

private fun Position.moveBy(instruction: Instruction, facingDirection: Direction): Position {
    return when (instruction.first) {
        InstructionPart.N -> this.first to this.second + instruction.second
        InstructionPart.S -> this.first to this.second - instruction.second
        InstructionPart.E -> this.first + instruction.second to this.second
        InstructionPart.W -> this.first - instruction.second to this.second
        InstructionPart.F -> when (facingDirection) {
            Direction.N -> this.first to this.second + instruction.second
            Direction.S -> this.first to this.second - instruction.second
            Direction.E -> this.first + instruction.second to this.second
            Direction.W -> this.first - instruction.second to this.second
        }
        InstructionPart.L -> this
        InstructionPart.R -> this
    }
}

fun main() {
    val input = regex.findAll(Utils.getInputAsText("Day12")).map { matchResult ->
        val (instruction, value) = matchResult.destructured
        Instruction(InstructionPart.valueOf(instruction), value.toInt())
    }.toList()
    val endShip1 = input.fold(Ship()) { ship, instruction ->
        ship.direction = ship.direction.turnIfNecessary(instruction)
        ship.position = ship.position.moveBy(instruction, ship.direction)
        ship
    }
    println("Puzzle 1 : ${endShip1.position.first.absoluteValue + endShip1.position.second.absoluteValue}")
    val endShip2 = input.fold(Ship()) { ship, instruction ->
        ship.moveWaypointIfNecessary(instruction)
        ship.moveToWaypoint(instruction)
        ship
    }
    println("Puzzle 2 : ${endShip2.position.first.absoluteValue + endShip2.position.second.absoluteValue}")
}
