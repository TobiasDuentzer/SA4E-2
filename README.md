Aufgabe 2.1

main.java started das Programm 

Nach dem Start wird auf eine Konsoleneingabe gewartet.

Wenn "1" eingegben wird, wechselt der Output zu der File "output.txt".

Wenn "2" eingegben wird, wechselt der Output zu MQTT

  broker="tcp://localhost:1883"
  
  topic="test"
  
Alle anderen Eingaben fuehren dazu, dass der Outupt in der Konsole ausgegeben wird.


Nachdem der Output ausgewaehlt wurde, startet der PuzzleSolver.java und versucht das Raetsel zu loesen.

Dabei wird jeder Test ausgegeben solange bis eine korrekte Loesung gefunden wurde.



Aufgabe 2.2

CamelApplication.java startet die Anwendung.

Ein Error konnte ich nicht loesen:

Caused by: java.lang.IllegalArgumentException: gRPC service class not found: org.example.SolvingPuzzleGrpc

Deshalb kann sich die Anwendung nicht mit dem gRPC Server verbinden. Dennoch hab ich den Code soweit wie moeglich versucht zu schreiben. Ich habe ebenfalls die services.proto hinzu gefuegt, inder der Service des gRPC-Servers definiert ist. Wenn ich es richtig verstanden habe muss die message, vor dem senden zum gPRC-Server, noch zu protobuf konvertiert werden, welches ich aber nicht geschafft habe.

Ich habe den gRPC Schritt mit manueller Eingabe uebersprungen um zu testen ob der Rest der Anwendung funktioniert, welches der Fall ist.

MtoGRoute.java ist der erste Teil der Aufgabe, indem von einem MQTT-Server das Raetsel empfangen wird, zu einem String gekuerzt und an den gRPC-Server weitergeleitet wird. Der String setzt sich aus der raetsel_id und dem Raetsel zusammen: "raetsel_id v1,v2,v3,v4,v5,v6,v7,v8,v9"
Dies ist die Form, welche mein Puzzelsolver benoetigt.

GtoMRoute.java ist der zweite Teil, indem die Loesung von dem gRPC-Server empfangen wird, mittels Prozess erweitert und in zurueck json umgewandelt wird.
Empfangen wird der String: "raetsel_id v1,v2,v3 v4,v5,v6 v7,v8,v9 time"
Dieser wird gesplitted und zu gewollter json umgewandelt und ergaenzt.

MtoMRoute.java ist der vollstaendige Prozess indem von MQTT ueber gRCP zurueck zu MQTT gesendet wird.
Vollstaendige Projekte:
https://drive.google.com/drive/folders/1H09yUW_e9gVTwloPeJi789w5TrNjXDRH?usp=sharing
