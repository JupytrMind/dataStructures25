import java.util.*;
import java.util.function.*;

/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
  
 import java.util.List;
 import java.util.ArrayList;
 import java.time.chrono.IsoChronology;
 import java.time.LocalDate;
 import java.time.temporal.ChronoUnit;
 import java.time.Period;
  
 class Person {
    
     public enum Sex {
         MALE, FEMALE
     }
    
     String name; 
     LocalDate birthday;
     Sex gender;
     String emailAddress;
    
     Person(String nameArg, LocalDate birthdayArg,
         Sex genderArg, String emailArg) {
         name = nameArg;
         birthday = birthdayArg;
         gender = genderArg;
         emailAddress = emailArg;
     }  
  
     public int getAge() {
         return birthday
             .until(IsoChronology.INSTANCE.dateNow())
             .getYears();
     }
  
     public void printPerson() {
       System.out.println(name + ", " + this.getAge());
     }
      
     public Sex getGender() {
         return gender;
     }
      
     public String getName() {
         return name;
     }
      
     public String getEmailAddress() {
         return emailAddress;
     }
      
     public LocalDate getBirthday() {
         return birthday;
     }

     public boolean isOver18() { return this.getAge() > 18; }
      
     public static int compareByAge(Person a, Person b) {
         return a.birthday.compareTo(b.birthday);
     }
  
     public static List<Person> createRoster() {
          
         List<Person> roster = new ArrayList<>();
         roster.add(
             new Person(
             "Fred",
             IsoChronology.INSTANCE.date(1980, 6, 20),
             Person.Sex.MALE,
             "fred@example.com"));
         roster.add(
             new Person(
             "Jane",
             IsoChronology.INSTANCE.date(1990, 7, 15),
             Person.Sex.FEMALE, "jane@example.com"));
         roster.add(
             new Person(
             "George",
             IsoChronology.INSTANCE.date(1991, 8, 13),
             Person.Sex.MALE, "george@example.com"));
         roster.add(
             new Person(
             "Bob",
             IsoChronology.INSTANCE.date(2000, 9, 12),
             Person.Sex.MALE, "bob@example.com"));
          
         return roster;
     }
      
 }

 class PersonIsOver18Predicate implements Predicate<Person> {
    public boolean test(Person p) {
        return p.getAge() > 18;
    }
}

public class Roster {
    List<Person> roster;

    public Roster() {
        roster = Person.createRoster();
    }

    public boolean isOver(Person p, int age) { return p.getAge() >= age; }

    public List<Person> getPeopleOver(int age) {
        List<Person> lst = new LinkedList<>();
        for (Person p : roster) {
            if (p.getAge() > age) lst.add(p);
        }
        return lst;
    }

    public List<Person> getPeopleUnder(int age) {
        List<Person> lst = new LinkedList<>();
        for (Person p : roster) {
            if (p.getAge() < age) lst.add(p);
        }
        return lst;
    }

    public List<Person> getPeopleOfGender(Person.Sex g) {
        List<Person> lst = new LinkedList<>();
        for (Person p : roster) {
            if (p.getGender().equals(g)) lst.add(p);
        }
        return lst;
    }

    

    public List<Person> getPeopleIf(Predicate<Person> filter) {
        List<Person> lst = new LinkedList<>();
        for (Person p : roster) {
            if (filter.test(p)) lst.add(p);
        }
        return lst;
    }

    

    public static void main(String[] args) {
        Roster r = new Roster();
        
        // get people over 18
        // List<Person> peopleOver18 = r.getPeopleIf(new PersonIsOver18Predicate());
        // List<Person> peopleOver18 = r.getPeopleIf(Person::isOver18);
        List<Person> peopleOver18 = r.getPeopleIf(p -> p.getAge() > 18);
        List<Person> peopleUnder18 = r.getPeopleIf(new Predicate<P>() {
            public boolean test(Person p) { return p.getAge() < 18; }
        };)
    }
}