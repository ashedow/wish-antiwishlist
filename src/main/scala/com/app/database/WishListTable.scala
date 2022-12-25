package com.app.database

import com.app.models.{WishList, UnWishList, WishListUser}

trait WishListTable {
  this: Db =>

  import config.profile.api._

  class WishLists(tag: Tag) extends Table[WishList](tag, "wish_list") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id")
    def wishId = column[Long]("wish_id")
    def * = (id, userId, wishId) <> ((WishList.apply _).tupled, WishList.unapply)
  }

  class UnWishLists(tag: Tag) extends Table[UnWishList](tag, "unwish_list") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("user_id")
    def unwishId = column[Long]("unwish_id")
    def * = (id, userId, unwishId) <> ((UnWishList.apply _).tupled, UnWishList.unapply)

  }

  class WishListUsers(tag: Tag) extends Table[WishListUser](tag, "wish_list_user") {
    def userId = column[Long]("user_id", O.PrimaryKey)
    def lastUpdate = column[Int]("last_update")
    def * = (userId, lastUpdate) <> ((WishListUser.apply _).tupled, WishListUser.unapply)

  }

}
