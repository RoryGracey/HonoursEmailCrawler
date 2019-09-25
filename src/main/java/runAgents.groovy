import groovyJCSP.*
import jcsp.lang.*
import jcsp.userIO.*

def int nodes = Ask.Int ("Number of Nodes ? ", 1, 9)
def int iterations = Ask.Int ("Number of Iterations ? ", 1, 9)
def String initialValue = Ask.string ( "Initial List Value ? ")

def One2OneChannel [] ring = Channel.createOne2One(nodes+1)
def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def serverUserToParser = Channel.one2one()
def serverSubjectToParser = Channel.one2one()
def serverBodyToParser = Channel.one2one()
def resultToServer = Channel.one2one()
One2OneChannel[]P2A = Channel.one2one()
def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def server = new Server(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), userOut: serverUserToParser.out(), subjectOut: serverSubjectToParser.out(), bodyOut: serverBodyToParser.out(), result: resultToServer.in())
def parser = new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), channelOutput: resultToServer.out())

def processNodes = (1 ..< nodes).collect { i -> new ProcessNode ( inChannel: ring[i].in(),
        outChannel: ring[i+1].out(),
        nodeId: i)
}

processNodes << new ProcessNode ( inChannel: ring[nodes].in(),
        outChannel: ring[0].out(),
        nodeId: nodes)

def rootNode = new AgentManager ( inChannel: ring[0].in(),
        outChannel: ring[1].out(),
        iterations: iterations,
        initialValue: initialValue)


def network = processNodes << rootNode << emailClient << server << parser

new PAR ( network ).run()