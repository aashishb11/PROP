JAVAC=/usr/bin/javac
JAR=/usr/bin/jar
CP=/bin/cp
MKDIR=/bin/mkdir
RM=/bin/rm
.SUFFIXES: .java .class

.java.class:
	$(JAVAC) -d ./classes/ $*.java

CLASSES = \
	main/CapaDades/CtrlPersistencia.java \
	main/CapaDomini/Controllers/CtrlDomini.java \
	main/CapaDomini/Models/Assistance.java \
	main/CapaDomini/Models/FiveGuess.java \
	main/CapaDomini/Models/GameSave.java \
	main/CapaDomini/Models/GeneticComputerPlayer.java \
	main/CapaDomini/Models/Human.java \
	main/CapaDomini/Models/Maquina.java \
	main/CapaDomini/Models/MastermindBoard.java \
	main/CapaDomini/Models/Player.java \
	main/CapaDomini/Models/Ranking.java \
	main/CapaPresentacio/Controllers/CtrlPresentacion.java \
	main/CapaPresentacio/Vista/MainWindow.java \
	main/CapaPresentacio/Vista/vistaAssistance.java \
	main/CapaPresentacio/Vista/vistaGame.java \
	main/CapaPresentacio/Vista/vistaLoadGame.java \
	main/CapaPresentacio/Vista/vistaNewGame.java \
	main/CapaPresentacio/Vista/vistaRecords.java \
	main/CapaPresentacio/Vista/vistaSettings.java \
	main/CapaPresentacio/Vista/Mastermind.java

default: classes

classes:
	$(MKDIR) -p ../classes
	$(JAVAC) -d ../classes $(CLASSES)

jar: classes
	mkdir -p ../EXE
	$(JAR) cvfe ../EXE/Mastermind.jar main.CapaPresentacio.Vista.Mastermind -C ../classes .
	$(CP) ../../ranking.txt ../EXE/
	$(CP) ../../ranking.txt ../FONTS/

clean:
	$(RM) -rf ../classes
	rm -f ../EXE/ranking.txt
	rm -f ../FONTS/ranking.txt

run: jar
	$(CP) ../../ranking.txt ../EXE/
	$(CP) ../../ranking.txt ../FONTS/
	java -jar ../EXE/Mastermind.jar
