import jcsp.lang.CSProcess
import jcsp.lang.*


class NetworkManager implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toParser
    ChannelOutput toQuarantine
    ChannelInput fromQuarantine
    void run(){
        def link = fromParser.read()
        println(link)
        toQuarantine.write(link)
        def qResponse = fromQuarantine.read()
        if(qResponse == 'OK'){
            toParser.write(qResponse)
        }else {
            toParser.write(qResponse)
        }
    }
}
