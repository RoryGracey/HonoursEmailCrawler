import groovyJCSP.*
import jcsp.lang.*

def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def NB2Receiver = Channel.one2one()
def Parser2Network = Channel.one2one()
def QNetwork2Network = Channel.one2one()
def checker2Buffer = Channel.any2one()
def NB2NM = Channel.one2one()


def NM2QNM = Channel.one2one()


def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def parser = new Parser(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), toNetworkManager: Parser2Network.out())
def NM = new DynNetManagerNonNet(fromParser: Parser2Network.in(), fromQuarantine: QNetwork2Network.in(), toQuarantine: NM2QNM.out(), fromNB: NB2NM.in())
def QNM = new DynQNMNonNet(fromNMC: NM2QNM.in(), toNMC: QNetwork2Network.out(), toNetBufferChan: checker2Buffer)
def NB = new DynNetBufferNonNet(fromCheckerProcesses: checker2Buffer.in(), toReceiver: NB2Receiver.out(), toNM: NB2NM.out())
def emailReceiver = new EmailReciever(channelInput: NB2Receiver.in())


def network = [emailClient, parser, NM, QNM, NB, emailReceiver]


new PAR ( network ).run()