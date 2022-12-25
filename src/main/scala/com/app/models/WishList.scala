// package models
package com.app.models

case class WishList(id: Long, userId: Long, wishId: Long)

case class UnWishList(id: Long, userId: Long, unwishId: Long)

case class WishListUser(userId: Long, lastUpdate: Int)
