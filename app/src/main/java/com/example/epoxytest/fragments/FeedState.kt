package com.example.epoxytest.fragments

sealed class FeedState
object Loading : FeedState()
object Success : FeedState()
