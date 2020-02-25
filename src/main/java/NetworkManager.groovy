import groovyJCSP.ALT
import jcsp.lang.CSProcess
import jcsp.lang.*
import jcsp.net2.*
import jcsp.net2.tcpip.TCPIPNodeAddress

class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toQuarantine
    def fromQuarantine = NetChannel.net2one()
    void run(){
        def jobs = []
        def alt = new ALT([fromParser, fromQuarantine])
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
