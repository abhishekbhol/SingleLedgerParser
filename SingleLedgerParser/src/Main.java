import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String csvFile = "D:\\Projects\\SingleLedgerParser\\testFiles\\test1.csv";
        String line = "";
        String cvsSplitBy = ",";

        List<Ledger> dataList = new ArrayList<Ledger>();
        HashMap<String, Integer> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                Ledger l = new Ledger();
                l.setAccountName(data[0]);
                l.setTransactionType(data[1].equals("Credit")  ? TransactionType.Credit : TransactionType.Debit);
                l.setAmount(Integer.parseInt(data[2]));

                dataList.add(l);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        for ( Ledger l: dataList) {

            if(map.containsKey(l.getAccountName()))
            {
                if(l.getTransactionType() == TransactionType.Credit)
                {
                    int newVal = map.get(l.getAccountName()) + l.getAmount();
                    map.replace(l.getAccountName(), newVal);
                }
                else
                {
                    int newVal = map.get(l.getAccountName()) - l.getAmount();
                    map.replace(l.getAccountName(), newVal);
                }
            }
            else
            {
                if(l.getTransactionType() == TransactionType.Credit) {
                    map.put(l.getAccountName(), l.getAmount());
                }
                else
                {
                    map.put(l.getAccountName(), -l.getAmount());
                }
            }
        }

        try {
            PrintWriter pw = new PrintWriter(new File("answer.csv"));
            StringBuilder sb = new StringBuilder();


            sb.append("Account Type");
            sb.append(',');
            sb.append("Value");
            sb.append('\n');

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                System.out.println("Key : " + key + ", Value : " + value);

                sb.append(key);
                sb.append(',');
                sb.append(value.toString());
                sb.append('\n');
            }

            pw.write(sb.toString());
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


    }
}
