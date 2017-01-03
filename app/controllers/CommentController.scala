package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * commentable_id: long
 * commentable_type: String
 * user_id: long (points to User)
 * title: String
 * body: String
 *
 */
object CommentController extends Controller {

    def get = Action {
        Ok
    }

    def post = Action {
        Ok
    }

    def put = Action {
        Ok
    }

    def delete = Action {
        Ok
    }

}
