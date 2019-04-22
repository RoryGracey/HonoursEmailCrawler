import jcsp.lang.*

class Parser implements CSProcess{
    ChannelInput subjectIn
    ChannelInput bodyIn
    ChannelOutput channelOutput
    void run(){
        def receivedInput = subjectIn.read()
        def receivedBody = bodyIn.read()
        println(receivedInput)
        println(receivedBody)
        channelOutput.write("XD")
    }
}
