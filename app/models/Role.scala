package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/7/17.
 */

case class Role(id: Long,
                name: String)

class RoleTableDef(tag: Tag) extends Table[Role](tag, "role") {

    def id = column[Long]("id", O.PrimaryKey)
    def name = column[String]("name")

    override def * = (id, name) <> ((Role.apply _).tupled, Role.unapply)

}

object Role {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val roles = TableQuery[RoleTableDef]

    def getRoles: Future[Seq[Role]] = dbConfig.db.run(roles.result)

    def addToRoles(role: Role): Future[Boolean] = dbConfig.db.run(roles += role).map(res => true)

}
