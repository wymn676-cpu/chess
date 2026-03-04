package com.antiquechess.domain.engine

interface ChessEngine {
    fun start()
    fun stop()
    
    /**
     * Analyzes a given FEN position and returns the evaluation and best move.
     * 
     * @param fen The Forsyth-Edwards Notation of the position.
     * @param depth The depth of calculation (e.g., 12).
     * @return [AnalysisResult] containing evaluation (centipawns/mate) and best move (e.g. "e2e4").
     */
    suspend fun analyzePosition(fen: String, depth: Int = 12): AnalysisResult
}

data class AnalysisResult(
    val evaluation: Double, // Negative for black advantage, positive for white advantage
    val bestMove: String
)
