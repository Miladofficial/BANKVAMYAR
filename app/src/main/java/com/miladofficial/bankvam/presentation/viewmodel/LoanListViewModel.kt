package com.miladofficial.bankvam.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miladofficial.bankvam.data.entity.LoanEntity
import com.miladofficial.bankvam.data.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanListViewModel @Inject constructor(
    private val repository: LoanRepository
) : ViewModel() {

    private val _loans = MutableStateFlow<List<LoanEntity>>(emptyList())
    val loans: StateFlow<List<LoanEntity>> = _loans.asStateFlow()

    init {
        loadLoans()
    }

    fun loadLoans() {
        viewModelScope.launch {
            repository.getAllLoans().collect { loanList ->
                _loans.value = loanList
            }
        }
    }

    fun deleteLoan(loan: LoanEntity) {
        viewModelScope.launch {
            repository.deleteLoan(loan)
            loadLoans()
        }
    }
}