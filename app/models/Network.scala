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

case class Network(id: Long, name: String, path: String, version: Int, created: Date, filepath: String, result: String)

class NetworkTableDef(tag: Tag) extends Table[Network](tag, "network") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def path = column[String]("path")
    def version = column[Int]("version")
    def created = column[Date]("created")
    def filepath = column[String]("filepath")
    def result = column[String]("result")

    override def * = (id, name, path, version, created, filepath, result) <> (Network.tupled, Network.unapply)

}

object Networks {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val networks = TableQuery[NetworkTableDef]

    def getNetworkByName(name: String): Future[Option[Network]] = dbConfig.db.run(
        networks.filter(_.name === name).result.headOption
    )

    def getNetworkByNameAndVersion(name: String, version: Int): Future[Option[Network]] = dbConfig.db.run(
        networks.filter(x => x.name === name && x.version === version).result.headOption
    )

    def getLatestNetworkByName(name: String): Future[Option[Network]] = dbConfig.db.run(
        networks.filter(_.name === name).sortBy(_.version.desc).result.headOption
    )

    def addToNetworks(network: Network): Future[Long] = dbConfig.db.run(
        networks += network
    ).map(
        res => network.id
    )

    def addToNetworksVersioned(network: Network): Future[Long] = dbConfig.db.run(
        networks.filter(_.name === network.name).sortBy(_.version.desc).result.headOption
    ).flatMap {
        _ match {
            case Some(n) =>
                addToNetworks(Network(0, network.name, network.path, n.version + 1, network.created, network.filepath, network.result))
            case None =>
                addToNetworks(network)
        }
    }

}
