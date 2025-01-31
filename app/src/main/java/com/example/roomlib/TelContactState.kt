package com.example.roomlib

data class TelContactState(
    val listTelContacts: List<TelContact> = emptyList<TelContact>(),
    val firstName: String = "",
    val lastName: String = "",
    val telephoneNumber: String = "",
    val isAddingTelContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
