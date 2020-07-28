package bean;

import bean.pagination.PaginationHelper;
import business.facade.BookFacadeLocal;
import business.model.Book;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riki
 */
@Named
@SessionScoped
public class BookManagedBean implements Serializable {

    @Inject
    private BookFacadeLocal bookFacadeLocal;

    private Book bookItem;
    private String title = "";
    private String author = "";

    public BookFacadeLocal getBookFacadeLocal() {
        return bookFacadeLocal;
    }
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel dataModel = null;

    private List<Book> books;

    public List<Book> getBooksByAuthor() {
        books = bookFacadeLocal.findByAuthor(author);
        return books;
    }

    public List<Book> getBooksByTitle() {
        books = bookFacadeLocal.findByTitle(title);
        return books;
    }

    public void setBooksByAuthor(List<Book> booksByAuthor) {
        this.books = booksByAuthor;
    }

    public PaginationHelper getPagination() {

        if (pagination == null) {

            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return bookFacadeLocal.count();
                }

                @Override
                public DataModel createPageDataModel() {

                    return new ListDataModel(bookFacadeLocal.findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public List<Book> getBooks() {

        if (!author.isEmpty()) {
            this.books = bookFacadeLocal.findByAuthor(author);
        } else if (!title.isEmpty()) {
            this.books = bookFacadeLocal.findByTitle(title);
        } else {
            this.books = bookFacadeLocal.findAll();
        }
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void removeBook(Book book) {
        bookFacadeLocal.remove(book);
    }

    public int getSize() {
        return getBooks().size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DataModel getDataModel() {
        if (dataModel == null) {
            dataModel = getPagination().createPageDataModel();
        }
        return dataModel;
    }

    private void recreateModel() {
        dataModel = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    private void updateCurrentItem() {
        int count = bookFacadeLocal.count();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {

                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            bookItem = bookFacadeLocal.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "index";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "index";
    }

    public String notLoggedIn() {
        return "Login if you want to read book";
    }

}
