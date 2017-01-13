package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 1/4/17.
 */

case class Comment(id: Long,
                   commentable_id: Long,
                   commentable_type: String,
                   user_id: Long,
                   title: String,
                   body: String)

class CommentTableDef(tag: Tag) extends Table[Comment](tag, "comment") {

    def id = column[Long]("id", O.PrimaryKey)
    def commentable_id = column[Long]("commentable_id")
    def commentable_type = column[String]("commentable_type")
    def user_id = column[Long]("user_id")
    def title = column[String]("title")
    def body = column[String]("body")

    override def * = (id, commentable_id, commentable_type, user_id, title, body) <> ((Comment.apply _).tupled, Comment.unapply)

}

object Comment {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val comments = TableQuery[CommentTableDef]

    def getComments: Future[Seq[Comment]] = dbConfig.db.run(comments.result)

    def addToComments(comment: Comment): Future[Boolean] = dbConfig.db.run(comments += comment).map(res => true)

}
