import groovyJCSP.*
import jcsp.lang.*

def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def Parser2Network = Channel.one2one()





def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def parser = new Parser(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), toNetworkManager: Parser2Network.out())
def NM = new NetworkManager(fromParser: Parser2Network.in())


def network = [emailClient, parser, NM]


new PAR ( network ).run()