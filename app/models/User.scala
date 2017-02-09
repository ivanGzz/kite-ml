package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/10/17.
 */

case class User(id: Long)

class UserTableDef(tag: Tag) extends Table[User](tag, "users") {

    def id = column[Long]("id", O.PrimaryKey)

    override def * = id <> (User.apply, User.unapply)

}

object Users {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val users = TableQuery[UserTableDef]

    def getUsers: Future[Seq[User]] = dbConfig.db.run(users.result)

    def addToUsers(user: User): Future[Boolean] = dbConfig.db.run(users += user).map(res => true)

}
