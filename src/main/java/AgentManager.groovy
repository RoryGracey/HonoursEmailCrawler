import jcsp.lang.*
import groovyJCSP.*

class AgentManager implements CSProcess{

    def ChannelInput inChannel
    def ChannelOutput outChannel
    def int iterations
    def String initialValue

    void run() {
        def One2OneChannel N2A = Channel.createOne2One()
        def One2OneChannel A2N = Channel.createOne2One()
        def ChannelInput toAgentInEnd = N2A.in()
        def ChannelInput fromAgentInEnd = A2N.in()
        def ChannelOutput toAgentOutEnd = N2A.out()
        def ChannelOutput fromAgentOutEnd = A2N.out()

        def theAgent = new Agent( result: [initialValue])

        for ( i in 1 .. iterations) {
            outChannel.write(theAgent)
            theAgent = inChannel.read()
            theAgent.connect ( [fromAgentOutEnd, toAgentInEnd ] )
            def agentManager = new ProcessManager (theAgent)
            agentManager.start()
            def returnedResults = fromAgentInEnd.read()
            println "Root: Iteration: $i is $returnedResults and initial value of $initialValue  "
            returnedResults << "end of " + i
            toAgentOutEnd.write (returnedResults)
            agentManager.join()
            theAgent.disconnect()
        }
    }
}