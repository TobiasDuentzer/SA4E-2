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
