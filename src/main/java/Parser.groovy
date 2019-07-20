import jcsp.lang.*

class Parser implements CSProcess{
    ChannelInput userIn
    ChannelInput subjectIn
    ChannelInput bodyIn
    ChannelOutput channelOutput
    void run(){
        def receivedUser = userIn.read()
        def receivedInput = subjectIn.read()
        def receivedBody = bodyIn.read()
        println(receivedUser)
        println(receivedInput)
        println(receivedBody)
        channelOutput.write("XD")
    }
}
