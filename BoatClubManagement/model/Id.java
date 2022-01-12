package BoatClubManagement.model;

import java.util.ArrayList;

public class Id {
	private int id;

	/**
	 * Check member id and give the appropriate id number based on the last id.
	 */

	public Id(ArrayList<Member> members) {
		if (members != null && !members.isEmpty()) {
			int maxId = 0;
			for (Member m : members) {
				if (m.getId() > maxId) {
					maxId = m.getId();
				}
			}
			this.id = maxId + 1;
		} else {
			this.id = 1;
		}
	}

	public int getId() {
		return id;
	}
}