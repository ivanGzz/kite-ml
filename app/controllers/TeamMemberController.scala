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
