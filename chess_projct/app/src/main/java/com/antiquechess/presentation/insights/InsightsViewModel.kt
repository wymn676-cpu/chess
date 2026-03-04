package com.antiquechess.presentation.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antiquechess.data.local.dao.ChessDao
import com.antiquechess.data.local.entity.WeaknessStatsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    chessDao: ChessDao
) : ViewModel() {

    val weaknessStats: StateFlow<List<WeaknessStatsEntity>> = chessDao.getAllWeaknessStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalBlunders: StateFlow<Int> = chessDao.getTotalBlunders()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val biggestWeakness: StateFlow<String?> = weaknessStats.map { stats ->
        stats.maxByOrNull { it.count }?.category
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}
