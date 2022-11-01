/*• Napisz program odtwarzający wybrany przez siebie prosty
        scenariusz z bloku Bazy Danych SQL.
        • Zrób to w ramach osobnego projektu (przećwicz dodawanie zależności).
        • Utwórz klasy odpowiadające modelowi danych i na nich operuj (przekazuj
        kompletne obiekty metodom wykonującym zapis i odczyt).
        • Wykorzystaj mechanizm transakcji.
        • ** Przygotuj minimalistyczną testową bazę danych i napisz testy
        integracyjne.

        Zadanie domowe 3
W ramach poprzedniego bloku robiliście różne zapytania, bardziej lub mniej złożone.
Wróćcie do nich wybierzcie sobie jedno z nich (lub cały scenariusz  - zależnie od tego na co się czujecie),
utwórzcie nowe projekty połączcie się z tą bazą z której korzystaliście wcześniej i spróbujcie programowo wykonać
tamte zapytania. Możecie spróbować stworzyć klasy reprezentujące model danych, spróbować ładować dane do klas,
zapisywać, podjąć próbę wykonania update (nie ćwiczyliśmy tego na zajęciach, ale używa się do tego metody
executeUpdate  klasy PreparedStatement i parametryzuje w identyczny sposób jak inserty), może poeksperymentować
z transakcjami.
Zadanie o charaketrze otwartym i będzie stanowiło tak duże wyzwanie, jak sami zdecydujecie. Głównym celem jest to,
żebyście - zamiast pracować w szablonie - zrobili nowy projekt od zera i zobaczyli jak to działa. Jedyną zależnością
którą trzeba dodać jest sterownik JDBC :slightly_smiling_face: Nie musicie wczytywać danych połączenia z pliku,
spokojnie możecie je zahardkodować.
Dużo większym wyzwaniem będzie napisanie testów do tego - macie sporo przykładów w naszym projekcie, ale nie
wgryzaliśmy się jak te testy działają. Jeśli ktoś podejmie taką próbę, niech woła tu na kanale, podpowiem co trzeba
zrobić :slightly_smiling_face:
Chciałbym, żeby każdy zrobił to zadanie choćby miało polegać na wykonaniu prostego selecta i wypisaniu wyników na System.out.
        */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Homework3 {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sda", "java124", "test123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sda.books;");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }
}
