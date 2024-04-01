package com.keyur.notes.Model

data class NotesModel(
    var noteId:String? = null,
    var title:String? = null,
    var note:String? = null,
    var by:String? = null
)

data class UserModel(
    var uId:String? = null,
    var name:String? = null,
    var email:String? = null,
    var pass:String? = null
)