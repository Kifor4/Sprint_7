package model;

public class OrdersListModel implements Model {
    private OrderModel[] orders;
    private PageInfoModel pageInfoModel;
    private StationModel[] availableStationModels;

    public OrderModel[] getOrders() {
        return orders;
    }

    public void setOrders(OrderModel[] orders) {
        this.orders = orders;
    }

    public PageInfoModel getPageInfo() {
        return pageInfoModel;
    }

    public void setPageInfo(PageInfoModel pageInfoModel) {
        this.pageInfoModel = pageInfoModel;
    }

    public StationModel[] getAvailableStations() {
        return availableStationModels;
    }

    public void setAvailableStations(StationModel[] availableStationModels) {
        this.availableStationModels = availableStationModels;
    }
}