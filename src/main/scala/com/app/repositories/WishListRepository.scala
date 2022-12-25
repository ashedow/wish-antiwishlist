package com.app.repositories

import com.app.database.{Db, WishListTable}
import com.app.models.WishList
import slick.basic.DatabaseConfig
import slick.jdbc.MySQLProfile
import slick.lifted

import scala.concurrent.Future

class WishListRepository (val config: DatabaseConfig[MySQLProfile])
  extends Db with WishListTable {

  import config.profile.api._

  private val wishList = lifted.TableQuery[WishLists]
  db.run(DBIO.seq(wishList.schema.create))

  def addWish(userId: Long, wishId: Long): Future[WishList] = db.run {
    (wishList.map(u => (u.userId, u.wishId))
      returning wishList.map(_.id)
      into ((data, id) => WishList(id, data._1, data._2))
      ) += (userId, wishId)
  }

  def getWish(userId: Long): Future[Seq[Long]] = db.run {
    wishList.filter{
      _.userId === userId
    }.map{_.wishId}.result
  }

  def deleteWish(userId: Long, wishId: Long): Future[Int] = db.run {
    wishList.filter(w => w.wishId === wishId && w.userId === userId).delete
  }
}
