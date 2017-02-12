package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

/**
 * Created by nigonzalez on 2/9/17.
 */

case class ChatRoom(id: Long)

class ChatRoomTableDef(tag: Tag) extends Table[ChatRoom](tag, "chat_room") {

    def id = column[Long]("id", O.PrimaryKey)

    override def * = id <> (ChatRoom.apply, ChatRoom.unapply)

}

object ChatRooms {

    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

    val chatRooms = TableQuery[ChatRoomTableDef]

    def getChatRooms: Future[Seq[ChatRoom]] = dbConfig.db.run(chatRooms.result)

    def addToChatRoom(chatRoom: ChatRoom): Future[Boolean] = dbConfig.db.run(chatRooms += chatRoom).map(res => true)

}