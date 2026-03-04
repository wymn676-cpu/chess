package com.antiquechess.data.engine

import com.antiquechess.domain.engine.AnalysisResult
import com.antiquechess.domain.engine.ChessEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import android.util.Log

class StockfishEngineImpl(
    private val enginePath: String
) : ChessEngine {

    private var process: Process? = null
    private var reader: BufferedReader? = null
    private var writer: BufferedWriter? = null

    override fun start() {
        if (process != null) return
        try {
            process = ProcessBuilder(enginePath).start()
            reader = BufferedReader(InputStreamReader(process?.inputStream))
            writer = BufferedWriter(OutputStreamWriter(process?.outputStream))
            
            sendCommand("uci")
            // Wait for non-blocking init in a real app, here we assume it's ready.
        } catch (e: Exception) {
            Log.e("Stockfish", "Failed to start engine from $enginePath: ${e.message}")
        }
    }

    override fun stop() {
        try {
            sendCommand("quit")
            writer?.close()
            reader?.close()
            process?.destroy()
        } catch (e: Exception) {
            // ignore
        } finally {
            process = null
            reader = null
            writer = null
        }
    }

    override suspend fun analyzePosition(fen: String, depth: Int): AnalysisResult = withContext(Dispatchers.IO) {
        if (process == null) start()

        sendCommand("position fen $fen")
        sendCommand("go depth $depth")

        var bestMove = ""
        var scoreCp = 0.0

        try {
            while (true) {
                val line = reader?.readLine() ?: break
                
                // Parse score
                if (line.startsWith("info depth") && line.contains("score cp")) {
                    val parts = line.split(" ")
                    val cpIndex = parts.indexOf("cp")
                    if (cpIndex != -1 && cpIndex + 1 < parts.size) {
                        scoreCp = parts[cpIndex + 1].toDouble() / 100.0
                    }
                } else if (line.startsWith("info depth") && line.contains("score mate")) {
                    val parts = line.split(" ")
                    val mateIndex = parts.indexOf("mate")
                    if (mateIndex != -1 && mateIndex + 1 < parts.size) {
                        val movesToMate = parts[mateIndex + 1].toInt()
                        scoreCp = if (movesToMate > 0) 100.0 else -100.0
                    }
                }

                if (line.startsWith("bestmove")) {
                    val parts = line.split(" ")
                    if (parts.size >= 2) {
                        bestMove = parts[1]
                    }
                    break
                }
            }
        } catch (e: Exception) {
            Log.e("Stockfish", "Error reading engine output: ${e.message}")
        }

        AnalysisResult(
            evaluation = scoreCp,
            bestMove = bestMove
        )
    }

    private fun sendCommand(command: String) {
        try {
            writer?.write(command + "\n")
            writer?.flush()
        } catch (e: Exception) {
            Log.e("Stockfish", "Failed to send command $command: ${e.message}")
        }
    }
}
