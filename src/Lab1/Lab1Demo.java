package Lab1;

        public class Lab1Demo {
            public static void main(String[] args) {
                PaymentService service = new PaymentService();
                Customer c = new Customer("C001", "Louange", "0788000000","iteritekalouange1@gmail.com ");
                service.addAccount(new WalletAccount("ACC001", c, 100000));

                service.deposit("ACC001", 5000, "REF001");   // works

                try {
                    service.deposit("ACC001", 5000, "REF001");   // same ID: rejected
                } catch (IllegalStateException e) {
                    System.out.println("Rejected: " + e.getMessage());
                }

                System.out.println(service.getAccount("ACC001").getBalance());  // 105000, not 110000
                service.getHistory("ACC001").forEach(System.out::println);      // 1 transaction
                service.getFailedTransactions().forEach(System.out::println);   // 1 failure

            }
        }
