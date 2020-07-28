/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import business.facade.PublisherFacadeLocal;
import business.model.Publisher;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author riki
 */
@Named(value = "publisherManagedBean")
@SessionScoped
public class PublisherManagedBean implements Serializable{

    @Inject
    private PublisherFacadeLocal publisherFacadeLocal;

    public PublisherFacadeLocal getPublisherFacadeLocal() {
        return publisherFacadeLocal;
    }
    
    
    private String publiserName;
    private String publisherAddress;

    public String getPubliserName() {
        return publiserName;
    }

    public void setPubliserName(String publiserName) {
        this.publiserName = publiserName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }
    
    public PublisherManagedBean() {
    }
    
    public void savePublisher() {
        Publisher publisher = new Publisher();
        publisher.setAddress(publisherAddress);
        publisher.setName(publiserName);
        publisherFacadeLocal.create(publisher);
    }
    
    
}
