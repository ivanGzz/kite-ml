package learning

import models.{UserCompetencies, UserCompetency}
import org.datavec.api.records.reader.impl.collection.ListStringRecordReader
import org.datavec.api.split.ListStringSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator

/**
 * Created by nigonzalez on 2/14/17.
 */
object UserCompetencyNet {

    def train = {
        UserCompetencies.getUserCompetenciesCount.flatMap { res =>
            println(res)
            UserCompetencies.getUserCompetencies(1000)
        }.map { res =>
            val userCompetencies = scala.collection.JavaConversions.seqAsJavaList(res.map(_.toList))
            val recordReader = new ListStringRecordReader()
            recordReader.initialize(new ListStringSplit(userCompetencies))
            //val iterator = new RecordReaderDataSetIterator()
        }
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
