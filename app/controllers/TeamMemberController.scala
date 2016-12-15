package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/15/16.
 *
 * Model:
 *
 * id: long
 * team_id: long (points to Team)
 * user_id: long (points to User)
 * project_id: long (points to Project)
 * role: String
 *
 */
object TeamMemberController extends Controller {

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
