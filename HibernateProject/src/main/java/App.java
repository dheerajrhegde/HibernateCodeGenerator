import com.github.dheerajhegde.hibernate.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.github.dheerajhegde.hibernate.Customer;
import com.github.dheerajhegde.hibernate.Order;
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

            System.out.println("Customer");
            Query query_cust = session.createQuery("from Customer");
            java.util.List<Customer> list_cust = query_cust.list();
            for (Customer c: list_cust){
                System.out.println(c.getCustomerId()+" | "+c.getCompanyName()+" | "+c.getContactName());
                break;
            }
            System.out.println("");
            System.out.println("Employee");
            Query query_emp = session.createQuery("from Employee");
            java.util.List<Employee> list_emp = query_emp.list();
            for (Employee e: list_emp){
                System.out.println(e.getEmployeeId()+" | "+e.getFirstName()+" | "+e.getLastName());
                break;
            }

            System.out.println("");
            System.out.println("Order");
            Query query_ord = session.createQuery("from Order");
            java.util.List<Order> list_ord = query_ord.list();
            for (Order order: list_ord){
                String cust_id = order.getCustomerId();
                Customer cust = session.load(Customer.class, cust_id);
                System.out.println(order.getOrderId()+" | "+order.getCustomerId()+" | "+cust.getCompanyName());
                break;
            }

            t.commit();
            session.close();
        }
    }
}