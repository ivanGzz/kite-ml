package models

import java.util

import play.api.db.slick.DatabaseConfigProvider
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import java.sql.Date

/**
 * Created by nigonzalez on 12/3/16.
 */

case class AuditLog(id: Long, url: String, payload: String, created: Date)

class AuditLogTableDef(tag: Tag) extends Table[AuditLog](tag, "audit_log") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def url = column[String]("url")
    def payload = column[String]("payload")
    def created = column[Date]("created")

    override def * = (id, url, payload, created) <> ((AuditLog.apply _).tupled, AuditLog.unapply)

}

object AuditLog {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val auditLogs = TableQuery[AuditLogTableDef]

    def addToLog(url: String, payload: String): Future[AuditLog] = {
        val today = new util.Date()
        val log = AuditLog(0L, url, payload, new Date(today.getTime))
        dbConfig.db.run(auditLogs += log).map(res => log)
    }

}
