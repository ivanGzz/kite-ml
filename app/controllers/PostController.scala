package controllers

import models.{AuditLog, Post}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by nigonzalez on 12/13/16.
 *
 * Model:
 *
 * id: long
 * user_id: long (points to User)
 * team_id: long (points to Team)
 * product_id = long (points to Product)
 * post_type: String
 * comment: String
 *
 */
object PostController extends Controller {

    implicit val postRead = Json.reads[Post]
    implicit val postWrite = Json.writes[Post]

    def get = Action.async {
        Post.getPosts.map(res => Ok(Json.toJson(res)))
    }

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(post, _) =>
                AuditLog.addToLog(request.uri, post.toString).flatMap(res =>
                    Post.addToPosts(post).map(res =>
                        Ok("Post added")
                    )
                )
            case JsError(errors) => {
                Future(BadRequest)
            }
        }
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
