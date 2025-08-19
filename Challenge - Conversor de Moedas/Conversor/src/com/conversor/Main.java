package com.conversor;

import com.conversor.service.CurrencyConverter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyConverter converter = new CurrencyConverter();

    public static void main(String[] args) {
        exibirMenu();
    }

    private static void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("CONVERSOR DE MOEDAS");
            System.out.println("=".repeat(50));
            System.out.println("1. USD → EUR (Dólar Americano → Euro)");
            System.out.println("2. EUR → USD (Euro → Dólar Americano)");
            System.out.println("3. USD → BRL (Dólar Americano → Real Brasileiro)");
            System.out.println("4. BRL → USD (Real Brasileiro → Dólar Americano)");
            System.out.println("5. USD → ARS (Dólar Americano → Peso Argentino)");
            System.out.println("6. ARS → USD (Peso Argentino → Dólar Americano)");
            System.out.println("7. Conversão personalizada");
            System.out.println("8. Ver histórico de conversões");
            System.out.println("9. Limpar histórico");
            System.out.println("0. Sair");
            System.out.println("=".repeat(50));
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer

                switch (opcao) {
                    case 1:
                        converterMoeda("USD", "EUR");
                        break;
                    case 2:
                        converterMoeda("EUR", "USD");
                        break;
                    case 3:
                        converterMoeda("USD", "BRL");
                        break;
                    case 4:
                        converterMoeda("BRL", "USD");
                        break;
                    case 5:
                        converterMoeda("USD", "ARS");
                        break;
                    case 6:
                        converterMoeda("ARS", "USD");
                        break;
                    case 7:
                        converterPersonalizada();
                        break;
                    case 8:
                        exibirHistorico();
                        break;
                    case 9:
                        limparHistorico();
                        break;
                    case 10:
                        System.out.println("Saindo... Obrigado!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número válido!");
                scanner.nextLine(); // Limpar buffer
                opcao = -1;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private static void converterMoeda(String de, String para) {
        try {
            System.out.print("Digite o valor a ser convertido: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar buffer

            double resultado = converter.convertCurrency(valor, de, para);
            System.out.printf("✅ %.2f %s = %.2f %s%n", valor, de, resultado, para);

        } catch (Exception e) {
            System.out.println("❌ Erro na conversão: " + e.getMessage());
        }
    }

    private static void converterPersonalizada() {
        try {
            System.out.println("\nMoedas disponíveis: USD, EUR, GBP, JPY, CAD, AUD, CHF, CNY, BRL, ARS");

            System.out.print("Digite a moeda de origem (ex: USD): ");
            String de = scanner.nextLine().toUpperCase();

            System.out.print("Digite a moeda de destino (ex: EUR): ");
            String para = scanner.nextLine().toUpperCase();

            System.out.print("Digite o valor a ser convertido: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar buffer

            double resultado = converter.convertCurrency(valor, de, para);
            System.out.printf("✅ %.2f %s = %.2f %s%n", valor, de, resultado, para);

        } catch (Exception e) {
            System.out.println("❌ Erro na conversão: " + e.getMessage());
        }
    }

    private static void exibirHistorico() {
        List<String> historico = converter.getConversionHistory();
        if (historico.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            System.out.println("\n📋 Histórico de Conversões:");
            for (int i = 0; i < historico.size(); i++) {
                System.out.println((i + 1) + ". " + historico.get(i));
            }
        }
    }

    private static void limparHistorico() {
        converter.clearHistory();
        System.out.println("Histórico limpo com sucesso!");
    }
}