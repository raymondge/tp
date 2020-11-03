package seedu.address.testutil;

import seedu.address.logic.commands.room.AllocateRoomCommand;
import seedu.address.logic.commands.room.AllocateRoomCommand.AllocateRoomDescriptor;
import seedu.address.model.patient.Name;
import seedu.address.model.room.Room;

/**
 * A utility class to help with building AllocateRoomDescriptor objects.
 */
public class AllocateRoomDescriptorBuilder {

    private AllocateRoomDescriptor descriptor;

    public AllocateRoomDescriptorBuilder() {
        descriptor = new AllocateRoomDescriptor();
    }

    public AllocateRoomDescriptorBuilder(AllocateRoomCommand.AllocateRoomDescriptor descriptor) {
        this.descriptor = new AllocateRoomCommand.AllocateRoomDescriptor(descriptor);
    }

    /**
     * Returns an {@code AllocateRoomDescriptor} with fields containing {@code room}'s details
     */
    public AllocateRoomDescriptorBuilder(Room room) {
        descriptor = new AllocateRoomDescriptor();
        descriptor.setRoomNumber(room.getRoomNumber());
        descriptor.setOccupied(room.isOccupied());
        descriptor.setPatientName(room.getPatient().get().getName());
    }

    /**
     * Sets the {@code roomNumber} of the {@code AllocateRoomDescriptor} that we are building.
     */
    public AllocateRoomDescriptorBuilder withRoomNumber(Integer roomNumber) {
        descriptor.setRoomNumber(roomNumber);
        return this;
    }

    /**
     * Sets the {@code isOccupied} of the {@code AllocateRoomDescriptor} that we are building.
     */
    public AllocateRoomDescriptorBuilder withOccupancy(Boolean isOccupied) {
        descriptor.setOccupied(isOccupied);
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code AllocateRoomDescriptor} that we are building.
     */
    public AllocateRoomDescriptorBuilder withPatient(Name name) {
        descriptor.setPatientName(name);
        return this;
    }

    public AllocateRoomDescriptor build() {
        return descriptor;
    }

    @Override
    public String toString() {
        return "AllocateRoomDescriptorBuilder{"
            + "descriptor=" + descriptor
            + '}';
    }
}
