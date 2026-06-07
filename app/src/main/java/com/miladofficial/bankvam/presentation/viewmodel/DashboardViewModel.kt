package com.miladofficial.bankvam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miladofficial.bankvam.data.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: LoanRepository
) : ViewModel() {

    private val _totalAmount = MutableStateFlow(0L)
    val totalAmount: StateFlow<Long> = _totalAmount.asStateFlow()

    private val _loanCount = MutableStateFlow(0)
    val loanCount: StateFlow<Int> = _loanCount.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getTotalAmount().collect { amount ->
                _totalAmount.value = amount ?: 0L
            }
        }

        viewModelScope.launch {
            repository.getAllLoans().collect { loans ->
                _loanCount.value = loans.size
            }
        }
    }

    fun refresh() {
        loadData()
    }
}