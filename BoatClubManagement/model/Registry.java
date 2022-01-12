package BoatClubManagement.model;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Registry {
    private Id id;
    private ArrayList<Member> members;
    private final FileHandler file_Handler = new FileHandler("FileHandler");

    public Registry() throws Exception {
        startUp();
    }

    public void storeInformation() throws Exception {
        file_Handler.writeToFile(members);
    }

    public void registerMember(String name, String lastName,String socialSecurityNumber, String phoneNumber) {
        id = new Id(members);
        Member member = new Member(name, lastName,socialSecurityNumber, phoneNumber,id.getId());
        members.add(member);
    }

    public Member getMemberRegistry(int id)  {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        throw new InputMismatchException("Member Not Found!");
    }

    public Member[] getMemberArray() {
        Member[] fullMemberList = new Member[members.size()];
        return members.toArray(fullMemberList);
    }

    public void removeMember(int id)  {
        Member member = getMemberRegistry(id);
        members.remove(member);
    }

    /**
     * If the File manager has access to a valid and readable file, it will initialize all the data to the members Arraylist
     * else
     * it will create a new array list.
     */

    @SuppressWarnings("unchecked")
    private void startUp() throws Exception {
        if (file_Handler.containsReadableFile()) {
            members = (ArrayList<Member>) file_Handler.readFromFile();
        } else {
            members = new ArrayList<>();
            file_Handler.writeToFile(members);
        }
    }
}
