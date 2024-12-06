package com.ecofoodconnect.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author tanmay
 */
public class PersonDirectory {
    private ArrayList<Person> persons;

    public PersonDirectory() {
        this.persons = new ArrayList<>();
    }

    // Add a person to the directory
    public void addPerson(Person person) {
        this.persons.add(person);
    }

    // Authenticate a person by username and password
    public Person authenticate(String username, String password) {
        for (Person person : persons) {
            if (person.getUsername().equals(username) && person.getPassword().equals(password)) {
                return person;
            }
        }
        return null;
    }

    // Get all persons in the directory
    public ArrayList<Person> getAllPersons() {
        return new ArrayList<>(persons);
    }

    // Get persons filtered by role
    public ArrayList<Person> getPersonsByRole(String role) {
        return persons.stream()
                .filter(person -> person.getRole().equals(role))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Get enterprise-specific persons
    public ArrayList<EnterprisePerson> getEnterprisePersons() {
        return persons.stream()
                .filter(person -> person instanceof EnterprisePerson)
                .map(person -> (EnterprisePerson) person)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Get persons filtered by enterprise type
    public ArrayList<EnterprisePerson> getEnterprisePersonsByType(String enterpriseType) {
        return persons.stream()
                .filter(person -> person instanceof EnterprisePerson)
                .map(person -> (EnterprisePerson) person)
                .filter(enterprisePerson -> enterpriseType.equals(enterprisePerson.getEnterpriseType()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Get non-enterprise persons
    public ArrayList<Person> getNonEnterprisePersons() {
        return persons.stream()
                .filter(person -> !(person instanceof EnterprisePerson))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Remove a person by ID
    public void removePersonById(String id) {
        persons.removeIf(person -> person.getId().equals(id));
    }

    // Update a person's information
    public void updatePerson(Person updatedPerson) {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getId().equals(updatedPerson.getId())) {
                persons.set(i, updatedPerson);
                break;
            }
        }
    }

    // Check if a username already exists
    public boolean isUsernameTaken(String username) {
        return persons.stream().anyMatch(person -> person.getUsername().equals(username));
    }
}