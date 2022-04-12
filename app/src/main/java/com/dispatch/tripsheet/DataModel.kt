package com.dispatch.tripsheet

class Tripsheetlist (val videos: List<DataModel>)


class DataModel(
    var Tripsheet: Int,
    var WOrder: Int,
    var DElNote: Int,
    var name: String,
    var Weight: Int,
    var tvdone: String,
    var Drivers: String,
    var state: DataState = DataState.Unselected

    )
