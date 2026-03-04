package com.ixafrica.netscan.data

data class IpInfoResponse(
    val ip: String,
    val success: Boolean,
    val isp: String?,
    val city: String?,
    val country: String?,
    val asn: String?,
    val org: String?
)