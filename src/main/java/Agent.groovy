import jcsp.lang.CSProcess
import jcsp.lang.ChannelInput
import jcsp.lang.ChannelOutput

class Agent implements CSProcess{
    ChannelInput fromParser
    ChannelOutput toParser
    void run(){
        def result = fromParser.read()
        toParser.write(result)
    }
}
