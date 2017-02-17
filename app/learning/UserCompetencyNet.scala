package learning

import models.UserCompetency

/**
 * Created by nigonzalez on 2/14/17.
 */
object UserCompetencyNet {

    def train = {
        ""
    }

    def rate(competencies: List[Int]): String = {
        val sum = competencies.sum
        sum match {
            case x if x > 12 => "A"
            case x if x > 8 => "B"
            case x if x > 4 => "C"
            case _ => "D"
        }
    }

}
