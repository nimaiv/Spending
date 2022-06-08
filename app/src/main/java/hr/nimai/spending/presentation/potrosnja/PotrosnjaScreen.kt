package hr.nimai.spending.presentation.potrosnja

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.util.Pair
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import hr.nimai.spending.domain.util.TypedItemSerializable
import hr.nimai.spending.presentation.destinations.SelectSpendingScreenDestination

@Destination
@Composable
fun PotrosnjaScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SelectSpendingScreenDestination, TypedItemSerializable>,
    viewModel: PotrosnjaViewModel = hiltViewModel()
) {

    val activity = LocalContext.current as AppCompatActivity

    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.onEvent(PotrosnjaEvent.DisplaySpending(result.value))
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Potrošnja",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Od: ")
                        Text(
                            text = state.startDate,
                            style = MaterialTheme.typography.h6
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Do: ")
                        Text(
                            text = state.endDate,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
                Button(
                    onClick = {
                              showDatePicker(activity) { dates ->
                                  viewModel.onEvent(PotrosnjaEvent.OnDatePick(dates))
                              }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "Promjeni")
                }
            }
            Divider(
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
            )
            Button(onClick = { navigator.navigate(SelectSpendingScreenDestination) }) {
                Text(text = "Odaberi")
            }
            Text(
                text = state.naziv,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Ukupno potrošeno: ${state.ukupno}kn",
                modifier = Modifier.padding(8.dp)
            )
            Divider(
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.items) { potrosnjaItem ->
                    Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 4.dp))
                    Surface(
                        color = MaterialTheme.colors.background
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = potrosnjaItem.first,
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                text = "${potrosnjaItem.second}kn",
                                modifier = Modifier.weight(2f)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun showDatePicker(activity: AppCompatActivity, onPositiveButtonClickListener: (Pair<Long,Long>) -> Unit) {
    val picker = MaterialDatePicker.Builder.dateRangePicker()
        .setTitleText("Odaberi period")
        .setSelection(Pair(
            MaterialDatePicker.thisMonthInUtcMilliseconds(),
            MaterialDatePicker.todayInUtcMilliseconds()
        ))
        .build()
    activity.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { datumi ->
            onPositiveButtonClickListener(datumi)
        }
    }
}