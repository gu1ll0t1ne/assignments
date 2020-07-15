//Change the path of the input file before compiling accordingly

package Assignment_7;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.regex.Pattern;

class MainClass
{
    public static void main(String[] args) throws IOException {
        Data d =new Data();
        Operators o = new Operators(d.code);
        d.TakeInput();
        o.CountOp();
        o.PrintOpMap();
        o.PrintOprMap();
        o.PrintResult();
    }
}

class Data
{
    ArrayList<String> code = new ArrayList<>();
    void TakeInput() throws IOException,NullPointerException {
        File file = new File("C:\\Users\\Anshul\\Desktop\\Assignment 7\\src\\input.c");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String st;
        while((st=br.readLine()) !=null)
        {
            code.add(st);
        }
    }
    void PrintData()
    {
        for(int i=0;i<code.size();i++)
        {
            System.out.println(code.get(i));
        }
    }
}

class Operators extends Data
{
    String[] t = {"<<",">>","<","=",">","-",",",";","(",")","[","]","{","}","+","++","--","&","^","*","%","!=","/","?","for","if","int","float","bool","double","else","return","while","continue","switch"};
    HashMap<String,Integer> opMap;
    HashMap<Character,Integer> oprMap;
    int totop=0;
    int disttotop=0;
    int totopr=0;
    int disttotopr=0;
    public Operators(ArrayList<String> cf)
    {
        code = cf;
        opMap = new HashMap<>();
        oprMap = new HashMap<>();
        for(int i=0;i<t.length;i++)
        {
            opMap.put(t[i],0);
        }
    }
    void CountOp()
    {
        for(Map.Entry m : opMap.entrySet())
        {
            int c1=0;
            String temp = (String)m.getKey();
            for(int i = 0 ;i<code.size();i++)
            {
                /*count=0;
                String str = code.get(i);
                int from=0;
                while((from=str.indexOf(temp,from))!=-1)
                {
                    count=count+1;
                    from=from+1;
                }*/
                String str = "~"+code.get(i)+"~";
                String str2 = "~"+code.get(i)+"~";
                String[] t = str2.split(Pattern.quote((temp)));
                for(int j=0;j<t.length;j++)
                {
                    if (t[j].length()>0) {
                        char op = t[j].charAt(t[j].length() - 1);
                        if (op != ' '||op!='~') {
                            if (oprMap.containsKey(op)) {
                                oprMap.replace(op, oprMap.get(op) + 1);
                            } else {
                                oprMap.put(op, 1);
                            }
                        }
                    }
                }
                c1+=(str.split(Pattern.quote(temp)).length)-1;

            }
            opMap.replace((String)m.getKey(),c1);
        }
        opMap.replace("+",opMap.get("+")-2*opMap.get("++"));
        opMap.replace("-",opMap.get("-")-2*opMap.get("--"));
    }
    void PrintOpMap()
    {
        System.out.format("   Operator    Frequency\n");
        for(Map.Entry m : opMap.entrySet())
        {
            int temp = (int) m.getValue();
            totop+=temp;
            disttotop+=1;
            if(temp!=0) {
                System.out.format("%10s%10d\n", m.getKey(), temp);
            }
        }
        System.out.println("\n");
    }
    void PrintOprMap()
    {
        System.out.format("   Operand  Frequency\n");
        for(Map.Entry m : oprMap.entrySet())
        {
            if ((char)m.getKey() != '~') {
                totopr+=(int)m.getValue();
                disttotopr+=1;
                System.out.format("%6c%9d\n", m.getKey(), m.getValue());
            }
        }
    }
    void PrintResult()
    {
        System.out.printf("Total operators: %d\nTotal distinct operators: %d\nTotal operands: %d\nTotal distinct operands: %d\n",totop,disttotop,totopr,disttotopr);
    }
}