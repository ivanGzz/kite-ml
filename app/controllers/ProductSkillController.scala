package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created by nigonzalez on 12/14/16.
 *
 * Model:
 *
 * id: long
 * product_id: long (points to Product)
 * skill_id: long (points to Skill)
 * value: long
 *
 */
object ProductSkillController extends Controller {

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
