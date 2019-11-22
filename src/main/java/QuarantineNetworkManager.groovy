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
        def avCheckers = []
        def alt = new ALT (fromChecker)
        while(true){
            jobRequest.write('RequestJob')
            def response = fromNetworkManager.read()
            if(response == 'nojob' && avCheckers.size() != 0){
                break
            }
            switch (alt.fairSelect()) {
                case 1:
                    def result = fromChecker[1].read()
                    if (result == 'Ready') {
                        avCheckers << 1
                        break
                    }else if(result != 'Ready'){
                        println('Result: ' + result)
                    }
                    break
                case 2:
                    def result = fromChecker[2].read()
                    if(result == 'Ready'){
                        avCheckers << 2
                        break
                    }else if(result != 'Ready'){
                        println('Result: ' + result)
                    }
                    break
                case 3:
                    def result = fromChecker[3].read()
                    if (result == 'Ready'){
                        avCheckers << 3
                        break
                    }else if(result != 'Ready'){
                        println('Result: ' + result)
                    }
                    break
                case 4:
                    def result = fromChecker[4].read()
                    if (result == 'Ready'){
                        avCheckers << 4
                        break
                    }else if(result != 'Ready'){
                        println('Result: ' + result)
                    }
                    break
            }
        }

    }
}
