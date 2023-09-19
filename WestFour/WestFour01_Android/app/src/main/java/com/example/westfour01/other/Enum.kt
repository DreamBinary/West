package com.example.westfour01.other

enum class BaseUrl {
    USER,
    NOVEL,
    CHAPTER,
    CONTENT,
    COLLECTION,
    HISTORY,
    MUSIC,
    IMAGE
}

enum class UserResponseCode {
    USER_NO_EXIST,
    ERROR_PASSWORD,
    SIGN_IN_SUCCESS,
    USER_EXISTED,
    SIGN_UP_SUCCESS,
    UPDATE_SUCCESS,
    UPDATE_FAILED,
    DELETE_SUCCESS,
    DELETE_FAILED
}

enum class CollectionResponseCode {
    INSERT_SUCCESS,
    DELETE_ALL_SUCCESS,
    DELETE_ALL_FAILED,
    DELETE_ONE_SUCCESS,
}

enum class HistoryResponseCode {
    DELETE_ALL_SUCCESS,
    DELETE_ALL_FAILED,
    DELETE_ONE_SUCCESS,
}

