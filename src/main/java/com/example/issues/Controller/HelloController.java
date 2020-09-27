package com.example.issues.Controller;

import com.example.issues.Entities.*;
import com.example.issues.Repositories.IssueRepository;
import com.example.issues.Repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    private UserRepository userRepository;

    private IssueRepository issueRepository;

    public HelloController(UserRepository userRepository,IssueRepository issueRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    @GetMapping("/")
    public String home(Model m,
                       @RequestParam(name = "requireLogin",required = false)boolean isRequire,
                       @RequestParam(name = "errorMessage",required = false)String errorMessage,
                       HttpServletRequest request){
        m.addAttribute("newIssue",new Issue());
        m.addAttribute("difficults", Difficult.values());
        if(isRequire) {
            m.addAttribute("requireLoginMessage","You need to login to do this function");
        }
        if(errorMessage != null) {
            m.addAttribute("errorMessage",errorMessage);
        }

        User u = (User) request.getSession().getAttribute("userLogined");

        if( u != null) {
            ArrayList<Status> statusList = new ArrayList<>();

            if(u.getRole().toString().equals("EXECUTOR")) {
                for(Status s : Status.values()) {
                    statusList.add(s);
                }
            } else {
                for(Status s : Status.values()) {
                    if(s.toString().equals("Closed")) {continue;}
                    statusList.add(s);
                }
            }

            m.addAttribute("statusList",statusList);

            ArrayList<Issue> issues = new ArrayList<>();
            issueRepository.findAllBy(u.getUserid()).iterator().forEachRemaining(issues::add);


            m.addAttribute("formIssues",new FormIssuesList(issues));
        } else {
            ArrayList<Issue> issues = new ArrayList<>();
            issueRepository.findAll().iterator().forEachRemaining(issues::add);

            m.addAttribute("formIssues",new FormIssuesList(issues));
        }


        return "home";
    }

    @PostMapping("/add")
    public String addIssue(@ModelAttribute("newIssue")Issue newIssue, HttpServletRequest request, RedirectAttributes ra) {
        User u = (User)request.getSession().getAttribute("userLogined");

        if(u == null) {
            ra.addAttribute("requireLogin",true);

            return "redirect:/";
        }

        newIssue.setUser(u);
        newIssue.setCreated(Date.valueOf(LocalDate.now()));
        newIssue.setStatus(Status.OnGoing);

        Issue i = issueRepository.save(newIssue);
        if(i == null) {
            ra.addAttribute("errorMessage","Something wrong with server, can't add new Issue");
        }

        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("formIssues")FormIssuesList f){
        for(Issue i : f.getIssues()) {
            System.out.println(i.getIssueid() + " : " + i.getStatus());
        }

        for(Issue i : f.getIssues()) {
            if(i.getStatus().toString() != "Closed") {
                issueRepository.updateStatusIssue(i.getIssueid(),i.getStatus());
            } else {
                Date date = Date.valueOf(LocalDate.now());
                issueRepository.setDone(i.getIssueid(),i.getStatus(),date);
            }

        }

        return "redirect:/";
    }

    @GetMapping("/closed")
    public String done(Model m,@RequestParam(name = "u",required = false)String username) {
        List<String> listUsername = new ArrayList<>();
        listUsername.add("all");
        userRepository.listUsername().iterator().forEachRemaining(listUsername::add);

        m.addAttribute("listUsername",listUsername);

        List<Issue> list = new ArrayList<>();
        if (username == null || username.equals("all")) {
            issueRepository.findAllClosedBy().iterator().forEachRemaining(list::add);
        } else {
            issueRepository.findAllClosedByUser(username).iterator().forEachRemaining(list::add);
        }
        m.addAttribute("list",list);

        return "closed";
    }

    @PostMapping("/changeUser")
    public String changeUser(Model m,@RequestParam(name = "u",required = false)String username,RedirectAttributes ra) {
        ra.addAttribute("u",username);

        return "redirect:/closed";
    }


    @GetMapping("/doc")
    public String documentation(Model m) {
        return "doc";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("userLogined");
        if(u != null && u.getRole().equals(Role.ADMIN)) {
            return "delete";
        }

        return "redirect:/";
    }

    @PostMapping("/bumbum")
    public String bumbum() {
        issueRepository.deleteAll();

        return "redirect:/";
    }

     @GetMapping("/login")
    public String login(Model m, HttpServletRequest request){
        if(request.getSession().getAttribute("userLogined") != null) {
            return "redirect:/";
        }

        m.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Model m,@ModelAttribute("user") User u, HttpServletRequest req){

        User user = userRepository.findUser(u.getUsername(),u.getPassword());

        if (user != null) {
            req.getSession().setAttribute("userLogined",user);

            return "redirect:/";
        }

        m.addAttribute("errorMessage","Username or password invaild");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("userLogined")!= null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
