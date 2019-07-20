import groovyJCSP.*
import jcsp.lang.*


def outputUser = Channel.any2one()
def outputSubject = Channel.any2one()
def outputBody = Channel.any2one()
def serverUserToParser = Channel.one2one()
def serverSubjectToParser = Channel.one2one()
def serverBodyToParser = Channel.one2one()
def resultToServer = Channel.one2one()



def emailClient = new EmailTester(OutputUser: outputUser.out(), OutputSubject: outputSubject.out(), OutputBody: outputBody.out())
def server = new Server(userIn: outputUser.in(), subjectIn: outputSubject.in(), bodyIn: outputBody.in(), userOut: serverUserToParser.out(), subjectOut: serverSubjectToParser.out(), bodyOut: serverBodyToParser.out(), result: resultToServer.in())
def parser = new Parser(userIn: serverUserToParser.in(), subjectIn: serverSubjectToParser.in(), bodyIn: serverBodyToParser.in(), channelOutput: resultToServer.out())

def processList = [emailClient, server, parser]

new PAR (processList).run()
