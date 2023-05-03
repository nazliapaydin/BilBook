import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
/**
 * A class that has some generic methods that may be useful for our project.
 * Author: Ata Uzay Kuzey
 */
public class GenericMethods
{
    private final static char[] CHARACTERS=new char[]
        {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        '0','1','2','3','4','5','6','7','8','9','!','?'};
    
    private final static String[] INAPPROPRIATE_STRINGS=new String[]
        {"fuck","dick","boob","tit","ass","cock","sex","porn","butt","wank","crap","shit","blowjob","boner","cunt","nigg","cum","hentai","cuck"};
    
    public final static Random rand=new Random();

    /**
     * A method to sort an arraylist using the sort method from the Arrays class. It does not return the sorted arraylist,
     * instead the arraylist is replaced with the sorted one.
     * @param <T> class of the objects the arraylist contains.
     * @param arrayList arraylist to be sorted.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void sort(ArrayList<T> arrayList)
    {
        if(arrayList==null)
        {
            throw new NullPointerException();
        }
        Object[] array=arrayList.toArray();
        arrayList.removeAll(arrayList);
        Arrays.sort(array);
        for(int i=0;i<array.length;i++)
        {
            arrayList.add((T)array[i]);
        }
    }

     /**
     * A method to reverse sort an arraylist using the sort method from the Arrays class. It does not return the sorted arraylist,
     * instead the arraylist is replaced with the sorted one.
     * @param <T> class of the objects the arraylist contains.
     * @param arrayList arraylist to be reverse sorted.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void reverseSort(ArrayList<T> arrayList)
    {
        if(arrayList==null)
        {
            throw new NullPointerException();
        }
        Object[] array=arrayList.toArray();
        arrayList.removeAll(arrayList);
        Arrays.sort(array);
        for(int i=array.length-1;i>=0;i--)
        {
            arrayList.add((T)array[i]);
        }
    }

     /**
     * A method to invert the members of an arraylist. It does not return the inverted arraylist,
     * instead the arraylist is replaced with the inverted one.
     * @param <T> class of the objects the arraylist contains.
     * @param arrayList arraylist to be inverted.
     */
    public static <T> void invert(ArrayList<T> arrayList)
    {
        if(arrayList==null)
        {
            throw new NullPointerException();
        }
        ArrayList<T> ogList=copyOf(arrayList);
        arrayList.removeAll(arrayList);
        for(int i=ogList.size()-1;i>=0;i--)
        {
            arrayList.add(ogList.get(i));
        }
    }

    /**
     * A method to copy the values of an arraylist onto a new arraylist.
     * @param <T> class of the objects the arraylist contains.
     * @param arrayList arraylist to be copied.
     * @return the copy of the arraylist.
     */
    public static <T> ArrayList<T> copyOf(ArrayList<T> arrayList)
    {
        if(arrayList==null)
        {
            throw new NullPointerException();
        }
        ArrayList<T> copy=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++)
        {
            copy.add(arrayList.get(i));
        }
        return copy;
    }

    /**
     * A method that calls the Date constructor.
     * @param day
     * @param month
     * @param year
     * @return a date object created with the given parameters.
     * @see Date#Date(int, int, int)
     */
    public static Date createDate(int day, int month, int year)
    {
        return new Date(day,month,year);
    }

    /**
     * Calls the isValid method in the date class.
     * @param date date to be checked
     * @return true if the date is valid, false otherwise.
     * @see Date#isValid()
     */
    public static boolean isValidDate(Date date)
    {
        return date.isValid();
    }

    /**
     * A method to learn the inappropriate word in a string object.
     * @param str string object to be checked.
     * @return null if the string object is clear, the inappropriate word in the string otherwise.
     */
    public static String inappropriateString(String str)
    {
        String check=str.toLowerCase();
        for(int i=0;i<INAPPROPRIATE_STRINGS.length;i++)
        {
            if(check.contains(INAPPROPRIATE_STRINGS[i]))
            {
                if(i!=4||!check.contains("bass"))
                {
                    return INAPPROPRIATE_STRINGS[i];
                }
            }
        }
        return null;
    }

    /**
     * A method to check if a string object contains an inappropriate word.
     * @param str the string object to be checked
     * @return true if the string contains something inappropriate, false otherwise.
     */
    public static boolean isInappropriate(String str)
    {
        return inappropriateString(str)!=null;
    }

    /**
     * Creates a code in base 64 of length.
     * @param length length of the code to be created.
     * @return the code randomly created.
     */
    public static String createCode(int length)
    {
        String str;
        do
        {
            str="";
            for(int i=0;i<length;i++)
            {
                str+=CHARACTERS[rand.nextInt(CHARACTERS.length)];
            }
        }
        while(isInappropriate(str));

        return str;
    }

    /**
     * A class that respresents a date object. A date object has instance variables for the day, month and year. 
     * The class implements the Comparable interface and therefore is sortable. The class also contains some methods
     * that can be helpful when dealing with dates.
     */
    public static class Date implements Comparable<Date>
    {
        private int day;
        private int month;
        private int year;

        /**
         * Date constructor. Throws an exception if the date isn't valid.
         * @param day
         * @param month
         * @param year
         */
        public Date(int day, int month, int year)
        {
            this.day=day;
            this.month=month;
            this.year=year;
            if(year<0)
            {
                this.month=1;
                this.day=1;
            }
            if(!isValid())
            {
                throw new ExceptionInInitializerError("Impossible Date");
            }
        }

        /**
         * Checks if the date is valid. Years should be between -3400 and 2024 and can't be 0, months should be between 1 and 12, days should be 
         * bigger than 0 and smaller than the number of the days in the respective month. It also checks for leap years.
         * @return true if the date is valid, false otherwise.
         */
        public boolean isValid()
        {
            if(month<1||month>12)
            {
                return false;
            }
            if(day<1||day>monthDay(month, year))
            {
                return false;
            }
            if(year==0||year>2023||year<-3400)
            {
                return false;
            }
            if(year==1582&&month==10&&day>4&&day<15)
            {
                return false;
            }

            return true;
        }

        /**
         * A method to get how many days are there in a month accounts for leap years
         * @param month number of the month, should be between 1 and 12
         * @param year the year the month is in
         * @return the number of days in the month
         */
        public static int monthDay(int month,int year)
        {
            if(month>12||month<1||year<=0)
            {
                return 30;
            }

            int[] monthDays=new int[]{0,31,28,31,30,31,30,31,31,30,31,30,31};
            if(month!=2)
            {
                return monthDays[month];
            }

            int february;
            if     (year%400==0)    {february=29;}
            else if(year%100==0)    {february=28;}
            else if(year% 4 ==0)    {february=29;}
            else                    {february=28;}

            return february;
        }
        
        @Override
        public String toString() {
            if(year<0)
            {
                return -year+" B.C.";
            }
            String str="";
            if(day<10){str+="0";}
            str+=day+"/";
            if(month<10){str+="0";}
            str+=month+"/";
            if(year<10){str+="0";}
            if(year<100){str+="0";}
            if(year<1000){str+="0";}
            str+=year+"";
            return str;
        }

        @Override
        public int compareTo(Date o) {
            if(o.year!=year)
            {
                return year-o.year;
            }
            if(o.month!=month)
            {
                return month-o.month;
            }
            if(o.day!=day)
            {
                return day-o.day;
            }
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Date)||obj==null)
            {
                return false;
            }
            Date date=(Date) obj;
            return date.day==day&&date.month==month&&date.year==year;
        }
    }
}