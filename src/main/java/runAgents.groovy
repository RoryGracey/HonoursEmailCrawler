import groovyJCSP.*
import jcsp.lang.*


def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def serverUserToParser = Channel.one2one()
def serverSubjectToParser = Channel.one2one()
def serverBodyToParser = Channel.one2one()
def resultToServer = Channel.one2one()
def server2receiver = Channel.one2one()
def Parser2Network = Channel.one2one()
def Network2Parser = Channel.one2one()
def Network2QNetwork = Channel.one2one()
def QNetwork2Network = Channel.one2one()

def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def server = new Server(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), userOut: serverUserToParser.out(), subjectOut: serverSubjectToParser.out(), bodyOut: serverBodyToParser.out(), result: resultToServer.in(), toReciever: server2receiver.out())
def parser = new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), toNetworkManager: Parser2Network.out(), fromNetworkManager: Network2Parser.in(), channelOutput: resultToServer.out())
def NM = new NetworkManager(toParser: Network2Parser.out(), fromParser: Parser2Network.in(), toQuarantine: Network2QNetwork.out(), fromQuarantine: QNetwork2Network.in())
def QNM = new QuarantineNetworkManager(fromNetworkManager: Network2QNetwork.in(), toNetworkManager: QNetwork2Network.out())
def emailReceiver = new EmailReciever(channelInput: server2receiver.in())


def network = []
network.add(emailClient)
network.add(server)
network.add(parser)
network.add(NM)
network.add(QNM)
network.add(emailReceiver)

new PAR ( network ).run()