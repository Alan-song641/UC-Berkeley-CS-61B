package com.song.homework01;

import java.util.Scanner;

public class FindMissingPage {
   private boolean[] pages;
   int size ;

   public FindMissingPage(){
       size=700;
       this.pages=new boolean[size];
       for(int i=0;i<pages.length;i++){
           pages[i]=false;
       }
   }

   public void insertion(String[] string){
       for(int i=0;i<string.length;i++) {

           if (string[i].length() <= 3) { //输入的页数最大999
               int p = Integer.parseInt(string[i]);
               if(p>=0&&p<size){
                   this.pages[p] = true;
                   System.out.println("Insetion sucessfully");
               }

           } else {
               int p1 = 0;
               int p2 = 0;
               for (int j = 0; j < string[i].length() - 1; j++) {
                   if (string[i].substring(j, j + 1).equalsIgnoreCase("–")) {
                       p1 = Integer.parseInt(string[i].substring(0, j));
                       p2 = Integer.parseInt(string[i].substring(j + 1));
                       //System.out.println(p1+" "+p2);
                       break;
                   }
               }

               for (int j = p1; j <= p2; j++) {
                   this.pages[j] = true;
               }

               System.out.println("Wide Insetion sucessfully");
           }
       }
   }



    public void OutputTrue(){
       System.out.print("You already read:");
       for(int i=0;i<this.pages.length;i++){
            if(pages[i]==true){
                System.out.print(i+" ");
            }
        }

    }
    public void OutputFalse(){
       System.out.print("You haven't read:");
        for(int i=0;i<pages.length;i++){
            if(pages[i]==false){
                System.out.print(i+" ");
            }
        }
    }

    public static void main(String[] args) {
            FindMissingPage pages = new FindMissingPage();
            Scanner sc = new Scanner(System.in);

            while(true) {
                System.out.println("请输入已读页数：");
                //System.out.println("注：合法格式为“”【123】或者【123–456】,end,read,notread");
                String string = sc.nextLine();

                if (string.equalsIgnoreCase("read")) {
                    pages.OutputTrue();
                }else if(string.equalsIgnoreCase("notread")){
                    pages.OutputFalse();
                }else if(string.equalsIgnoreCase("end")){
                    System.out.println("欢迎下次使用哦宝贝");
                    return;
                }
                else{
                    String[] splitedS=string.split(", ");
                    pages.insertion(splitedS);
                }


            }



    }

}
