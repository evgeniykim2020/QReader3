package com.evgeniykim.qreader3.domain

import kotlin.properties.Delegates

class Favorite {
    var id: Int by Delegates.notNull()
    var date: String by Delegates.notNull()
    var context: String by Delegates.notNull()

    constructor(date: String, context: String) {
        this.date = date
        this.context = context
    }

    constructor(id: Int, date: String, context: String) {
        this.id = id
        this.date = date
        this.context = context
    }
}