package com.github.drewstephensdesigns.parkexplorer.utils

object Config {

    // Get API at https://www.nps.gov/subjects/developer/get-started.htm
    const val API_KEY = ""
    
    // URL for endpoint for activities within parks
    const val BASE_URL = "https://developer.nps.gov/api/v1/topics/parks?&q=archeology%2C%20animals%2C%20tragic%20events&limit=1000&api_key=$API_KEY"

}
