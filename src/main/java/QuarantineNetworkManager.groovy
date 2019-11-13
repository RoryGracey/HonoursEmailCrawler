import jcsp.lang.*
import groovyJCSP.*
import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelInputList fromChecker
    ChannelInput checkerReady
    ChannelOutput toNetworkManager
    void run(){
        def fromNM = fromNetworkManager.read()
        def alt = new ALT (fromChecker)
        while(true){
            def index = alt.select()
            switch (index) {
                case 0:
                    def result = fromChecker[0].read()
                    println(result)
                    break
                case 1:
                    def result = fromChecker[1].read()
                    println(result)
                    break
                case 2:
                    def result = fromChecker[2].read()
                    println(result)
                    break
                case 3:
                    def result = fromChecker[3].read()
                    println(result)
                    break
            }
        }
    }
}
