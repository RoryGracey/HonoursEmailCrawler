import jcsp.lang.*

class Server implements CSProcess{
    ChannelInput userIn
    ChannelInput subjectIn
    ChannelInput bodyIn
    ChannelInput result
    ChannelOutput userOut
    ChannelOutput subjectOut
    ChannelOutput bodyOut
    ChannelOutput toReciever
    void run(){
        String user = userIn.read()
        String subject = subjectIn.read()
        String body = bodyIn.read()
        //println "$user ..  $subject .. $body"
        userOut.write(user)
        subjectOut.write(subject)
        bodyOut.write(body)
        def resultFromParser = result.read()
        toReciever.write(user + ": " + subject + ": "+ body + ": " + resultFromParser)
        println(resultFromParser)
    }
}
