package com.app.repositories

import com.app.database.{Db, WishListTable}
import com.app.models.UnWishList
import slick.basic.DatabaseConfig
import slick.jdbc.MySQLProfile
import slick.lifted

import scala.concurrent.Future

class UnWishListRepository (val config: DatabaseConfig[MySQLProfile])
  extends Db with WishListTable {

  import config.profile.api._

  private val unwishList = lifted.TableQuery[UnWishLists]
  db.run(DBIO.seq(unwishList.schema.create))

  def addUnWish(userId: Long, unwishId: Long): Future[UnWishList] = db.run {
    (unwishList.map(u => (u.userId, u.unwishId))
      returning unwishList.map(_.id)
      into ((data, id) => UnWishList(id, data._1, data._2))
      ) += (userId, unwishId)
  }

  def getUnWish(userId: Long): Future[Seq[Long]] = db.run {
    unwishList.filter{
      _.userId === userId
    }.map{_.unwishId}.result
  }

  def deleteUnWish(userId: Long, unwishId: Long): Future[Int] = db.run {
    unwishList.filter(w => w.unwishId === unwishId && w.userId === userId).delete
  }
}
