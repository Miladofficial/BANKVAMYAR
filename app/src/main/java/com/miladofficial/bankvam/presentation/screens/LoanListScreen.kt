package com.miladofficial.bankvam.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miladofficial.bankvam.data.entity.LoanEntity
import com.miladofficial.bankvam.presentation.viewmodel.LoanListViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanListScreen(
    viewModel: LoanListViewModel = hiltViewModel(),
    onNavigateToAddEdit: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    val loans by viewModel.loans.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("لیست وام‌ها") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("🔙")
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToAddEdit(0) }) {
                        Icon(Icons.Default.Add, contentDescription = "افزودن وام")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (loans.isEmpty()) {
                Text(
                    text = "هیچ وامی ثبت نشده است\nروی دکمه + کلیک کنید",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(loans) { loan ->
                        LoanListItem(
                            loan = loan,
                            onItemClick = { onNavigateToAddEdit(loan.id) },
                            onDeleteClick = { viewModel.deleteLoan(loan) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoanListItem(
    loan: LoanEntity,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = loan.bankName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "نوع: ${loan.loanType}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "مبلغ: ${formatRial(loan.amount)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "تعداد اقساط: ${loan.installments}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "حذف")
            }
        }
    }
}

fun formatRial(amount: Long): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return "${formatter.format(amount)} ریال"
}