package ir.mctab.java32.projects.scholarshipmanagement.core.utilities;

import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;

import java.util.List;

public class Display {

    public static void printScholarshipList(List<Scholarship> scholarshipList, String role){

        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "====================");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+role+"'s List");
        System.out.println("=======================================================" +
                "=======================================================" +
                "=======================================================" +
                "====================");
        System.out.printf("%-3s %-22s %-10s %-10s  %-12s  %-15s %-10s %-20s  %-9s  %-15s  %-11s  %-20s %-10s\n"
                , "Id", "Status", "Name", "Family", "NationalCode", "LastUniversity", "LastDegree", "LastField",
                "LastScore", "ApplyUniversity", "ApplyDegree", "ApplyField", "ApplyDate");
        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "--------------------");

        for (int i = 0; i < scholarshipList.size(); i++) {
            System.out.println(scholarshipList.get(i));
        }

        System.out.println("-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "-------------------------------------------------------" +
                "--------------------");
    }

}