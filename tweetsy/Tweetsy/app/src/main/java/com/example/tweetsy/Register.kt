package com.example.tweetsy

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmUUID

class Register : RealmObject {
    @PrimaryKey
    var id: String = RealmUUID.random().toString()
    var name: String = ""
    var email: String = ""
    var password: String = ""

}
