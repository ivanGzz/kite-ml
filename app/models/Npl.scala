package models

import java.sql.Date

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by Nelson on 4/20/2017.
 */

case class Nlp(id: Long, name: String, path: String, filepath: String, version: Int, created: Date)

class NlpTableDef(tag: Tag) extends Table[Nlp](tag, "nlp") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def path = column[String]("path")
    def filepath = column[String]("filepath")
    def version = column[Int]("version")
    def created = column[Date]("created")

    override def * = (id, name, path, filepath, version, created) <> (Nlp.tupled, Nlp.unapply)

}

object Nlps {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val nlps = TableQuery[NlpTableDef]

    def getLatestNetworkByName(name: String): Future[Option[Nlp]] = dbConfig.db.run(
        nlps.filter(_.name === name).sortBy(_.version.desc).result.headOption
    )

}
