import jcsp.lang.*

class EmailTester implements CSProcess{
    ChannelOutput OutputUser
    ChannelOutput OutputBody
    ChannelOutput OutputSubject
    void run(){
        String user = "User One"
        String subject = "Subject from user One"
        String body = "body from user one: https://www.google.com/"
        OutputUser.write(user)
        OutputSubject.write(subject)
        OutputBody.write(body)

    }
}
