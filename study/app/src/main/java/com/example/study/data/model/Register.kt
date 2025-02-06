package com.example.study.data.model

import io.realm.RealmObject

open class Register:RealmObject() {
    var name: String = ""
    var email:String = ""
    var password:String = ""
    var confirmPassword:String =""
}