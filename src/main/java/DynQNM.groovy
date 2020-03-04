import jcsp.lang.*
import groovyJCSP.*

class DynQNM implements CSProcess{
    def toNMC
    def fromNMLocation
    def fromNMC
    //def CheckerOutput = new ChannelOutputList()
    //def CheckerInput = new ChannelInputList()
    def checkerNum = 0

    def chan1 = Channel.one2oneArray(4 )
    def CheckerOut = new ChannelOutputList(chan1)

    def chan2 = Channel.one2oneArray(checkerNum + 2)
    def CheckerIn = new ChannelInputList(chan2)

    void run(){
        while(true) {
            toNMC.write('RequestJob')
            // create alt between checkers and NM so we can see when a checker process terminates
            def job = fromNMC.read()
            if (job && checkerNum < 5) {
                checkerNum = checkerNum + 1
                def newChecker = new DynChecker(channelInput: chan1[checkerNum].in(), channelOutput: chan2[checkerNum].out()
                        , nodeID: checkerNum)
                newChecker.run()
                CheckerOut[checkerNum].write(job)
                break
            }
        }
    }
}
