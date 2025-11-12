// package com.example.sistema_agendamento_poli.config;

// import com.example.sistema_agendamento_poli.bloco.Bloco;
// import com.example.sistema_agendamento_poli.bloco.BlocoDTO;
// import com.example.sistema_agendamento_poli.bloco.BlocoService;
// import com.example.sistema_agendamento_poli.sala.SalaDTO;
// import com.example.sistema_agendamento_poli.sala.SalaService;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.List;
// import java.util.Optional;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     private final BlocoService blocoService;
//     private final SalaService salaService;

//     public DataInitializer(BlocoService blocoService, SalaService salaService) {
//         this.blocoService = blocoService;
//         this.salaService = salaService;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         // cria blocos e salas apenas se estiver vazio
//         if (salaService.listarSalas().isEmpty()) {
//             // cria blocos se não existirem
//             List<Bloco> blocos = blocoService.listarBlocos();
//             Bloco blocoA;
//             Optional<Bloco> foundA = blocos.stream().filter(b -> "A".equalsIgnoreCase(b.getNome())).findFirst();
//             if (foundA.isPresent()) {
//                 blocoA = foundA.get();
//             } else {
//                 BlocoDTO dtoA = BlocoDTO.builder().nome("A").qt_Salas(10).build();
//                 blocoService.criarBloco(dtoA);
//                 blocoA = blocoService.listarBlocos().stream().filter(b -> "A".equalsIgnoreCase(b.getNome())).findFirst().orElse(null);
//             }

//             Bloco blocoB;
//             Optional<Bloco> foundB = blocoService.listarBlocos().stream().filter(b -> "B".equalsIgnoreCase(b.getNome())).findFirst();
//             if (foundB.isPresent()) {
//                 blocoB = foundB.get();
//             } else {
//                 BlocoDTO dtoB = BlocoDTO.builder().nome("B").qt_Salas(8).build();
//                 blocoService.criarBloco(dtoB);
//                 blocoB = blocoService.listarBlocos().stream().filter(b -> "B".equalsIgnoreCase(b.getNome())).findFirst().orElse(null);
//             }

//             // cria algumas salas de exemplo vinculadas aos blocos criados
//             if (blocoA != null) {
//                 SalaDTO s1 = SalaDTO.builder().codigo("A101").nome("Laboratório A101").andar(1).blocoId(blocoA.getId()).build();
//                 SalaDTO s2 = SalaDTO.builder().codigo("A102").nome("Sala de Aula A102").andar(1).blocoId(blocoA.getId()).build();
//                 salaService.criarSala(s1);
//                 salaService.criarSala(s2);
//             }
//             if (blocoB != null) {
//                 SalaDTO s3 = SalaDTO.builder().codigo("B201").nome("Auditório B201").andar(2).blocoId(blocoB.getId()).build();
//                 SalaDTO s4 = SalaDTO.builder().codigo("B202").nome("Sala de Reuniões B202").andar(2).blocoId(blocoB.getId()).build();
//                 salaService.criarSala(s3);
//                 salaService.criarSala(s4);
//             }

//             System.out.println("DataInitializer: blocos e salas de exemplo criados.");
//         } else {
//             System.out.println("DataInitializer: salas já existem — pulando criação.");
//         }
//     }
// }