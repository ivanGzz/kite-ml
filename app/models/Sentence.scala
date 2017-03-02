package models

import java.sql.Date

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 3/1/17.
 */

case class Sentence(id: Long, lang: String, content: String, sentiment: String, question: Boolean, common_ground: Boolean, created: Date)

class SentenceTableDef(tag: Tag) extends Table[Sentence](tag, "sentence") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def lang = column[String]("lang")
    def content = column[String]("content")
    def sentiment = column[String]("sentiment")
    def question = column[Boolean]("question")
    def common_ground = column[Boolean]("common_ground")
    def created = column[Date]("created")

    override def * = (id, lang, content, sentiment, question, common_ground, created) <> (Sentence.tupled, Sentence.unapply)

}

object Sentences {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val sentences = TableQuery[SentenceTableDef]

    def getSentencesCount: Future[Int] = dbConfig.db.run(
        sentences.length.result
    )

    def getSentenceByPosition(position: Int): Future[Option[Sentence]] = dbConfig.db.run(
        sentences.drop(position).take(1).result.headOption
    )

    def addToSentences(sentence: Sentence): Future[Long] = dbConfig.db.run(
        sentences += sentence
    ).map(
        res => sentence.id
    )

}
