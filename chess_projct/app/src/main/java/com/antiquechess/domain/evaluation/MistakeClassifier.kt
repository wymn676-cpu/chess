package com.antiquechess.domain.evaluation

import kotlin.math.abs

enum class MistakeType {
    Excellent,
    Good,
    Inaccuracy,
    Mistake,
    Blunder
}

object MistakeClassifier {

    /**
     * Classifies a move based on the drop in evaluation (delta).
     * 
     * Evaluation is from the perspective of the player making the move.
     * So a positive delta means the evaluation dropped (got worse for the player).
     * 
     * @param prevEval The engine evaluation before the move (in centipawns, e.g. 1.0)
     * @param newEval The engine evaluation after the move (e.g. 0.0)
     * @param isWhiteMove true if it was White's turn to move, false for Black
     */
    fun classifyMove(prevEval: Double, newEval: Double, isWhiteMove: Boolean): MistakeType {
        // Delta > 0 means the evaluation worsened for the player who just moved
        val delta = if (isWhiteMove) {
            prevEval - newEval
        } else {
            // If black played, an evaluation going from -1.0 to +1.0 is a 2.0 drop for black
            newEval - prevEval
        }

        // If delta is negative, the move was actually better than the engine expected 
        // (usually due to depth horizon effect), or just a good move.
        if (delta <= 0.0) {
            return MistakeType.Excellent
        }

        return when {
            delta < 0.5 -> MistakeType.Good
            delta < 1.5 -> MistakeType.Inaccuracy
            delta < 3.0 -> MistakeType.Mistake
            else -> MistakeType.Blunder
        }
    }
}
