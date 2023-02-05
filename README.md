Aufgabe 2.1

main.java started das Programm 

Nach dem Start wird auf eine Konsoleneingabe gewartet.

Wenn "1" eingegben wird, wechselt der Output zu der File "output.txt".

Wenn "2" eingegben wird, wechselt der Output zu MQTT

  broker="tcp://localhost:1883"
  
  topic="test"
  
Alle anderen Eingaben führen dazu, dass der Outupt in der Konsole ausgegeben wird.


Nachdem der Output ausgewählt wurde, startet der PuzzleSolver.java und versucht das Raetsel zu loesen.

Dabei wird jeder Test ausgegeben solange bis eine korrekte Loesung gefunden wurde.


Aufgabe 2.2

CamelApplication.java startet die Anwendung.

Vorab, ich konnte ein Problem nicht Lösen:

Caused by: java.lang.IllegalArgumentException: gRPC service class not found: org.example.SolvingPuzzleGrpc

Deshalb kann sich die Anwendung nicht mit dem gRPC Server verbinden. Dennoch hab ich den Code soweit wie möglich versucht zu schreiben. Ich habe ebenfalls die services.proto hinzu gefuegt, inder der Service definiert ist.

Ich habe den gRPC Schritt mit manueller Eingabe uebersprungen um zu testen ob der Rest der Anwendung funktioniert, welches der Fall ist.

MtoGRoute.java ist der erste Teil der Aufgabe, indem von einem MQTT-Server das Raetsel empfangen wird, zu einem String gekuerzt und an den gRPC-Server weitergeleitet wird.

GtoMRoute.java ist der zweite Teil, indem die Lösung von dem gRPC-Server empfangen wird, mittels Prozess erweitert und in zurück json umgewandelt wird.

MtoMRoute.java ist der vollständige Prozess indem von MQTT über gRCP zurück zu MQTT gesendet wird.
