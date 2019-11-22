import jcsp.lang.*
import groovyJCSP.*
import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelInputList fromChecker
    ChannelOutputList toChecker
    ChannelOutput toBuffer
    ChannelOutput jobRequest
    void run(){
        sleep(1000)
        println('started')
        def avCheckers = []
        println(avCheckers)
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }
        while (true){
            println('top of loop')
            jobRequest.write('RequestJob')
            def response = fromNetworkManager.read()
            println(response)
            if (response == 'nojob'){
                println('here')
                jobRequest.write('RequestJob')
            }else{
                def takingJob = (int)avCheckers.pop()
                toChecker[takingJob].write(response)
                def checkerResponse = fromChecker[takingJob].read()
                avCheckers << takingJob
                println(avCheckers)
                println(checkerResponse)
            }

        }
    }
}
