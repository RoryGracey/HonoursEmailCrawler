import groovyJCSP.ALT
import groovyJCSP.ChannelOutputList
import jcsp.lang.CSProcess
import jcsp.lang.*
import jcsp.net2.*
import jcsp.net2.tcpip.TCPIPNodeAddress

class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toQuarantine
    void run(){
        def qnmIDs = 1
        def qnmsConnected = 0
        def nodeAddr = new TCPIPNodeAddress(3000)
        Node.getInstance().init(nodeAddr)
        println "controller ip address = ${nodeAddr.getIpAddress()}"
        def fromQNMs = NetChannel.net2one()
        def fromQNMLoc = fromQNMs.getLocation()
        def qnmList = new ChannelOutputList()
        qnmAmount = 1
        for (p in 0..<1) qnmList.append(null)
        if(qnmsConnected == 0){
            fromQNMs.read()

        }
        def jobs = []
        def alt = new ALT([fromParser])
        while(true) {
            def index = alt.select()
            switch (index){
                case 0:
                    jobs << fromParser.read()
                    break
                case 1:
                    def jobR = fromQuarantine.read()
                    if (jobs.size() != 0)
                        toQuarantine.write(jobs.pop())
                    break
            }
        }
    }
}
