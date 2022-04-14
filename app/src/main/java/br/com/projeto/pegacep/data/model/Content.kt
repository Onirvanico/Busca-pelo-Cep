package br.com.projeto.pegacep.data.model

import com.google.gson.annotations.SerializedName

data class Content(
    val cep: String,
    @SerializedName("address_type")
    val addressType: String,
    @SerializedName("address_name")
    val addressName: String,
    val address: String,
    val state: String,
    val distric: String,
    val lat: String,
    val lng: String,
    val city: String,
    @SerializedName("city_ibge")
    val cityIbge: String,
    val ddd: String
) {

}
