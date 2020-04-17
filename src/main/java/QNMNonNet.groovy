import groovyJCSP.*
import jcsp.lang.*

class QNMNonNet implements CSProcess{
    ChannelInput fromNetworkManager
    ChannelOutputList toChecker
    ChannelInput fromCheckers
    ChannelOutput jobRequest
    void run(){
        println('started')
        def avCheckers = []
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }
        println(avCheckers)
        def response
        while (true){
            jobRequest.write('RequestJob')
            response = fromNetworkManager.read()
            if (response != 'NOJ') {
                def takingJob = (int) avCheckers.pop()
                println(takingJob)
                toChecker[takingJob].write(response)
                avCheckers << takingJob
                }
            }
        }
    }
