package brennerfsouza.ConsultaFIPE.model;

import java.util.Comparator;
import java.util.List;

public class ListFilter {

    public void listFilter(String textFilter, List<Data> items) {
        System.out.println("Lista de marcas com o filtro \n");
        items.stream()
                .filter(d -> d.name().toUpperCase().contains(textFilter.toUpperCase()))
                .sorted(Comparator.comparing(Data::code))
                .forEach(System.out::println);
    }
}
