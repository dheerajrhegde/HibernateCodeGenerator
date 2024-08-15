import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.github.dheerajhegde.hibernate.Customer;
import org.hibernate.Query;
import org.hibernate.Transaction;
import java.util.Iterator;
// Main class
public class App {

    // Main driver method
    public static void main(String[] args)
    {

        // Create Configuration
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Customer.class);

        // Create Session Factory and auto-close with try-with-resources.
        try (SessionFactory sessionFactory
                     = configuration.buildSessionFactory()) {

            // Initialize Session Object
            Session session = sessionFactory.openSession();

            Transaction t = session.beginTransaction();

            Query query = session.createQuery("from Customer");
            java.util.List<Customer> list = query.list();
            for (Customer c: list){
                System.out.println(c.getCustomerId());
            }

            t.commit();
            session.close();
        }
    }
}