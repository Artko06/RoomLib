package com.example.roomlib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomlib.ui.theme.RoomLibTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    state: TelContactState,
    onEvent: (ITelContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    BasicAlertDialog(
        onDismissRequest = { onEvent(ITelContactEvent.CloseDialogWindow) },
        modifier = modifier
    )
    {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Add contact", fontSize = 28.sp)

                Spacer(modifier = Modifier.padding(24.dp))
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    OutlinedTextField(
                        value = state.firstName,
                        onValueChange = {
                            onEvent(ITelContactEvent.SetFirstName(it))
                        },
                        placeholder = {
                            Text(text = "First name")
                        }
                    )

                    OutlinedTextField(
                        value = state.lastName,
                        onValueChange = {
                            onEvent(ITelContactEvent.SetLastName(it))
                        },
                        placeholder = {
                            Text(text = "Last name")
                        }
                    )
                    OutlinedTextField(
                        value = state.telephoneNumber,
                        onValueChange = {
                            onEvent(ITelContactEvent.SetTelephoneNumber(it))
                        },
                        placeholder = {
                            Text(text = "Telephone number")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onEvent(ITelContactEvent.SaveTelContactEvent)}
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    RoomLibTheme {
        AddContactDialog(state = TelContactState(), onEvent = {})
    }
}