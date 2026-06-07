package com.miladofficial.bankvam.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miladofficial.bankvam.data.entity.LoanEntity
import com.miladofficial.bankvam.data.repository.LoanRepository
import com.miladofficial.bankvam.presentation.viewmodel.AddEditLoanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditLoanScreen(
    loanId: Long,
    onNavigateBack: () -> Unit,
    viewModel: AddEditLoanViewModel = hiltViewModel()
) {
    val isSaving by viewModel.isSaving.collectAsState()
    val saved by viewModel.saved.collectAsState()

    var loan by remember { mutableStateOf<LoanEntity?>(null) }
    val scope = rememberCoroutineScope()
    val repository = hiltViewModel<LoanRepository>()

    LaunchedEffect(loanId) {
        if (loanId != 0L) {
            scope.launch {
                repository.getAllLoans().collect { list ->
                    loan = list.find { it.id == loanId }
                }
            }
        }
    }

    var bankName by remember { mutableStateOf(loan?.bankName ?: "") }
    var loanType by remember { mutableStateOf(loan?.loanType ?: "") }
    var amount by remember { mutableStateOf(loan?.amount?.toString() ?: "") }
    var installments by remember { mutableStateOf(loan?.installments?.toString() ?: "") }
    var interestRate by remember { mutableStateOf(loan?.interestRate?.toString() ?: "") }
    var startDate by remember { mutableStateOf(loan?.startDate ?: "") }
    var note by remember { mutableStateOf(loan?.note ?: "") }

    LaunchedEffect(loan) {
        loan?.let {
            bankName = it.bankName
            loanType = it.loanType
            amount = it.amount.toString()
            installments = it.installments.toString()
            interestRate = it.interestRate.toString()
            startDate = it.startDate
            note = it.note
        }
    }

    LaunchedEffect(saved) {
        if (saved) {
            onNavigateBack()
            viewModel.resetSavedState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (loanId == 0L) "افزودن وام جدید" else "ویرایش وام") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("🔙")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = bankName,
                onValueChange = { bankName = it },
                label = { Text("نام بانک") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = loanType,
                onValueChange = { loanType = it },
                label = { Text("نوع وام") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("مبلغ وام (ریال)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = installments,
                onValueChange = { installments = it },
                label = { Text("تعداد اقساط") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = interestRate,
                onValueChange = { interestRate = it },
                label = { Text("نرخ سود سالانه (درصد)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value