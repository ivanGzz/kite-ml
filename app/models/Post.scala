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

case class Post(id: Long,
                user_id: Long,
                team_id: Long,
                product_id: Long,
                post_type: String,
                comment: String)

class PostTableDef(tag: Tag) extends Table[Post](tag, "post") {

    def id = column[Long]("id", O.PrimaryKey)
    def user_id = column[Long]("user_id")
    def team_id = column[Long]("team_id")
    def product_id = column[Long]("product_id")
    def post_type = column[String]("post_type")
    def comment = column[String]("comment")

    override def * = (id, user_id, team_id, product_id, post_type, comment) <> ((Post.apply _).tupled, Post.unapply)

}

object Post {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val posts = TableQuery[PostTableDef]

    def getPosts: Future[Seq[Post]] = dbConfig.db.run(posts.result)

    def addToPosts(post: Post): Future[Boolean] = dbConfig.db.run(posts += post).map(res => true)

}
