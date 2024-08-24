package org.me.main;

import org.me.models.Aluno;
import org.me.utils.DataProcessor;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import static java.lang.StringTemplate.STR;

public class Main {

    private static final String message = """
            ** CADASTRO DE ALUNOS **
            
            1 - Cadastrar alunos
            2 - Excluir aluno
            3 - Alterar aluno
            4 - Buscar Aluno pelo nome
            5 - Listar alunos (com status de aprovação
            6 - FIM
            
            """;

    public static void main(String[] args){
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Scanner scanner = new Scanner(System.in);

        int choice = 1;

        while(6 != choice){

            System.out.println(message);
            System.out.print("Digite a oção desejada: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    registerAluno();
                    break;
                case 2:
                    deleteAluno();
                    break;
                case 3:
                    updateAluno();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    listAllAlunos();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Digite um número de 1 a 6");
            }

        }


    }

    private static void registerAluno() {
        System.out.println("CADASTRO DE ALUNO:");

        Scanner scanner = new Scanner(System.in);
        Aluno a = new Aluno();
        System.out.print("Digite o nome: ");
        a.setNome(scanner.nextLine());
        System.out.print("Digite o RA: ");
        a.setRa(scanner.nextLine());
        System.out.print("Digite o email: ");
        a.setEmail(scanner.nextLine());
        System.out.print("Digite a nota 1: ");
        a.setNota1(scanner.nextBigDecimal());
        System.out.print("Digite a nota 2: ");
        a.setNota2(scanner.nextBigDecimal());
        System.out.print("Digite a nota 3: ");
        a.setNota3(scanner.nextBigDecimal());

        DataProcessor.registerAluno(a);
    }

    private static void deleteAluno() {
        System.out.println("EXCLUIR ALUNO:");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();

        if(DataProcessor.deleteAluno(name)){
            System.out.println("Aluno removido com sucesso!");
        }else{
            System.out.println("Aluno não encontrado");
        }

    }

    private static void updateAluno() {
        System.out.println("ALTERAR ALUNO: ");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();

        Aluno oldAluno = DataProcessor.selectAlunoByName(name);
        if(null == oldAluno){
            System.out.println("Aluno não encontrado!");
            return;
        }
        System.out.println("Dados do aluno:");
        System.out.println(STR."Nome: \{oldAluno.getNome()}");
        System.out.println(STR."Email: \{oldAluno.getEmail()}");
        System.out.println(STR."RA: \{oldAluno.getRa()}");
        System.out.println(STR."Nome: \{oldAluno.getNota1()} - \{oldAluno.getNota2()} - \{oldAluno.getNota3()}");

        System.out.println("NOVOS DADOS: ");

        Aluno newAluno = new Aluno();
        System.out.print("Digite o nome: ");
        newAluno.setNome(scanner.nextLine());
        System.out.print("Digite o RA: ");
        newAluno.setRa(scanner.nextLine());
        System.out.print("Digite o email: ");
        newAluno.setEmail(scanner.nextLine());
        System.out.print("Digite a nota 1: ");
        newAluno.setNota1(scanner.nextBigDecimal());
        System.out.print("Digite a nota 2: ");
        newAluno.setNota2(scanner.nextBigDecimal());
        System.out.print("Digite a nota 3: ");
        newAluno.setNota3(scanner.nextBigDecimal());

        DataProcessor.updateAluno(oldAluno, newAluno);
    }

    private static void searchByName() {
        System.out.println("CONSULTAR ALUNO: ");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();

        Aluno a = DataProcessor.selectAlunoByName(name);
        if(null == a){
            System.out.println("Aluno não encontrado!");
            return;
        }
        System.out.println("Dados do aluno:");
        System.out.println(STR."Nome: \{a.getNome()}");
        System.out.println(STR."Email: \{a.getEmail()}");
        System.out.println(STR."RA: \{a.getRa()}");
        System.out.println(STR."Nome: \{a.getNota1()} - \{a.getNota2()} - \{a.getNota3()}");
        System.out.println("------------------------------");
    }

    private static void listAllAlunos(){
        System.out.println("Exibindo todos os alunos:");

        List<Aluno> alunos = DataProcessor.listAllAlunos();

        for(Aluno a : alunos){
            BigDecimal soma = a.getNota1().add(a.getNota2()).add(a.getNota3());
            BigDecimal media = soma.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
            String situcacao;
            if (0 > media.compareTo(BigDecimal.valueOf(4))){
                situcacao = "Reprovado";
            }else if(0 > media.compareTo(BigDecimal.valueOf(6))){
                situcacao = "Recuperação";
            }else{
                situcacao = "Aprovado";
            }

            System.out.println("-----------------");
            System.out.println(STR."Nome: \{a.getNome()}");
            System.out.println(STR."Email: \{a.getEmail()}");
            System.out.println(STR."RA: \{a.getRa()}");
            System.out.println(STR."Nome: \{a.getNota1()} - \{a.getNota2()} - \{a.getNota3()}");
            System.out.println(STR."Media: \{media}");
            System.out.println(STR."Situação: \{situcacao}");
        }
        System.out.println("------------------------------");
    }
}
