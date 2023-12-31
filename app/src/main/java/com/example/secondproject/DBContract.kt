package com.example.secondproject
import android.provider.BaseColumns

object DBContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "products"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_QUANTITY = "quantity"
    }
}