import jcsp.lang.*

class EmailTester implements CSProcess{
    ChannelOutput OutputUser
    ChannelOutput OutputBody
    ChannelOutput OutputSubject
    void run(){
        def i = 0
        try (BufferedReader br = new BufferedReader(new FileReader('txtFile.txt'))) {
            String line;
            while ((line = br.readLine()) != null) {
                String user = "User One"
                String subject = "Subject from user One"
                String body = "body from user one: " + line
                if(line == "DONE"){
                    println("file finished")
                }
                OutputUser.write(user)
                OutputSubject.write(subject)
                OutputBody.write(body)
                i = i + 1
                sleep(100)
            }
        }
    }
}
