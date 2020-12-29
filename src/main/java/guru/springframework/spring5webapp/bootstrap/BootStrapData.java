package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//CommandLineRunner tells Spring to look for instances of this type
//once it finds some, it's going to run them
//@Component annotation tell Spring to detect this as a Spring managed component.
@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    //When Spring implement this component, that's going to bring it into the Spring context, it will
    // do Dependency injection into the constructor for an instance of the AuthorRepository and BookRepository
    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    //CMD+N: implement run methods
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher penguin = new Publisher("Penguin","123 P St", "Sacramento", "CA", "95004");
        publisherRepository.save(penguin);
        System.out.println("Number of Publishers "+ publisherRepository.count());

        //Go to Author and Book Class and remove books and authors in the constructors respectively
        // initialize authors and books properties: = new HashSet<>();
        Author eric = new Author("Eric","Evans");
        Book ddd = new Book("Domain Driven Design", "12312");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        // use setter because one book only has one publisher
        ddd.setPublisher(penguin);
        // use getter + add because one publisher has many books
        penguin.getBooks().add(ddd);

        //Underneath the cover, Spring Data JPA is utilizing Hibernate to save these two in memory H2 Database.
        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(penguin);

        Author rod = new Author("Rod","Johnson");
        Book noEJB = new Book("J2EE Development without EJB","3939459459");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        noEJB.setPublisher(penguin);
        penguin.getBooks().add(noEJB);
//        publisherRepository.save(penguin);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(penguin);

        System.out.println("Number of Books "+ bookRepository.count());
        System.out.println("Penguin Number of Books: "+ penguin.getBooks().size());
    }
}
