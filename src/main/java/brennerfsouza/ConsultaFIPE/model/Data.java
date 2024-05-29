package brennerfsouza.ConsultaFIPE.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Data(@JsonAlias("codigo") String code,
                   @JsonAlias("nome") String name) {

    @Override
    public String toString() {
        return "code='" + code + '\'' +
                ", name='" + name + '\'' ;
    }
}
