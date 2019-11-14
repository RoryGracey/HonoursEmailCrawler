import jcsp.lang.*
import groovyJCSP.*
import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelInputList fromChecker
    ChannelOutputList toChecker
    ChannelInput checkerReady
    ChannelOutput toNetworkManager
    void run(){
        def avCheckers = []
        def alt = new ALT (fromChecker)
        while(true){
            println(avCheckers)
            def index = alt.fairSelect()
            switch (index) {
                case 0:
                    def fromNM = fromChecker[0].read()
                    if(fromNM == 'jobav'){
                        if (avCheckers.size() != 0) {
                            def takingJob = (int) avCheckers.pop()
                            toNetworkManager.write(takingJob)
                            def job = fromNetworkManager.read()
                            toChecker[takingJob].write(job)
                            println('sent to checker')
                        }else{

                        }
                        break
                    }
                    break
                case 1:
                    def result = fromChecker[1].read()
                    if (result == 'Ready') {
                        avCheckers << 1
                        break
                    }
                    break
                case 2:
                    def result = fromChecker[2].read()
                    println(result)
                    if(result == 'Ready'){
                        avCheckers << 2
                        break
                    }
                    break
                case 3:
                    def result = fromChecker[3].read()
                    if (result == 'Ready'){
                        avCheckers << 3
                        break
                    }
                    break
                case 4:
                    def result = fromChecker[4].read()
                    if (result == 'Ready'){
                        avCheckers << 4
                        break
                    }else{
                        println(result)
                    }
                    break
            }
        }
    }
}
