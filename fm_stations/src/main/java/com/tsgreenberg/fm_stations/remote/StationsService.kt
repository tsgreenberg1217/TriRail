package com.tsgreenberg.fm_stations.remote

import com.tsgreenberg.fm_stations.models.GetStopsResponse

interface StationsService {
    suspend fun getStops(token: String = "TESTING"): GetStopsResponse
}