import jcsp.lang.*

class EmailTester implements CSProcess{
    ChannelOutput OutputUser
    ChannelOutput OutputBody
    ChannelOutput OutputSubject
    void run(){
        def i = 0
        while(i < 10) {
            String user = "User One"
            String subject = "Subject from user One"
            String body = "body from user one: https://www.google.com/"
            OutputUser.write(user)
            OutputSubject.write(subject)
            OutputBody.write(body)
            i = i + 1
            sleep(2000)
        }
    }
}
