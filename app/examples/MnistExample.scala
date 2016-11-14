package examples

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.Builder
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, Updater}
import org.deeplearning4j.nn.conf.layers.{OutputLayer, DenseLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction

/**
 * Created by nigonzalez on 10/31/16.
 */
object MnistExample {

    def run: String = {
        println("Starting")
        val numRows = 28
        val numColumns = 28
        val outputNum = 10
        val batchSize = 128
        val rngSeed = 123
        val numEpochs = 1
        val mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed)
        val mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed)
        val conf = new Builder()
        conf.seed(rngSeed)
        conf.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        conf.iterations(1)
        conf.learningRate(0.006)
        conf.updater(Updater.NESTEROVS).momentum(0.9)
        conf.regularization(true).l2(1e-4)
        val inputLayer = new DenseLayer.Builder()
        inputLayer.nIn(numRows * numColumns)
        inputLayer.nOut(1000)
        inputLayer.activation("relu")
        inputLayer.weightInit(WeightInit.XAVIER)
        val outputLayer = new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
        outputLayer.nIn(1000)
        outputLayer.nOut(outputNum)
        outputLayer.activation("softmax")
        outputLayer.weightInit(WeightInit.XAVIER)
        val list = conf.list()
        list.layer(0, inputLayer.build())
          .layer(1, outputLayer.build())
          .pretrain(false)
          .backprop(true)
        val configuration = list.build()
        val model = new MultiLayerNetwork(configuration)
        model.init()
        model.setListeners(new ScoreIterationListener(1))
        for (i <- 0 until numEpochs) {
            println("Epoch")
            model.fit(mnistTrain)
        }
        println("Evaluating")
        val eval = new Evaluation(outputNum)
        while (mnistTest.hasNext()) {
            val next = mnistTest.next()
            val output = model.output(next.getFeatureMatrix())
            eval.eval(next.getLabels(), output)
        }
        println("finish")
        eval.stats()
    }

}
