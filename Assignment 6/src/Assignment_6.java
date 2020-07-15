/*
* Before compiling change the path of input.txt to the location of the input file
* in your system.
* keep the same input format or else he result may be incorrect.
*/



/*
The sample input is given below:
Activity	Intermediate predecessor	Duration (in weeks)
A	-	9
B	A	4
C	A	3
D	B, C	7
E	D	6
F	E	1
G	D	8
H	F, G	5
*/

import java.io.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;


class MainClass
{
    public static void main(String[] args) throws IOException {
        inputTable in = new inputTable();
        in.takeInput();
        outputTable out = new outputTable(in.Activity,in.IntP,in.Duration);
        out.SetTime();
        out.SetCrit();
        out.PrintResult();

    }
}
class inputTable {
    ArrayList<String> Activity;
    ArrayList<String> IntP;
    ArrayList<Integer> Duration;

    inputTable() {
        Activity = new ArrayList<>();
        IntP = new ArrayList<>();
        Duration = new ArrayList<>();
    }

    public void takeInput() throws IOException {
        // Change path of the input file in the next line
        File sample = new File("C:\\Users\\Anshul\\Desktop\\Assignment 6\\src\\input.txt"); // Path of Table(in .txt file)
        BufferedReader br = new BufferedReader(new FileReader((sample)));

        String st;
        st = br.readLine();
        while ((st = br.readLine()) != null) {
            String temp[] = st.split("\t");
            Activity.add(temp[0]);
            IntP.add(temp[1]);
            int tem = Integer.parseInt(temp[2]);
            Duration.add(tem);
        }
    }
}


class outputTable extends inputTable {
    HashMap<String, Integer> StartTime;
    HashMap<String, Integer> EndTime;
    HashMap<String, Integer> Crit;

    public outputTable(ArrayList<String> activity, ArrayList<String> intP, ArrayList<Integer> duration) {
        Activity = activity;
        IntP = intP;
        Duration = duration;
        StartTime = new HashMap<>();
        EndTime = new HashMap<>();
        Crit = new HashMap<>();
        for(int i =0;i<Activity.size();i++)
        {
            Crit.put(Activity.get((i)),0);
        }
    }

    public void SetTime()
    {
        for(int i=0;i<Activity.size();i++)
        {
            String temp[] = IntP.get(i).split(", ");
            int max = 0;
            for(int j=0;j<temp.length;j++)
            {

                if(temp[j].equals("-"))
                {
                    StartTime.put(Activity.get(i),0);
                    EndTime.put(Activity.get(i),Duration.get(i));
                }
                else
                {
                    int t=EndTime.get(temp[j]);
                    if( t > max)
                    {
                        max = t;
                    }
                }
            }
            StartTime.put(Activity.get(i),max);
            EndTime.put(Activity.get(i),max+Duration.get(i));

        }
    }
    public void SetCrit()
    {
        String K = null;
        int max=0;
        for(Map.Entry m :EndTime.entrySet())
        {
            int t = (int) m.getValue();
            if(t>max)
            {
                max=t;
                K = (String) m.getKey();
            }
        }
        Crit.replace(K,1);
        int tem=StartTime.get(K);
        while(tem!=0)
        {
            for(Map.Entry m :EndTime.entrySet())
            {
                int t = (int) m.getValue();
                if(t == tem)
                {
                    K = (String) m.getKey();
                    break;
                }
            }
            Crit.replace(K,1);
            tem=StartTime.get(K);
        }
    }
    public void PrintResult()
    {
        ArrayList<String > path = new ArrayList<>();
        System.out.printf("Activity   Start_Time   Completion_Time   Critical_Path\n");
        for(int i=0;i<Activity.size();i++) {
            char temp=' ';
            if(Crit.get(Activity.get(i))==1)
            {
                temp='*';
                path.add(Activity.get(i));
            }
            System.out.printf("%s\t\t\t\t%d\t\t\t\t%d\t\t\t\t%c\n", Activity.get(i), StartTime.get(Activity.get(i)), EndTime.get(Activity.get(i)),temp);
        }
        System.out.printf("The Critical Path is ");
        for(int i=0;i<path.size()-1;i++)
        {
            System.out.printf("%s->",path.get(i));
        }
        System.out.println(path.get(path.size()-1));
    }
}