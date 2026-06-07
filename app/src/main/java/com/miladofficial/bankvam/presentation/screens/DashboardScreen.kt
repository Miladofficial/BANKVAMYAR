package com.miladofficial.bankvam.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.miladofficial.bankvam.presentation.viewmodel.DashboardViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToLoanList: () -> Unit
) {
    val totalAmount by viewModel.totalAmount.collectAsState()
    val loanCount by viewModel.loanCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("داشبورد BANKVAM") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToLoanList) {
                Text("لیست وام‌ها")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "مجموع مبلغ وام‌ها",
                            color = androidx.compose.ui.graphics.Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = formatRial(totalAmount),
                            color = androidx.compose.ui.graphics.Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("تعداد وام‌های ثبت شده", fontSize = 16.sp)
                        Text(
                            text = "$loanCount عدد",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "💡 نکات مدیریت وام",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• اقساط خود را به موقع پرداخت کنید")
                        Text("• برای وام‌های معوق، زودتر اقدام کنید")
                        Text("• از این اپ برای برنامه‌ریزی مالی استفاده کنید")
                    }
                }
            }
        }
    }
}

fun formatRial(amount: Long): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return "${formatter.format(amount)} ریال"
}