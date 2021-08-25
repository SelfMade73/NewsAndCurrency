package com.example.epoxytest.net.currency_api

enum class CurrencyTypes( val currency : String ) {
    USD( "USD"),
    RUB( "RUB"),
    EURO( "EUR"),
    JPY("JPY"),
    AED("AED"), // < United Arab Emirates Dirham
    NZD("NZD"), // < New Zealand Dollar
    SEK("SEK"), // < Swedish Krona
    YER("YER")  // < Yemeni Rial
}