## Requisites

- Java 1.7
- Scala 2.11.7
- SBT 0.13.11

## Running

Open a terminal in the root folder and run:

    sbt
    
In the sbt command line run:

    compile
    
to compile the source code. If everything went well you can run:

    run
    
to run the project. It will load a new web service listening in port 9000.

## Web services documentation

### Home 

The web service in ```localhost:9000``` just print a Hello World message.

### MNIST

The web service in ```localhost:9000/mnist``` will train a small neural network to recognize hand-written digits using the
MNIST dataset. It will print the results of the training.

### Sentiment

The web service in ```localhost:9000/sentiment``` will try to categorize the text query as a NEGATIVE, NEUTRAL or POSITIVE
sentiment. Example: ```localhost:9000/sentiment?text=this+is+really+great``` should return a POSITIVE sentiment.