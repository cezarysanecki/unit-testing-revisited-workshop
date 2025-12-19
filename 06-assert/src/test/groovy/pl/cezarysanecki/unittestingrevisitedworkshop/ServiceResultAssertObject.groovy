package pl.cezarysanecki.unittestingrevisitedworkshop

class ServiceResultAssertObject {

    private ServiceResult serviceResult

    private ServiceResultAssertObject(ServiceResult serviceResult) {
        this.serviceResult = serviceResult
    }

    static ServiceResultAssertObject of(ServiceResult serviceResult) {
        return new ServiceResultAssertObject(serviceResult)
    }

    ServiceResultAssertObject theSameVehicle(UUID vehicleId) {
        assert serviceResult.handledVehicle() == vehicleId
        return this
    }

    ServiceResultAssertObject rejected() {
        assert serviceResult.status() == ServiceResult.Status.REJECTED
        return this
    }

    ServiceResultAssertObject accepted() {
        assert serviceResult.status() == ServiceResult.Status.SUCCESS
        return this
    }

    ServiceResultAssertObject noCharge() {
        assert serviceResult.cost() == 0.0d
        return this
    }

    ServiceResultAssertObject charge(double cost) {
        assert serviceResult.cost() == cost
        return this
    }

    ServiceResultAssertObject noHandledParts() {
        assert serviceResult.acceptedParts().isEmpty()
        return this
    }

    ServiceResultAssertObject allParts() {
        assert serviceResult.acceptedParts() == EnumSet.allOf(Parts.class)
        return this
    }

    ServiceResultAssertObject allPartsBut(Parts part) {
        def allParts = EnumSet.allOf(Parts.class)
        allParts.remove(part)
        assert serviceResult.acceptedParts() == allParts
        return this
    }

}