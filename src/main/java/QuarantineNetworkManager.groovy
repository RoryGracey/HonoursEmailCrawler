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
            def index = alt.fairSelect()
            jobRequest.write('requestJob')
            println('wroteJobRequest')
            switch (index) {
                case 0:
                    def fromNM = fromChecker[0].read()
                    println(fromNM)
                    break
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
