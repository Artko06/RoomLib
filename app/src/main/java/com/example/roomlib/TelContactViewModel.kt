package com.example.roomlib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TelContactViewModel(
    private val dao: ITelContactDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _state = MutableStateFlow(TelContactState())
    private val _telContacts = _sortType.flatMapLatest {
        sortType ->
        when(sortType){
            SortType.FIRST_NAME -> dao.getTelContactOrderByFirstName()
            SortType.LAST_NAME -> dao.getTelContactOrderByLastName()
            SortType.TELEPHONE_NUMBER -> dao.getTelContactOrderByTelephoneNumber()
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _telContacts) {
        state, sortType, telContacts ->
        state.copy(
            listTelContacts = telContacts,
            sortType = sortType
        )
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            TelContactState()
        )

    fun onEvent(event: ITelContactEvent){
        when(event){
            ITelContactEvent.CloseDialogWindow -> {
                _state.update { it.copy(
                    isAddingTelContact = false
                )
                } }

            is ITelContactEvent.DeleteTelContactEvent -> {
                viewModelScope.launch {
                    dao.deleteTelContact(event.telContact)
                }
            }

            ITelContactEvent.SaveTelContactEvent -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val telephoneNumber = state.value.telephoneNumber

                if(firstName.isBlank() || lastName.isBlank() || lastName.isBlank()){
                    return
                }

                viewModelScope.launch {
                    dao.upsertTelContact(
                        TelContact(
                        firstName = firstName,
                        lastName = lastName,
                        telephoneNumber = telephoneNumber
                    ))
                }

                _state.update {
                    it.copy(
                        firstName = "",
                        lastName = "",
                        telephoneNumber = "",
                        isAddingTelContact = false
                    )
                }
            }

            is ITelContactEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                )
                } }

            is ITelContactEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                )
                } }

            is ITelContactEvent.SetTelephoneNumber -> {
                _state.update { it.copy(
                    telephoneNumber = event.telephoneNumber
                )
                } }

            ITelContactEvent.ShowDialogWindow -> {
                _state.update { it.copy(
                    isAddingTelContact = true
                )
                } }

            is ITelContactEvent.SortTelContactEvent -> {
                _sortType.value = event.sortType
            }
        }
    }
}