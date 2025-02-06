package com.example.study.data.model

import io.realm.RealmObject

open class Login:RealmObject() {

    var email:String = ""
    var password:String = ""

}