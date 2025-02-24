import jcsp.lang.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class Parser implements CSProcess{
    ChannelInput userIn
    ChannelInput subjectIn
    ChannelInput bodyIn
    ChannelOutput toNetworkManager
    void run(){
        while(true) {
            def receivedUser = userIn.read()
            def receivedInput = subjectIn.read()
            def receivedBody = bodyIn.read()
            def result = (receivedBody =~ /https?:\\/\\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&\/\/=]*)|DONE/)[0]
            def parsedLink = result[0]
            //println(parsedLink)
            toNetworkManager.write(parsedLink)
        }
    }
}
