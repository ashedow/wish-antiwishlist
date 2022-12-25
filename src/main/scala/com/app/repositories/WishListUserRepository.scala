package repositories

import com.google.protobuf.Timestamp
import com.app.database.{Db, WishListTable}
import com.app.models.WishListUser
import slick.basic.DatabaseConfig
import slick.jdbc.MySQLProfile
import slick.lifted

import scala.concurrent.Future

class WishListUserRepository(val config: DatabaseConfig[MySQLProfile])
  extends Db with WishListTable {

  import config.profile.api._

  private final val RECENT_THRESHOLD = 86400 //is considered recent if it was updated less than one day ago (86400 sec).

  private val wishList = lifted.TableQuery[WishListUsers]
  db.run(DBIO.seq(wishList.schema.create))

  def refreshUser(userId: Long): Future[Int] = db.run {
    wishList.insertOrUpdate(WishListUser(userId, Timestamp.SECONDS_FIELD_NUMBER))
  }

  def getRecentUsers():Future[Seq[Long]] = db.run {
    wishList.filter( _.lastUpdate - Timestamp.SECONDS_FIELD_NUMBER >= RECENT_THRESHOLD).map(_.userId).result
  }
}
