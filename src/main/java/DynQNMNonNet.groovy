import groovyJCSP.ALT
import groovyJCSP.ChannelOutputList
import jcsp.lang.CSProcess
import jcsp.lang.Channel
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput
import jcsp.lang.ProcessManager
import jcsp.net2.NetChannel
import jcsp.net2.tcpip.TCPIPNodeAddress

class DynQNMNonNet implements CSProcess{
    ChannelInput fromNMC
    ChannelOutput toNMC
    ChannelInput fromNB
    def checkerNum = 0
    def ChanOutPass
    def chan1 = Channel.one2oneArray(4 )
    def CheckerOut = new ChannelOutputList(chan1)
    def toNetBufferChan
    def split
    def response
    void run(){
        while(true) {
                if (checkerNum < 4) {
                    toNMC.write("Request Job")
                    response = fromNMC.read()
                    if (response)
                        split = response.split('-')

                    if(split[0] != 'NOJ') {
                        def newChecker = new DynChecker(toNBChan: toNetBufferChan, channelInput: chan1[(int) checkerNum].in(), nodeID: checkerNum)
                        def processManager = new ProcessManager(newChecker)
                        processManager.start()
                        CheckerOut[(int) checkerNum].write(split[0])
                        checkerNum = checkerNum + 1
                    }
                }else{
                    toNMC.write("NOC")
                    response = fromNMC.read()
                    if (response) {
                        split = response.split('-')
                        def inte = Integer.parseInt(split[1])
                        if(checkerNum > 0) {
                            checkerNum = checkerNum - inte
                        }
                    }
                }
        }
    }
}
