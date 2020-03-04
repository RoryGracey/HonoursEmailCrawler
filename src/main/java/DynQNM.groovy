import jcsp.lang.*
import groovyJCSP.*
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

class DynQNM implements CSProcess{
    def toNMC
    def fromNMLocation
    def fromNMC
    ChannelInput fromCheckers
    def checkerNum = 0

    def chan1 = Channel.one2oneArray(4 )
    def CheckerOut = new ChannelOutputList(chan1)

    def controllerIP = '146.176.75.72'

    def toNetworkBuffer = new TCPIPNodeAddress(controllerIP, 3015)
    def toNetBufferChan = NetChannel.any2net(toNetworkBuffer, 52)

    void run(){
        while(true) {
            // create alt between checkers and NM so we can see when a checker process terminates
            toNMC.write(fromNMLocation)
            def alt = new ALT([fromNMC, fromCheckers])
            println "going to alt"
            toNMC.write('RequestJob')
            def index = alt.select()
            switch (index){
                case 0:
                    def job = fromNMC.read()
                    println "job"
                    if (job && checkerNum < 5) {
                        checkerNum = checkerNum + 1
                        def newChecker = new DynChecker(toNBChan: toNetBufferChan, channelInput: chan1[checkerNum].in(),
                                channelOutput: chan1[checkerNum].out(), nodeID: checkerNum)

                        CheckerOut[checkerNum].write(job)
                        println "wrote job"
                        break
                    }
                    break
                case 1:
                    def cResponse = fromCheckers.read()
                    checkerNum = checkerNum - 1
                    break
            }
        }
    }
}
