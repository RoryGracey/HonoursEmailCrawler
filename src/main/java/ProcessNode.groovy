
import jcsp.lang.*

class ProcessNode implements CSProcess{

    def ChannelInput inChannel
    def ChannelOutput outChannel
    def ChannelOutput toParser
    def ChannelInput fromParser
    def int nodeId

    def One2OneChannel N2A = Channel.createOne2One()
    def One2OneChannel A2N = Channel.createOne2One()
    //def One2OneChannel parserToAgent = Channel.createOne2One()
    def One2OneChannel AgentToParser = Channel.createOne2One()
    void run() {
        def ChannelInput toAgentInEnd = N2A.in()
        def ChannelInput fromAgentInEnd = A2N.in()
        def ChannelOutput toAgentOutEnd = N2A.out()
        def ChannelOutput fromAgentOutEnd = A2N.out()
        def int localValue = nodeId
        while (true) {
            def theAgent = inChannel.read()
            theAgent.connect([fromAgentOutEnd, toAgentInEnd])
            def agentManager = new ProcessManager(theAgent)
            agentManager.start()
            def resultFromAgent = fromAgentInEnd.read()
            toParser.write(resultFromAgent)
            toAgentOutEnd.write(resultFromAgent)
            agentManager.join()
            theAgent.disconnect()
            outChannel.write(theAgent)
            println("ended loop.")
        }
    }
}