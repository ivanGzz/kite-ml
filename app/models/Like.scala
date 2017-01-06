package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/5/17.
 */

case class Like(id: Long,
                likeable_id: Long,
                likeable_type: String,
                user_id: Long)

class LikeTableDef(tag: Tag) extends Table[Like](tag, "likes") {

    def id = column[Long]("id", O.PrimaryKey)
    def likeable_id = column[Long]("likeable_id")
    def likeable_type = column[String]("likeable_type")
    def user_id = column[Long]("user_id")

    override def * = (id, likeable_id, likeable_type, user_id) <> ((Like.apply _).tupled, Like.unapply)

}

object Like {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val likes = TableQuery[LikeTableDef]

    def getLikes: Future[Seq[Like]] = dbConfig.db.run(likes.result)

    def addToLikes(like: Like): Future[Boolean] = dbConfig.db.run(likes += like).map(res => true)

}
