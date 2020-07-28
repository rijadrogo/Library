/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import business.facade.BookFacadeLocal;
import business.model.Book;
import business.model.Publisher;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author riki
 */
@Named(value = "addBookManagedBean")
@ViewScoped
public class AddBookManagedBean implements Serializable {

    @Inject
    private PublisherManagedBean publisherManagedBean;
    
    @Inject
    private BookManagedBean bookManagedBean;   
    
    private String bookName;
    private String bookAuthor;
    private String pdfLink;
    private Date publisherDate = new Date();
    private String publisher;

   
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public Date getPublisherDate() {
        return publisherDate;
    }

    public void setPublisherDate(Date publisherDate) {
        this.publisherDate = publisherDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getPublisherList() {
        return publisherManagedBean.getPublisherFacadeLocal().findAll().stream().map(Publisher::getName).collect(Collectors.toList());
    }

    public AddBookManagedBean() {
    }
    
    
    public void saveBook(){
        Book book = new Book();
        book.setBookAuthor(bookAuthor);
        book.setBookName(bookName);
        book.setPublishDate(publisherDate);
        book.setPdfLink(pdfLink);
        Publisher publisherr = publisherManagedBean.getPublisherFacadeLocal().findByName(this.publisher);
        book.setPubliserID(publisherr);
        bookManagedBean.getBookFacadeLocal().create(book);
    }
    

}
