package ControllerFile;

import Mapping.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties());
        cfg.addAnnotatedClass(Professor.class);
        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Course.class);
        cfg.addAnnotatedClass(Grade.class);
        cfg.addAnnotatedClass(Section.class);
        cfg.addAnnotatedClass(Student.class);
        cfg.addAnnotatedClass(Weight.class);
        cfg.addAnnotatedClass(Logininfo.class);
        cfg.addAnnotatedClass(Room.class);
        cfg.addAnnotatedClass(Studentlist.class);
        sessionFactory = cfg.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}