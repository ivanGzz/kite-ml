package models

import java.sql.Date
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 2/26/17.
 */

case class Network(id: Long, name: String, network: String, created: Date)

class NetworkTableDef(tag: Tag) extends Table[Network](tag, "network") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def network = column[String]("network")
    def created = column[Date]("created")

    override def * = (id, name, network, created) <> (Network.tupled, Network.unapply)

}

object Networks {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val networks = TableQuery[NetworkTableDef]

    def getNetworkByName(name: String): Future[Option[Network]] = dbConfig.db.run(
        networks.filter(_.name === name).result.headOption
    )

    def addToNetworks(network: Network): Future[Long] = dbConfig.db.run(
        networks += network
    ).map(
        res => network.id
    )

}
