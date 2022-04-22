package com.tsgreenberg.eta_info.testing

object TestingTags {
    private const val ETA_SOUTH = "ETA_SOUTH"
    private const val ETA_NORTH = "ETA_NORTH"
    private const val TITLE_TAG = "TITLE"
    private const val MINS_TAG = "MINS"

    const val ETA_TITLE_NORTH = "${ETA_NORTH}_${TITLE_TAG}"
    const val ETA_TITLE_SOUTH = "${ETA_SOUTH}_${TITLE_TAG}"

    const val ETA_MINS_NORTH = "${ETA_NORTH}_${MINS_TAG}"
    const val ETA_MINS_SOUTH = "${ETA_SOUTH}_${MINS_TAG}"
    const val ETA_VIEWPAGER = "ETA_VIEWPAGER"

}