package controllers

import play.api.mvc.{Action, Controller}

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
