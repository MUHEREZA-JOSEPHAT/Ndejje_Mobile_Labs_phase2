package com.ndejje.momocalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndejje.momocalc.ui.theme.MoMoCalculatorAppTheme
import com.ndejje.momocalc.ui.theme.MoMoTypography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(typography = MoMoTypography) {
                Surface(modifier = Modifier.fillMaxSize(),
                    ) {

                MoMoCalcScreen()
            }
            }
        }
    }
}

@Composable
fun MoMoCalcScreen() {
    var amountInput by remember { mutableStateOf("") }

    val numericAmount = amountInput.toDoubleOrNull()
    val isError = when {
        amountInput.isEmpty() -> false
        numericAmount == null -> true
        numericAmount < 0 -> true
        else -> false
    }

    val errorMessage = when {
        amountInput.isNotEmpty() && numericAmount == null ->
           stringResource(R.string.error_numbers_only)
        numericAmount != null && numericAmount < 0 ->
            stringResource(R.string.Negative_Integer_Error)
        else -> ""
    }
    val fee = if (!isError){ when {
        numericAmount == null -> 0.0
        numericAmount > 0 && numericAmount <= 2499999 -> numericAmount * 0.03
        numericAmount >= 2500000 ->numericAmount * 0.015
        else -> 0.0
    }
    }else 0.0
    val formattedFee = "UGX %,.0f".format(fee)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(R.dimen.screen_padding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

) {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        HoistedAmountInput(
            amount = amountInput,
            onAmountChange = { amountInput = it },
            isError = isError,
            errorMessage = errorMessage

        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_medium)))

            Text(
                text = stringResource(R.string.fee_label, formattedFee),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

    }
}

@Composable
fun HoistedAmountInput(
    amount : String,
    //This valuable amount is to allow state in
    onAmountChange:(String) -> Unit,  // allow s events or data to flow out
    isError: Boolean = false,
    errorMessage: String = ""

){
    Column(){
        TextField(
            value =  amount,
            onValueChange = onAmountChange,


            label = {
                Text(stringResource(R.string.enter_amount))
            },
            supportingText = {
                if (isError) {

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )

                }


            }
        )
    }

}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoMoCalculatorAppTheme {

    }
}