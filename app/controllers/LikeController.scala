package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * likeable_id: long
 * likeable_type: String
 * user_id: long (points to User)
 *
 */
object LikeController extends Controller {

    def get = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def post = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def put = Action {
        Ok(NOT_IMPLEMENTED)
    }

    def delete = Action {
        Ok(NOT_IMPLEMENTED)
    }

}
