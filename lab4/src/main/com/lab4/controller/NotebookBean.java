package com.lab4.controller;

import com.lab4.dao.DaoFactory;
import com.lab4.dao.NotebookDao;
import com.lab4.dao.UserDao;
import com.lab4.entity.Notebook;
import com.lab4.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "notebookBean")
@RequestScoped
@Getter
@Setter
public class NotebookBean implements Serializable {
    private NotebookDao notebookDao;
    private List<Notebook> notebookList;

    private long id;
    private String brand;
    private String processor;
    private String videocard;
    private String series;
    private String userLogin;
    private boolean saveFlag = false;


public String saveNotebook() {

    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    User currentUser = (User)
            ((HttpSession) externalContext.getSession(false))
                    .getAttribute("User");
    long userId = currentUser.getId();
    Notebook insertNotebook = new Notebook(id,brand, processor, videocard,series,currentUser);

    try(NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {

        System.out.println(insertNotebook);
        if (notebookDao.create(insertNotebook))
            return "/user/notebookList";
        else return "/error/400?faces-redirect=true&includeViewParams=true";
    }
}

    public String saveNotebookByAdmin() {
        User forUser;
        try (UserDao userDao = DaoFactory.getInstance().getUserDao()){
            forUser = userDao.getByLogin(userLogin).get();
        }
        Notebook insertNotebook = new Notebook(id,brand, processor, videocard,series,forUser);

        try(NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {
            System.out.println(insertNotebook);
            if (notebookDao.create(insertNotebook))
                return "/admin/notebookList";
            else return "/error/400?faces-redirect=true&includeViewParams=true";
        }
    }


    public List<Notebook> getAllNotebooks() {
        try (NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {
            return notebookDao.getAll();
        }
    }
    public String toUpdatePage(int id){
        this.id = id;
        try(NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {
           Notebook newNotebook = notebookDao.getById(id).get();
            this.brand = newNotebook.getBrand();
            this.processor = newNotebook.getProcessor();
            this.videocard = newNotebook.getVideocard();
            this.series = newNotebook.getSeries();
        }
        System.out.println("FORWARD TO UPDATE PAGE");
        return "/user/updateNotebook?faces-redirect=true&includeViewParams=true";
    }


    public String updateNotebook(int id){
        try(NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {
            if (validation() || !notebookDao.getById(id).isPresent()) {
                return "/error/400?faces-redirect=true&includeViewParams=true";
            }
            Notebook updateNotebook = notebookDao.getById(id).get();
            updateNotebook.setBrand(brand);
            updateNotebook.setProcessor(processor);
            updateNotebook.setVideocard(videocard);
            updateNotebook.setSeries(series);
            notebookDao.update(updateNotebook);
        }
        System.out.println("UPDATE Notebook " + id + " " + brand + " " + processor + " " + videocard + " " + series);
        return "/user/notebookList?faces-redirect=true";
    }


    public void deleteNotebook(int id){
        try(NotebookDao notebookDao = DaoFactory.getInstance().getNotebookDao()) {
            notebookDao.remove(id);
        }
    }

    private boolean validation(){
        boolean result = (id == 0 || brand.isEmpty()|| processor.isEmpty()|| videocard.isEmpty()|| series.isEmpty());
        System.out.println("VALIDATION FAILS: " + result);
        return result;
    }
}
