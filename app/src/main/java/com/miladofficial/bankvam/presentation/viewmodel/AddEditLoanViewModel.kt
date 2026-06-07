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
class AddEditLoanViewModel @Inject constructor(
    private val repository: LoanRepository
) : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    fun saveLoan(
        id: Long,
        bankName: String,
        loanType: String,
        amount: Long,
        installments: Int,
        interestRate: Double,
        startDate: String,
        note: String
    ) {
        viewModelScope.launch {
            _isSaving.value = true
            val loan = LoanEntity(
                id = id,
                bankName = bankName,
                loanType = loanType,
                amount = amount,
                installments = installments,
                interestRate = interestRate,
                startDate = startDate,
                note = note
            )
            if (id == 0L) {
                repository.insertLoan(loan)
            } else {
                repository.updateLoan(loan)
            }
            _saved.value = true
            _isSaving.value = false
        }
    }

    fun resetSavedState() {
        _saved.value = false
    }
}