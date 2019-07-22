import groovyJCSP.*
import jcsp.lang.*


int agents = 2
def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def serverUserToParser = Channel.one2one()
def serverSubjectToParser = Channel.one2one()
def serverBodyToParser = Channel.one2one()
def resultToServer = Channel.one2one()
One2OneChannel[]P2A = Channel.one2one()
def agentList = []
def parserList = []
def i = 0
def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def server = new Server(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), userOut: serverUserToParser.out(), subjectOut: serverSubjectToParser.out(), bodyOut: serverBodyToParser.out(), result: resultToServer.in())
//def parser = new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), channelOutput: resultToServer.out())
while(i < agents){
    parserList << new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), channelOutput: resultToServer.out())
    agentList << new Agent(fromParser: P2A[i].in(), toParser: P2A[i].out())
    i = i + 1
}
def processList = [emailClient, server]



new PAR (processList + agentList).run()
