package com.manidevs.cryptotrackingev.model

    data class CryptoCoin(
        val id: String,
        val name: String,
        val symbol: String,
        val price_usd: String,
        val percent_change_1h: String,
        val percent_change_24h: String,
        val percent_change_7d: String
    )