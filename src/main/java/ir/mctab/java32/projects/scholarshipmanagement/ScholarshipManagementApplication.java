package ir.mctab.java32.projects.scholarshipmanagement;

import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.utilities.Display;
import ir.mctab.java32.projects.scholarshipmanagement.features.dashboard.DashboardUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.dashboard.DashboardUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LoginUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LogoutUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LoginUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.util.List;
import java.util.Scanner;

public class ScholarshipManagementApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("exit")) {
            System.out.println("what do you want? ");
            command = scanner.nextLine();
            // Login
            if (command.equals("login")) {
                System.out.println("Username : ");
                String username = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();
                LoginUseCase loginUseCase = new LoginUseCaseImpl();
                User user = loginUseCase.login(username, password);
                if (user != null) {
                    System.out.println(" Login successful by " + user.getRole());
                } else {
                    System.out.println(" Login Failed! Invalid Username or Password!");
                }
            } else if (command.equals("logout")) {
                if (AuthenticationService.getInstance().getLoginUser() == null) {
                    System.out.println("You Aren't Logged In!");
                } else {
                    LogoutUseCaseImpl logoutUseCase = new LogoutUseCaseImpl();
                    logoutUseCase.logout();
                    System.out.println(" Logout Successful!");
                }
            }
            //**********************************request by student

            else if (command.equals("request")) {
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Student")) {
                    System.out.println("Please Login as Student!");
                } else {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Family:");
                    String family = scanner.nextLine();
                    String nationalCode = "";
                    do {
                        System.out.print("National Code: ");
                        nationalCode = scanner.nextLine();
                        if (nationalCode.length() != 10)
                            System.out.println("Invalid National Code Length!");
                    } while (nationalCode.length() != 10);

                    System.out.print("Last University: ");
                    String lastUni = scanner.nextLine();
                    System.out.print("Last Degree: ");
                    String lastDegree = scanner.nextLine();
                    System.out.print("Last Field: ");
                    String lastField = scanner.nextLine();

                    String strScore;
                    System.out.print("Last Score: ");
                    strScore = scanner.nextLine();
                    Float lastScore = Float.parseFloat(strScore);

                    System.out.print("Apply University: ");
                    String applyUni = scanner.nextLine();
                    System.out.print("Apply Degree: ");
                    String applyDegree = scanner.nextLine();
                    System.out.print("Apply Field: ");
                    String applyField = scanner.nextLine();
                    System.out.print("Apply Date: ");
                    String applyDate = scanner.nextLine();

                    Scholarship scholarship = new Scholarship(null, "RequestedByStudent", name, family, nationalCode
                            , lastUni, lastDegree, lastField, lastScore, applyUni, applyDegree, applyField, applyDate);

                    new RequestScholarshipByStudentUseCaseImpl().request(scholarship);
                    System.out.println("Request Sent Successfully!");
                }

            }
            //******************************find or accept or reject by supervisor
            else if (command.equals("svlist") || command.equals("svaccept") || command.equals("svreject")) {
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Supervisor")) {
                    System.out.println("Please Login as Supervisor!");
                }


                if (command.equals("svlist")) {
                    FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
                            = new FindScholarshipBySupervisorUseCaseImpl();

                    List<Scholarship> scholarships = findScholarshipBySupervisorUseCase
                            .listScholarships();
                    for (int i = 0; i < scholarships.size(); i++) {
                        System.out.println(scholarships.get(i));
                    }
                }

                if (command.equals("svaccept")) {
                    System.out.print("Scholarship Id: ");
                    String scholarshipId = scanner.nextLine();

                    AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
                            = new AcceptScholarshipBySupervisorUseCaseImpl();
                    acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId));
                    System.out.println("Accepted by Supervisor Successfully!");
                } else if (command.equals("svreject")){
                    System.out.print("Scholarship Id: ");
                    String scholarshipId = scanner.nextLine();
                    RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
                            = new RejectScholarshipBySupervisorUseCaseImpl();
                    rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
                    System.out.println("Rejected by Supervisor Successfully!");
                }

            }
            //*************manager
            else if (command.equals("mnglist") || command.equals("mngaccept") || command.equals("mngreject")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("Manager")){
                    System.out.println("\t\u26a0 Please Login as Manager!");
                }
                else {
                    FindScholarshipByManagerUseCase findScholarshipByManagerUseCase
                            = new FindScholarshipByManagerUseCaseImpl();

                    List<Scholarship> scholarships = findScholarshipByManagerUseCase
                            .listScholarships();

                    if (command.equals("mnglist")) {
                        if (scholarships.size() == 0)
                            System.out.println(" Manager's List is Empty!");
                        else
                            Display.printScholarshipList(scholarships, "Manager");
                    }
                    else {
                        System.out.print(" Scholarship Id: ");
                        String scholarshipId = scanner.nextLine();
                            Scholarship selectedScholarship = null;
                            for (Scholarship i : scholarships) {
                                if (i.getId() == Long.parseLong(scholarshipId)){
                                    selectedScholarship = i;
                                }
                            }
                            if (selectedScholarship == null){
                                System.out.println("\t\t\u26a0 Entered Scholarship Doesn't Exist in Manager List!");
                            }
                            else {
                                if (command.equals("mngaccept")){
                                    AcceptScholarshipByManagerUseCase acceptScholarshipByManagerUseCase
                                            = new AcceptScholarshipByManagerUseCaseImpl();
                                    acceptScholarshipByManagerUseCase.accept(Long.parseLong(scholarshipId));
                                    System.out.println("Accepted by Manager Successfully!");
                                }
                                else if(command.equals("mngreject")) {
                                    RejectScholarshipByManagerUseCase rejectScholarshipByManagerUseCase
                                            = new RejectScholarshipByManagerUseCaseImpl();
                                    rejectScholarshipByManagerUseCase.reject(Long.parseLong(scholarshipId));
                                    System.out.println("Rejected by Manager Successfully!");
                                }

                            }
                        }
                    }
                }
            //*****************university
            else if (command.equals("unilist")){
                User loginUser = AuthenticationService.getInstance().getLoginUser();
                if (loginUser == null || !loginUser.getRole().equals("University")) {
                    System.out.println(" Please Login as University!");
                }
                else {
                    FindScholarshipByUniversityUseCase findScholarshipByUniversityUseCase
                            = new FindScholarshipByUniversityUseCaseImpl();
                    List<Scholarship> scholarships = findScholarshipByUniversityUseCase.listScholarships();
                    if (scholarships.size() == 0)
                        System.out.println("University List is Empty!");
                    else
                        Display.printScholarshipList(scholarships, "University");
                }
            }
//************************dashboard
            else if (command.equals("dashboard")){
                DashboardUseCase dashboardUseCase = new DashboardUseCaseImpl();
                dashboardUseCase.display();
            }
        }
    }
}




