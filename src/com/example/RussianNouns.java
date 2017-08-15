package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.*;



public class RussianNouns {

    public static void main(String[] args) throws Exception {
        int positionNum = 1;
        while (nonEmptyFile("C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt")) {

            String word = readerFirstLineFile("C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt"); //берет слово в списке слов
            String definition = readerFileEfremova(word, "C:\\Users\\ANESTEISHA\\толковый словарь Ефремовой.txt"); //записывает толкование со словаря Ефремовой

            writerNewListWords(positionNum+". "+word, "C:\\Users\\ANESTEISHA\\список_слов_нумерованный.txt");
            writerNewListWords(positionNum+". ", "C:\\Users\\ANESTEISHA\\список_цифр.txt");
            writerNewListWords(positionNum+"."+definition, "C:\\ANESTEISHA\\нумерованный_список_толкований.txt");
            writerNewListWords(definition.substring(1), "C:\\Users\\ANESTEISHA\\список_толкований.txt");

            writerNewFileVocabulary(word, definition, "C:\\Users\\ANESTEISHA\\словарь русских существительных с толкованием.txt");

            positionNum = deleteFirstLineFromList(positionNum,"C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt");

            // System.out.println("");

        }

    }


    public static String readerFirstLineFile(String sourceFileName) throws Exception {
        FileReader reader = new FileReader(sourceFileName);
        Scanner scan = new Scanner(reader);
        String word=null;
        while (scan.hasNextLine()) {
            word = scan.nextLine(); break; // читает первую строку
        }
        reader.close();
        return word;
    }

    public static void writerNewFileVocabulary (String word, String definition, String outputFileName) throws Exception {

        FileWriter writer = new FileWriter(outputFileName, true); // true - дозапись, false - перезапись


        writer.write(word); //записывает данное слово
        writer.append(" -");
        writer.append(definition);//записывает определение

        writer.append('\n');
        writer.flush();
        writer.close();
    }


    public static String readerFileEfremova(String word, String fileEfremova) throws Exception{
        FileReader reader = new FileReader(fileEfremova);
        Scanner scan = new Scanner(reader);
        String definition = null;
        // word = word.trim(); // отсекает пробелы вначале и вконце слова

        while (scan.hasNextLine()){

            String aa = scan.nextLine(); // очередная строка записывается в строку aa

            // System.out.println(word.length() + " word "); // проверить совпадает ли длина заданного слова и слова со строки
            // System.out.println(aa.length() + " aa "); // для того же
            // System.out.println(aa);


            // если заданное слово совпадает со словом строки aa делать
            if (word.equals(aa)) {


                String zero = scan.nextLine();  // пропускает строку после слова, там не нужная инфа
                if (zero.startsWith(" I") || zero.startsWith("I") || zero.startsWith(" (а также")) zero = scan.nextLine(); // пропустить еще одну лишнюю строку после слова, если она есть

                String a = scan.nextLine();

                if (a.startsWith(" 1)")) a = a.substring(3);
                if (a.startsWith(" а)")) a = a.substring(3);


                if (a.startsWith(" см.") || a.startsWith(" То же")) {
                    writerNewListWords(word, "C:\\Users\\ANESTEISHA\\список отсылочных слов_(см).txt");
                    writerNewListWords(word + " -" + a, "C:\\Users\\ANESTEISHA\\список отсылочных слов_(см)_plus.txt");

                }




                //выводит дефиницию(толкование) из словаря Ефремовой

                {
                    definition = a;
                    if (a.endsWith(".")) break;

                    for (int i = 0; i <4 ; i++) {
                        String b = scan.nextLine();
                        definition = definition + b;
                        if (b.endsWith(".")) break;
                    }

                }
                break;


            }

            else {
                definition = " Определение не найдено.";

            }

        }

              //если в строке есть лишние знаки аля " (1)", убирает

        for (int i = 1; i < 9; i++) {

            if (definition.indexOf(" (" + i +")")>0 ) {
                char[] mas = definition.toCharArray();
                for (int j = 0; j <definition.length(); j++) {

                    if(mas[j]== ' '&& mas[j+1]=='('&& mas[j+3]==')')   {
                        for (int k = 1; k <=4; k++) {
                            mas = remove(mas, j);
                        }
                        break;
                    }

                }
                definition = definition.copyValueOf(mas);
            }

        }

        //если в строке есть лишние знаки аля " (1*)", убирает

        for (int i = 1; i < 9; i++) {

            if (definition.indexOf(" (" + i +"*)")>0) {
                char[] mas = definition.toCharArray();
                for (int j = 0; j <definition.length(); j++) {

                    if(mas[j]== ' '&& mas[j+1]=='('&& mas[j+4]==')')   {

                        for (int k = 1; k <=5; k++) {
                            mas = remove(mas, j);
                        }

                        break;
                    }

                }


                definition = definition.copyValueOf(mas);
            }

        }

        //если в строке есть лишние знаки аля "(1)*", убирает

        for (int i = 1; i < 9; i++) {

            if (definition.indexOf("(" + i +"*)")>0) {
                char[] mas = definition.toCharArray();
                for (int j = 0; j <definition.length(); j++) {

                    if(mas[j]== '('&& mas[j+3]==')')   {

                        for (int k = 1; k <=4; k++) {
                            mas = remove(mas, j);
                        }

                        break;
                    }

                }


                definition = definition.copyValueOf(mas);
            }

        }

        //если в строке есть лишние знаки аля " (1-2)", убирает

        Pattern p = Pattern.compile("\\s\\D\\d\\D\\d\\D(\\s|\\.|,)");
        Matcher m = p.matcher(definition);

        if (m.find()) {
            char[] mas = definition.toCharArray();
            int j =m.start();

            for (int i = 1; i <=6; i++) {
                mas = remove(mas, j);
            }


            definition = definition.copyValueOf(mas);
        }

        //если в строке есть лишние знаки аля " (1*2-3)", убирает

        p = Pattern.compile("\\s\\D\\d\\D\\d\\D\\d\\D(\\s|\\.|,)");
        m = p.matcher(definition);

        if (m.find()) {
            char[] mas = definition.toCharArray();
            int j =m.start();

            for (int i = 1; i <=8; i++) {
                mas = remove(mas, j);
            }

            definition = definition.copyValueOf(mas);

        }

        // System.out.println(word + " word");
        // System.out.println(definition);

        if (definition.equals(" Определение не найдено.")) writerNewListWords(word, "C:\\Users\\ANESTEISHA\\слова без толкования.txt");
        reader.close();
        return definition;
    }


    //удаляет символ из массива символов строки
    public static char[] remove(char[] mas, int index){
        int n = mas.length;
        char[] mas1 = new char[n-1];

        for (int i =index; i < n-1; i++)
            mas[i] = mas [i+1];

        for (int j = 0; j < mas1.length; j++) {
            mas1[j]=mas[j];
        }

        return mas1;
    }

     //удаляет первую строку в заданном файле,  увеличивает счётчик на 1, возвращает счётчик
    public static int deleteFirstLineFromList(int positionNum, String  sourceFileName) throws Exception{

        FileReader reader = new FileReader(sourceFileName);
        String outputFileName = sourceFileName + "_copy.txt";
        FileWriter writer = new FileWriter(outputFileName, false); //false - перезапись файла-копии, true - дозапись

        Scanner scan = new Scanner(reader);

        String line = scan.nextLine();

        while (scan.hasNextLine()) {

            line = scan.nextLine();

            {
                writer.write(line);
                writer.write('\n');

            }
        }
        reader.close();
        writer.flush();
        writer.close();

        fileDeleteRename(sourceFileName, outputFileName);

        int newNum = positionNum + 1;
        return newNum;
    }

    //обновляет файл
    public static void fileDeleteRename(String sourceFileName, String outputFileName){
        File  reader = new File(sourceFileName);
        File writer = new File(outputFileName);
        reader.delete();
        writer.renameTo(reader);
    }

    //если файл не пустой, возвращает true
    public static boolean nonEmptyFile(String sourceFileName) throws Exception{
        boolean a = false;
        FileReader reader = new FileReader(sourceFileName);
        Scanner scan = new Scanner(reader);
        if (scan.hasNextLine()) a = true;
        reader.close();
        return a;
    }

    //создаёт новый список
    public static void writerNewListWords(String word, String outputFileName) throws Exception{
        FileWriter writer = new FileWriter(outputFileName, true); // true - дозапись, false - перезапись

        writer.write(word); //записывает данное слово
        writer.append('\n');
        writer.flush();
        writer.close();
    }
}