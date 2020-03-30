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
    def ChanOutPass
    def chan1 = Channel.one2oneArray(4 )
    def CheckerOut = new ChannelOutputList(chan1)

    def controllerIP = '146.176.75.72'

    def toNetworkBuffer = new TCPIPNodeAddress(controllerIP, 3015)
    def toNetBufferChan = NetChannel.any2net(toNetworkBuffer, 52)
    def processQueue = true
    def running = true
    void run(){
        toNMC.write(fromNMLocation)
        while(running) {
            // create alt between checkers and NM so we can see when a checker process terminates
            toNMC.write("Request Job")
            def job = fromNMC.read()
            println checkerNum
            if (job && checkerNum < 4) {
                processQueue = true
                def newChecker = new DynChecker(toNBChan: toNetBufferChan, channelInput: chan1[checkerNum].in(),
                        channelOutput: ChanOutPass.out(), nodeID: checkerNum)
                def processManager = new ProcessManager(newChecker)
                processManager.start()
                CheckerOut[checkerNum].write(job)
                checkerNum = checkerNum + 1
                
            }
        }
    }
}
