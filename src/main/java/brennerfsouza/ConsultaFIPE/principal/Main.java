package brennerfsouza.ConsultaFIPE.principal;

import brennerfsouza.ConsultaFIPE.model.Data;
import brennerfsouza.ConsultaFIPE.model.ListFilter;
import brennerfsouza.ConsultaFIPE.model.Models;
import brennerfsouza.ConsultaFIPE.model.Vehicle;
import brennerfsouza.ConsultaFIPE.service.ConsumeAPI;
import brennerfsouza.ConsultaFIPE.service.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner reader = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConverter converter = new DataConverter();
    ListFilter listFilter = new ListFilter();
    List<Vehicle> vehicleList = new ArrayList<>();
    private String anddressComplement;
    private int operation = 0;
    private final String anddress = "https://parallelum.com.br/fipe/api/v1/";
    private String finalAnddress;
    private String brandCode;
    private String modelCode;
    private String filter;

    public void showMenu() {


        System.out.println("Bem vindo ao ConsultaFIPE!\n\n");

        while (operation != 4) {
            System.out.println("*** Menu ***\n");
            System.out.println("1 - Consultar Carro");
            System.out.println("2 - Consultar Caminhão");
            System.out.println("3 - Consultar Moto");
            System.out.println("4 - Sair\n");
            System.out.println("Escolha uma opção:");
            operation = reader.nextInt();
            reader.nextLine();


            switch (operation) {
                case 1:
                    finalAnddress = anddress + "carros/marcas";
                    break;
                case 2:
                    finalAnddress = anddress + "caminhoes/marcas";
                    break;
                case 3:
                    finalAnddress = anddress + "motos/marcas";
                    break;
                case 4:
                    System.out.println("Sistema sendo finalizado");
                    break;
                default:
                    System.out.println("Opção não valida!");
            }

            if (operation > 0 && operation < 4) {
                var json = consumeAPI.dataObtain(finalAnddress);
                System.out.println(json);
                var brands = converter.obtainList(json, Data.class);

                brands.stream()
                        .sorted(Comparator.comparing(Data::code))
                        .forEach(System.out::println);


                System.out.println("""
                        Digite parte da marca que deseja filtrar
                        Ou
                        Aperte Enter para continuar""");
                filter = reader.nextLine();

                if (filter != "") {
                    listFilter.listFilter(filter, brands);
                }

                System.out.println("Digite o codigo da marca:");
                brandCode = reader.nextLine();

                finalAnddress = finalAnddress + "/" + brandCode + "/modelos";

                json = consumeAPI.dataObtain(finalAnddress);
                System.out.println(json);

                var modelsList = converter.obtainData(json, Models.class);

                System.out.println("Modelos da marca");

                modelsList.models().stream()
                        .sorted(Comparator.comparing(Data::code))
                        .forEach(System.out::println);


                System.out.println("""
                        Digite parte da modelo que deseja filtrar
                        Ou
                        Aperte Enter para continuar""");
                filter = reader.nextLine();

                if (filter != "") {
                    listFilter.listFilter(filter, modelsList.models());
                }

                System.out.println("Digite o codigo do modelo:");
                modelCode = reader.nextLine();

                finalAnddress = finalAnddress + "/" + modelCode + "/anos";

                json = consumeAPI.dataObtain(finalAnddress);
                //System.out.println(json);

                var years = converter.obtainList(json, Data.class);


//                years.stream()
//                        .sorted(Comparator.comparing(Data::code))
//                        .forEach(
//
//                                e -> System.out.println(consumeAPI.dataObtain(finalAnddress + "/" + e.code()))
//                        );

                for (int i = 0; i < years.size(); i++) {
                    var anddressYear = finalAnddress + "/" + years.get(i).code();
                    json = consumeAPI.dataObtain(anddressYear);
                    Vehicle vehicle = converter.obtainData(json, Vehicle.class);
                    vehicleList.add(vehicle);
                }
                System.out.println("\nTodos os veiculos com o valor por ano: \n");
                vehicleList.forEach(System.out::println);
                System.out.println("Aperte Enter para retornar ao Menu");
                reader.nextLine();
            }
        }

    }
}

