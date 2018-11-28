package com.battaglino.santiago.mvvmkotlin.db.converters

/**
 * Created by Santiago Battaglino.
 */
class OwnerConverter {

    /*@TypeConverter
    fun fromOwner(owner: Images?): String? {
        return if (owner == null) null else String.format(Locale.getDefault(), "%s-separator-%s-separator-%s",
                owner.login,
                owner.type,
                owner.url)
    }

    @TypeConverter
    fun toOwner(fullOwner: String): Images? {
        if (TextUtils.isEmpty(fullOwner)) {
            return null
        }

        val parts = fullOwner.split("-separator-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val owner = Images()
        owner.login = parts[0]
        owner.type = parts[1]
        owner.url = parts[2]

        return owner
    }*/
}