import java.util.*;
import java.io.*;

public class bai4 {
  public static Map<String, Integer> vocab = new HashMap<>();
  public static Map<String, Integer> corpus = new HashMap<>();
  public static Map<String, Integer> pairCorpus = new HashMap<>();
  public static double[] probs;
  public static double[][] conditionalProbs;

  public static void readFile(String filename) {
    try {
      List<String> lines = new ArrayList<>();
      File file = new File(filename);
      Scanner fileScanner = new Scanner(file);

      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine().trim().toLowerCase();
        lines.add(line);
      }
      fileScanner.close();

      int wordId = 0;
      for (String line : lines) {
        String[] words = line.split("\\s+");
        for (String word : words) {
          corpus.put(word, corpus.getOrDefault(word, 0) + 1);
          if (!vocab.containsKey(word)) {
            vocab.put(word, wordId++);
          }
        }
        for (int i = 0; i < words.length - 1; i++) {
          String bigram = words[i] + "_" + words[i + 1];
          pairCorpus.put(bigram, pairCorpus.getOrDefault(bigram, 0) + 1);
        }
      }

      // // Debugging: In từ vựng và số lần xuất hiện
      // System.out.println("Từ vựng và số lần xuất hiện:");
      // for (Map.Entry<String, Integer> entry : corpus.entrySet()) {
      //   System.out.println(entry.getKey() + " : " + entry.getValue());
      // }

      // // Debugging: In bigram và số lần xuất hiện
      // System.out.println("\nCác bigram và số lần xuất hiện:");
      // for (Map.Entry<String, Integer> entry : pairCorpus.entrySet()) {
      //   System.out.println(entry.getKey() + " : " + entry.getValue());
      // }

    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filename);
      e.printStackTrace();
    }
  }

  public static void constructSingleProb() {
    int totalWords = corpus.values().stream().mapToInt(Integer::intValue).sum();
    probs = new double[vocab.size()];
    for (Map.Entry<String, Integer> entry : corpus.entrySet()) {
      probs[vocab.get(entry.getKey())] = (double) entry.getValue() / totalWords;
    }

    // // Debugging: In xác suất của từng từ
    // System.out.println("\nXác suất của từng từ:");
    // for (Map.Entry<String, Integer> entry : corpus.entrySet()) {
    //   System.out.println(entry.getKey() + " : " + probs[vocab.get(entry.getKey())]);
    // }
  }

  public static void constructConditionalProb() {
    conditionalProbs = new double[vocab.size()][vocab.size()];
    for (Map.Entry<String, Integer> entry : pairCorpus.entrySet()) {
      String[] words = entry.getKey().split("_");
      if (vocab.containsKey(words[0]) && vocab.containsKey(words[1])) {
        int wordId1 = vocab.get(words[0]);
        int wordId2 = vocab.get(words[1]);
        conditionalProbs[wordId1][wordId2] = (double) entry.getValue() / corpus.get(words[0]);
      }
    }

    // // Debugging: In xác suất có điều kiện
    // System.out.println("\nXác suất có điều kiện:");
    // for (Map.Entry<String, Integer> entry : pairCorpus.entrySet()) {
    //   String[] words = entry.getKey().split("_");
    //   if (vocab.containsKey(words[0]) && vocab.containsKey(words[1])) {
    //     System.out.println(entry.getKey() + " : " + conditionalProbs[vocab.get(words[0])][vocab.get(words[1])]);
    //   }
    // }
  }

  public static void training() {
    constructSingleProb();
    constructConditionalProb();
  }

  public static List<String> inferring(String w0) {
    List<String> words = new ArrayList<>();
    words.add(w0);
    if (!vocab.containsKey(w0)) {
      System.out.println("Từ không có trong từ điển.");
      return words;
    }

    int w0Idx = vocab.get(w0);
    for (int t = 1; t <= 5; t++) {
      double maxProb = 0.0;
      String nextWord = "";
      int nextIdx = -1;

      for (Map.Entry<String, Integer> entry : vocab.entrySet()) {
        int idx = entry.getValue();
        if (conditionalProbs[w0Idx][idx] > maxProb) {
          maxProb = conditionalProbs[w0Idx][idx];
          nextWord = entry.getKey();
          nextIdx = idx;
        }
      }

      // Debugging: In bước chọn từ tiếp theo
      // System.out.println("Bước " + t + " - Từ hiện tại: " + words.get(words.size() - 1));
      // if (nextIdx == -1) {
      //   System.out.println("Không tìm thấy từ tiếp theo.");
      //   break;
      // }
      // System.out.println("Từ tiếp theo: " + nextWord + " (Xác suất: " + maxProb + ")");

      words.add(nextWord);
      w0Idx = nextIdx;
    }
    return words;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhập đường dẫn file dữ liệu: ");
    String filename = scanner.nextLine();
    readFile(filename);
    training();
    System.out.print("Nhập từ bắt đầu: ");
    String startWord = scanner.next();
    List<String> generatedText = inferring(startWord);
    System.out.println("Câu sinh ra: " + String.join(" ", generatedText));
  }
}
