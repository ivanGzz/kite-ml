package models

import java.sql.Date

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/10/17.
 */

case class User(id: Long, birthday: Date, active: Boolean, role_id: Long)

class UserTableDef(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Long]("id", O.PrimaryKey)
    def birthday = column[Date]("birthday")
    def active = column[Boolean]("active")
    def role_id = column[Long]("role_id")

    override def * = (id, birthday, active, role_id) <> ((User.apply _).tupled, User.unapply)

}

object User {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val users = TableQuery[UserTableDef]

    def getUsers: Future[Seq[User]] = dbConfig.db.run(users.result)

    def addToUsers(user: User): Future[Boolean] = dbConfig.db.run(users += user).map(res => true)

}
