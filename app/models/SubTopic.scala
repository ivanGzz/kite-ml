package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 1/9/17.
 */

case class SubTopic(id: Long,
                    code: String,
                    topic_id: Long)

class SubTopicTableDef(tag: Tag) extends Table[SubTopic](tag, "sub_topic") {

    def id = column[Long]("id", O.PrimaryKey)
    def code = column[String]("code")
    def topic_id = column[Long]("topic_id")

    override def * = (id, code, topic_id) <> ((SubTopic.apply _).tupled, SubTopic.unapply)

}

object SubTopic {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val subTopics = TableQuery[SubTopicTableDef]

    def getSubTopics: Future[Seq[SubTopic]] = dbConfig.db.run(subTopics.result)

    def addToSubTopics(subTopic: SubTopic): Future[Boolean] = dbConfig.db.run(subTopics += subTopic).map(res => true)

}
