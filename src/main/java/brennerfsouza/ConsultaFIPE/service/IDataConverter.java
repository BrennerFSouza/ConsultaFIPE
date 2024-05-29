package brennerfsouza.ConsultaFIPE.service;

import java.util.List;

public interface IDataConverter {
    <T> T obtainData(String json, Class<T> tClass);

    <T> List<T> obtainList(String Json, Class<T> tClass);
}
