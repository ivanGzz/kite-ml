package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 2/9/17.
 */

case class UserCompetency(id: Long, user_id: Long, chat_room_id: Long, competencies: String)

class UserCompetencyTableDef(tag: Tag) extends Table[UserCompetency](tag, "user_competency") {

    def id = column[Long]("id", O.PrimaryKey)
    def user_id = column[Long]("user_id")
    def chat_room_id = column[Long]("chat_room_id")
    def competencies = column[String]("competencies")

    override def * = (id, user_id, chat_room_id, competencies) <> (UserCompetency.tupled, UserCompetency.unapply)

}

object UserCompetencies {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val userCompetencies = TableQuery[UserCompetencyTableDef]

    def getUserCompetency(id: Long): Future[Option[UserCompetency]] = dbConfig.db.run(
        userCompetencies.filter(_.id === id).result.headOption
    )

    def getUserCompetencies: Future[Seq[UserCompetency]] = dbConfig.db.run(
        userCompetencies.result
    )

    def addToUserCompetencies(userCompetency: UserCompetency): Future[Long] = dbConfig.db.run(
        userCompetencies += userCompetency
    ).map(
        res => userCompetency.id
    )

    def updateUserCompetencies(userId: Long, chatRoomId: Long, competencies: String): Future[Boolean] = dbConfig.db.run(
        userCompetencies.filter(
            x => x.user_id === userId && x.chat_room_id === chatRoomId
        ).map(
            _.competencies
        ).update(competencies)
    ).map(
        res => true
    )

}
