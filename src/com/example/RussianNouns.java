package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.*;



    public class RussianNouns {

        public static void main(String[] args) throws Exception {
         int positionNum = 30855;
            while (nonEmptyFile("C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt")) {

                String word = readerFirstLineFile("C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt"); //берет слово в списке слов
                String definition = readerFileEfremova(word, "C:\\Users\\ANESTEISHA\\толковый словарь Ефремовой.txt"); //записывает толкование со словаря Ефремовой

                positionNum = writerNewFileVocabulary(word, definition, positionNum,"C:\\Users\\ANESTEISHA\\словарь русских существительных с толкованием.txt");

                deleteFirstLineFromList("C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt");

                System.out.println("");

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

        public static int writerNewFileVocabulary (String word, String definition, int positionNum, String outputFileName) throws Exception {
            int newNum = positionNum+1;
            FileWriter writer = new FileWriter(outputFileName, true); // true - дозапись, false - перезапись


            writer.write(word); //записывает данное слово
            writerNewListWords(newNum+". "+word, "C:\\Users\\ANESTEISHA\\список_слов_нумерованный.txt");
            writerNewListWords(newNum+". ", "C:\\Users\\ANESTEISHA\\список_цифр.txt");
            writerNewListWords(newNum+"."+definition, "C:\\ANESTEISHA\\нумерованный_список_толкований.txt");
            writerNewListWords(definition.substring(1), "C:\\Users\\ANESTEISHA\\список_толкований.txt");

            writer.append(" -");
            writer.append(definition);//записывает определение

            writer.append('\n');
            writer.flush();
            writer.close();
            return newNum;
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

                     //выводится дефиниция(толкование)
                    {
                        definition = a;
                        if (a.endsWith(".")) break;

                        String b = scan.nextLine();
                        definition = a + b;
                        if (b.endsWith(".")) break;


                        String c = scan.nextLine();
                        definition = definition + c;
                        if (c.endsWith(".")) break;


                        String d = scan.nextLine();
                        definition = definition + d;
                        if (d.endsWith(".")) break;


                        String e = scan.nextLine();
                        definition = definition + e;
                        if (e.endsWith(".")) break;

                    }
                break;


                }

                else {
                    definition = " Определение не найдено.";

                }

            }



            for (int i = 1; i < 9; i++) {

             if (definition.indexOf(" (" + i +")")>0 ) {
               char[] mas = definition.toCharArray();
                 for (int j = 0; j <definition.length(); j++) {

                     if(mas[j]== ' '&& mas[j+1]=='('&& mas[j+3]==')')   {
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);

                         break;
                     }

                 }
                 definition = definition.copyValueOf(mas);
               }

            }

            for (int i = 1; i < 9; i++) {

             if (definition.indexOf(" (" + i +"*)")>0) {
               char[] mas = definition.toCharArray();
                 for (int j = 0; j <definition.length(); j++) {

                     if(mas[j]== ' '&& mas[j+1]=='('&& mas[j+4]==')')   {
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);

                         break;
                     }

                 }


              definition = definition.copyValueOf(mas);
               }

            }

            for (int i = 1; i < 9; i++) {

             if (definition.indexOf("(" + i +"*)")>0) {
               char[] mas = definition.toCharArray();
                 for (int j = 0; j <definition.length(); j++) {

                     if(mas[j]== '('&& mas[j+3]==')')   {

                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);
                         mas = remove(mas, j);

                         break;
                     }

                 }


               definition = definition.copyValueOf(mas);
               }

            }

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


        //метод удаляет символ из массива символов строки
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


        public static void deleteFirstLineFromList(String  sourceFileName) throws Exception{

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
        }


        public static void fileDeleteRename(String sourceFileName, String outputFileName){
            File  reader = new File(sourceFileName);
            File writer = new File(outputFileName);
            reader.delete();
            writer.renameTo(reader);
        }

        public static boolean nonEmptyFile(String sourceFileName) throws Exception{
            boolean a = false;
            FileReader reader = new FileReader(sourceFileName);
            Scanner scan = new Scanner(reader);
            if (scan.hasNextLine()) a = true;
            reader.close();
            return a;
        }

        public static void writerNewListWords(String word, String outputFileName) throws Exception{
            FileWriter writer = new FileWriter(outputFileName, true); // true - дозапись, false - перезапись

            writer.write(word); //записывает данное слово
            writer.append('\n');
            writer.flush();
            writer.close();
        }
    }
