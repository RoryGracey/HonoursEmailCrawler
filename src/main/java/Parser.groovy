import jcsp.lang.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class Parser implements CSProcess{
    ChannelInput userIn
    ChannelInput subjectIn
    ChannelInput bodyIn
    ChannelOutput channelOutput
    ChannelOutput parserToAgentManager
    ChannelInput parserFromAgent
    void run(){
        def outputFromAgent = []
        def receivedUser = userIn.read()
        def receivedInput = subjectIn.read()
        def receivedBody = bodyIn.read()
        println(receivedUser)
        println(receivedInput)
        println(receivedBody)
        def result = (receivedBody =~/[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&\/\/=]*)/)[0]
        def parsedLink = result[0]
        channelOutput.write(parsedLink)
        parserToAgentManager.write("ready")
        def i = 0
        while(i ..< 4){
            i = i + 1
            outputFromAgent << parserFromAgent.read()
        }
        println("OUTPUT: " + outputFromAgent)
    }
}
