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

case class Topic(id: Long, code: String, subject_id: Long)

class TopicTableDef(tag: Tag) extends Table[Topic](tag, "topic") {

    def id = column[Long]("id", O.PrimaryKey)
    def code = column[String]("code")
    def subject_id = column[Long]("subject_id")

    override def * = (id, code, subject_id) <> ((Topic.apply _).tupled, Topic.unapply)

}

object Topic {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val topics = TableQuery[TopicTableDef]

    def getTopics: Future[Seq[Topic]] = dbConfig.db.run(topics.result)

    def addToTopics(topic: Topic): Future[Boolean] = dbConfig.db.run(topics += topic).map(res => true)

}
